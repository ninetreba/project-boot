package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.configuration.PriceProperties;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.RentActionDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.RentDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.rent.Rent;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.rent.StatusRent;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Transport;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.user.User;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.TripRepository;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.UserRepository;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class TripServiceImpl implements TripService{
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final PriceProperties priceProperties;


    @Autowired
    public TripServiceImpl(TripRepository tripRepository, VehicleRepository vehicleRepository, UserRepository userRepository, PriceProperties priceProperties) {
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.priceProperties = priceProperties;
    }


    public RentDto createRent(RentActionDto rentActionDto){
        Rent rent = new Rent();
        Transport transport = vehicleRepository.findById(rentActionDto.getIdTransport()).orElseThrow();

        User user = userRepository.getById(rentActionDto.getIdUser());

        if (transport.getType() == Type.ELECTRIC_SCOOTER){
            if (user.getBalance().compareTo(priceProperties.getStartingPriceElectricScooter()) == -1) {
                throw new IllegalArgumentException("недостаточно средств");
            }
        } else {
            if (user.getBalance().compareTo(priceProperties.getStartingPriceBicycle()) == -1){
                throw new IllegalArgumentException("недостаточно средств");
            }
        }

        // проверка свободно ли тс!

        // int compareTo(BigDecimal other): сравнивает два числа.
        // Возвращает -1, если текущий объект меньше числа other, 1 - если текущий объект больше и 0 - если числа равны



        rent.setUser(userRepository.getById(rentActionDto.getIdTransport()));

        rent.setStatusRent(StatusRent.IN_PROGRESS);
        rent.setStartRent(LocalDateTime.now());
        rent.setTransport(transport);
        rent.setStartParking(transport.getCurrentParking());


        tripRepository.save(rent);


// to RentDto



// на методы find, get посмотреть чтоб были проверки если нет такой парковки, транспорта
// по поводу парковки у машины. типа какая парковка когда машина ездит но
//  с другой стороны надо давать пользователю ток свободные машины на парковке
    return null;
    }


    public void closeRent(RentActionDto rentActionDto){
        Rent rent = tripRepository.findByUser_IdAndStatusRentAndTransport_Id(rentActionDto.getIdUser(), StatusRent.IN_PROGRESS, rentActionDto.getIdTransport());
        rent.setEndRent(LocalDateTime.now());

        BigDecimal durationRent = new BigDecimal(MINUTES.between(rent.getStartRent(), rent.getEndRent()));

        BigDecimal cost;
        if (rent.getTransport().getType() == Type.ELECTRIC_SCOOTER){
            cost = durationRent.multiply(priceProperties.getPricePerMinElectricScooter());
            cost = cost.add(priceProperties.getStartingPriceElectricScooter());
        } else {
            cost = durationRent.multiply(priceProperties.getPricePerMinBicycle());
            cost = cost.add(priceProperties.getStartingPriceBicycle());
        }

        User user = userRepository.getById(rentActionDto.getIdUser());
        if (user.getBalance().compareTo(cost) == -1){
            throw new IllegalArgumentException("недостаточно средств, поездка не закрыта"); // поменять на свои рантайм эксэпшн
        }

        // задает парковку закрытия. обработка что на этой парковке можно поставить этот тип транспорта

        rent.setStatusRent(StatusRent.CLOSED);
        rent.setCost(cost);
        Parking endParking;


        tripRepository.save(rent);
        // to dto
    }



}
