package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.exception.BusinessRuntimeException;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.exception.ErrorCodeEnum;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CreateTransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.TransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.ParkingType;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.*;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.ParkingRepository;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


// клиент - поиск по статусу, типу, парковке
// admin - создание всех типов, удаление(изм статуса), поиск по статусу, типу, парковке

@RequiredArgsConstructor
@Service
public class VehicleServiceImpl implements VehicleService{
    private final VehicleRepository vehicleRepository;
    private final ParkingRepository parkingRepository;

    private final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);


    public TransportDto createTransport(CreateTransportDto createTransportDto){
        Parking parking = parkingRepository.getById(createTransportDto.getParkingId());

        if (createTransportDto.getType() == Type.ELECTRIC_SCOOTER && parking.getParkingType() == ParkingType.BICYCLE_ONLY
        || createTransportDto.getType() == Type.BICYCLE && parking.getParkingType() == ParkingType.ELECTRIC_SCOOTER_ONLY){
            throw new BusinessRuntimeException(ErrorCodeEnum.WRONG_TYPE_PARKING);
        }

        if (createTransportDto.getType() == Type.ELECTRIC_SCOOTER){
            ElectricScooter electricScooter = new ElectricScooter();

            electricScooter.setType(createTransportDto.getType());
            electricScooter.setStatus(Status.WORKING);

            electricScooter.setCoordinateX(parking.getCoordinateX() + parking.getRadius() * 0.3);
            electricScooter.setCoordinateY(parking.getCoordinateY() + parking.getRadius() * 0.4);

            electricScooter.setCondition(createTransportDto.getCondition());
            electricScooter.setCurrentParking(parking);
            electricScooter.setBattery(createTransportDto.getBattery());
            electricScooter.setMaxSpeed(createTransportDto.getMaxSpeed());
            electricScooter.setTransportStatus(TransportStatus.FREE);
            vehicleRepository.save(electricScooter);
            electricScooter.setNumber("ЭСМ-" + electricScooter.getId());
            vehicleRepository.save(electricScooter);
            logger.info("создание самоката.");
            return toTransportDto((Transport) electricScooter);
        }
        Bicycle bicycle = new Bicycle();
        bicycle.setCoordinateX(parking.getCoordinateX() + parking.getRadius() * 0.3);
        bicycle.setCoordinateY(parking.getCoordinateY() + parking.getRadius() * 0.4);
        bicycle.setTransportStatus(TransportStatus.FREE);
        bicycle.setType(createTransportDto.getType());
        bicycle.setStatus(Status.WORKING);
        bicycle.setCondition(createTransportDto.getCondition());
        bicycle.setCurrentParking(parking);
        vehicleRepository.save(bicycle);
        bicycle.setNumber("ВЕЛ-" + bicycle.getId());
        vehicleRepository.save(bicycle);
        logger.info("создание велосипеда.");
        return toTransportDto((Transport) bicycle);

    }

    public List<TransportDto> getTransportAll(){
        logger.info("получение всех транспортов.");
        List<TransportDto> transportDtoList = new ArrayList<>();
        for (Transport transport : vehicleRepository.findByStatus(Status.WORKING)){
            transportDtoList.add(toTransportDto(transport));
        }
        return transportDtoList;
    }

    @Override
    public TransportDto getTransport(Long id){
        logger.info("получение транспорта.");
        return toTransportDto(vehicleRepository.findById(id).orElseThrow(
                () -> new BusinessRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_FOUND)
        ));
    }

    @Override
    public Transport getById(Long id){
        logger.info("получение транспорта.");
        return vehicleRepository.findById(id).orElseThrow(
                () -> new BusinessRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_FOUND)
        );
    }

    public Transport findByNumber(String number){
        return vehicleRepository.findByNumber(number).orElseThrow(
                () -> new BusinessRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_FOUND)
        );
    }


    public TransportDto deleteTransport(Long id){
        logger.info("удаление транспорта.");
        Transport transport = vehicleRepository.findById(id).orElseThrow();
        transport.setStatus(Status.DELETED);
        vehicleRepository.save(transport);
        return toTransportDto(transport);
    }


    public List<TransportDto> findByType(Type type){
        List<TransportDto> transportDtoList = new ArrayList<>();

        for (Transport transport : vehicleRepository.findByType(type)){
            transportDtoList.add(toTransportDto(transport));
        }
        return transportDtoList;
    }


    public List<TransportDto> findByParking(Long id){
        List<TransportDto> transportDtoList = new ArrayList<>();
        for (Transport transport : vehicleRepository.findByCurrentParking_Id(id)){
            transportDtoList.add(toTransportDto(transport));
        }
        return transportDtoList;
    }

    public List<TransportDto> findByStatus(Status status){
        List<TransportDto> transportDtoList = new ArrayList<>();
        for (Transport transport : vehicleRepository.findByStatus(status)){
            transportDtoList.add(toTransportDto(transport));
        }
        return transportDtoList;
    }

    public void save(Transport transport){
        vehicleRepository.save(transport);
    }


    public List<TransportDto> findByTransportStatusAndStatus(TransportStatus transportStatus, Status status){
        List<TransportDto> transportDtoList = new ArrayList<>();
        for (Transport transport : vehicleRepository.findByTransportStatusAndStatus(transportStatus, status)){
            transportDtoList.add(toTransportDto(transport));
        }
        return transportDtoList;
    }


    public static TransportDto toTransportDto(Transport transport){

        TransportDto transportDto = new TransportDto();

        if (transport.getType() == Type.ELECTRIC_SCOOTER) {
            ElectricScooter electricScooter = (ElectricScooter) transport;

            transportDto.setId(electricScooter.getId());
            transportDto.setNumber(transport.getNumber());
            transportDto.setStatus(transport.getStatus());
            transportDto.setType(electricScooter.getType());
            transportDto.setCurrentParking(ParkingServiceImpl.toParkingDto(electricScooter.getCurrentParking()));
            transportDto.setCondition(electricScooter.getCondition());
            transportDto.setBattery(electricScooter.getBattery());
            transportDto.setMaxSpeed(electricScooter.getMaxSpeed());
        } else {
            transportDto.setId(transport.getId());
            transportDto.setNumber(transport.getNumber());
            transportDto.setStatus(transport.getStatus());
            transportDto.setType(transport.getType());
            transportDto.setCurrentParking(ParkingServiceImpl.toParkingDto(transport.getCurrentParking()));
            transportDto.setCondition(transport.getCondition());
        }
        return transportDto;
    }


}
