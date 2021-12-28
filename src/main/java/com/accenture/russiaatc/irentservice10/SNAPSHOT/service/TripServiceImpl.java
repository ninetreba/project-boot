package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.configuration.TripProperties;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.exception.BusinessRuntimeException;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.exception.ErrorCodeEnum;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CloseRentDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CreateRentDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.RentDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.ParkingType;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.rent.Rent;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.rent.StatusRent;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Transport;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.TransportStatus;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.user.User;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService{
    private final TripRepository tripRepository;
    private final VehicleService vehicleService;
    private final UserService userService;
    private final TripProperties tripProperties;
    private final ParkingService parkingService;



    public RentDto createRent(CreateRentDto createRentDto) {
        if (tripRepository.countByUser_IdAndStatusRent(createRentDto.getIdUser(), StatusRent.IN_PROGRESS) >= tripProperties.getMaxNumberTrips()) {
            throw new BusinessRuntimeException(ErrorCodeEnum.TOO_MUCH_RENTS);
        }

        Transport transport = vehicleService.getById(createRentDto.getIdTransport());
        if (transport.getTransportStatus() == TransportStatus.BUSY) {
            throw new BusinessRuntimeException(ErrorCodeEnum.TRANSPORT_BUSY);
        }

        User user = userService.getById(createRentDto.getIdUser());
        Type transportType = transport.getType();

        if (transportType == Type.ELECTRIC_SCOOTER &&
                user.getBalance().compareTo(tripProperties.getStartingPriceElectricScooter()) == -1) {
            throw new BusinessRuntimeException(ErrorCodeEnum.NO_MONEY);
        } else {
            if (user.getBalance().compareTo(tripProperties.getStartingPriceBicycle()) == -1) {
                throw new BusinessRuntimeException(ErrorCodeEnum.NO_MONEY);
            }
        }

        BigDecimal cost = new BigDecimal(0);
        if (transportType == Type.ELECTRIC_SCOOTER) {
            cost = tripProperties.getStartingPriceElectricScooter();
        } else {
            cost = tripProperties.getStartingPriceBicycle();
        }

        transport.setTransportStatus(TransportStatus.BUSY);

        Rent rent = new Rent()
                .setCost(cost)
                .setUser(user)
                .setStatusRent(StatusRent.IN_PROGRESS)
                .setStartRent(LocalDateTime.now())
                .setTransport(transport)
                .setStartParking(transport.getCurrentParking());

        vehicleService.save(transport);
        tripRepository.save(rent);
        return toRentDto(rent);
    }



    public RentDto closeRent(CloseRentDto closeRentDto){
        Rent rent = tripRepository.findByUser_IdAndStatusRentAndTransport_Id(closeRentDto.getIdUser(), StatusRent.IN_PROGRESS, closeRentDto.getIdTransport());

        Transport transport = vehicleService.getById(closeRentDto.getIdTransport());
        Type transportType = transport.getType();

        Parking endParking = parkingService.getParking(closeRentDto.getIdParking());

        if (transportType == Type.ELECTRIC_SCOOTER && endParking.getParkingType() == ParkingType.BICYCLE_ONLY
                || transportType == Type.BICYCLE && endParking.getParkingType() == ParkingType.ELECTRIC_SCOOTER_ONLY){
            throw new BusinessRuntimeException(ErrorCodeEnum.WRONG_TYPE_PARKING);
        }

        LocalDateTime endTime = LocalDateTime.now();
        int scale = 2;
        BigDecimal durationRent = new BigDecimal(SECONDS.between(rent.getStartRent(), endTime));
        durationRent = durationRent.divide(new BigDecimal(60), scale, RoundingMode.CEILING);

        BigDecimal cost = new BigDecimal(0);
        if (transportType == Type.ELECTRIC_SCOOTER){
            cost = durationRent.multiply(tripProperties.getPricePerMinElectricScooter());
            cost = cost.add(tripProperties.getStartingPriceElectricScooter());
        } else {
            cost = durationRent.multiply(tripProperties.getPricePerMinBicycle());
            cost = cost.add(tripProperties.getStartingPriceBicycle());
        }

        User user = userService.getById(closeRentDto.getIdUser());
        if (user.getBalance().compareTo(cost) == -1){
            throw new BusinessRuntimeException(ErrorCodeEnum.NO_MONEY);
        }
        user.setBalance(user.getBalance().subtract(cost));


        rent.setStatusRent(StatusRent.CLOSED);
        transport.setTransportStatus(TransportStatus.FREE);
        rent.setCost(cost);
        rent.setEndRent(LocalDateTime.now());
        rent.setEndParking(endParking);

        vehicleService.save(transport);
        tripRepository.save(rent);
        userService.save(user);
        return toRentDto(rent);
    }


    public RentDto toRentDto(Rent rent){
        RentDto rentDto = RentDto.builder().id(rent.getId())
                .statusRent(rent.getStatusRent())
                .cost(rent.getCost())
                .userDto(UserServiceImpl.toUserDto(rent.getUser()))
                .transportDto(VehicleServiceImpl.toTransportDto(rent.getTransport()))
                .startParkingDto(ParkingServiceImpl.toParkingDto(rent.getStartParking()))
                .startRent(rent.getStartRent())
                .endRent(rent.getEndRent()).build();
        if (rent.getEndParking() != null){
            rentDto.setEndParkingDto(ParkingServiceImpl.toParkingDto(rent.getEndParking()));
        }
        return rentDto;
    }



}
