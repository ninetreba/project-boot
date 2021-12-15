package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CreateTransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.TransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.ParkingType;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Bicycle;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.ElectricScooter;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Transport;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.ParkingRepository;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


// клиент - поиск по статусу, типу, парковке
// admin - создание всех типов, удаление(изм статуса), поиск по статусу, типу, парковке

@Service
public class VehicleServiceImpl implements VehicleService{
    private final VehicleRepository vehicleRepository;
    private final ParkingRepository parkingRepository;

    private final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository, ParkingRepository parkingRepository) {
        this.vehicleRepository = vehicleRepository;
        this.parkingRepository = parkingRepository;
    }

    public TransportDto createTransport(CreateTransportDto createTransportDto){
        Parking parking = parkingRepository.getById(createTransportDto.getParkingId());

        if (createTransportDto.getType() == Type.ELECTRIC_SCOOTER && parking.getParkingType() == ParkingType.BICYCLE_ONLY
        || createTransportDto.getType() == Type.BICYCLE && parking.getParkingType() == ParkingType.ELECTRIC_SCOOTER_ONLY){
            throw new IllegalArgumentException("транспорт нельзя поместить на эту паркову");
        }

        if (createTransportDto.getType() == Type.ELECTRIC_SCOOTER){
            ElectricScooter electricScooter = new ElectricScooter();

            electricScooter.setType(createTransportDto.getType());
            electricScooter.setStatus(Status.WORKING);
            electricScooter.setCondition(createTransportDto.getCondition());
            electricScooter.setCurrentParking(parking);
            electricScooter.setBattery(createTransportDto.getBattery());
            electricScooter.setMaxSpeed(createTransportDto.getMaxSpeed());
            vehicleRepository.save(electricScooter);
            electricScooter.setNumber("ЭСМ-" + electricScooter.getId());
            vehicleRepository.save(electricScooter);
            logger.info("создание транспорта.");
            return toTransportDto((Transport) electricScooter);
        } else {
            Bicycle bicycle = new Bicycle();
            bicycle.setType(createTransportDto.getType());
            bicycle.setStatus(Status.WORKING);
            bicycle.setCondition(createTransportDto.getCondition());
            bicycle.setCurrentParking(parking);
            vehicleRepository.save(bicycle);
            return toTransportDto((Transport) bicycle);
        }
    }

    public List<TransportDto> getTransportAll(){
        List<TransportDto> transportDtoList = new ArrayList<>();
        for (Transport transport : vehicleRepository.findByStatus(Status.WORKING)){
            transportDtoList.add(toTransportDto(transport));
        }
        logger.info("получение всех транспортов.");
        return transportDtoList;
    }

    // ok
    public TransportDto getById(Long id){
        return toTransportDto(vehicleRepository.findById(id).orElseThrow());
    }


    public TransportDto deleteTransport(Long id){
        Transport transport = vehicleRepository.findById(id).orElseThrow();
        transport.setStatus(Status.DELETED);
        vehicleRepository.save(transport);
        return toTransportDto(transport);
    }


    // ok
    public List<TransportDto> findByType(Type type){
        List<TransportDto> transportDtoList = new ArrayList<>();

        for (Transport transport : vehicleRepository.findByType(type)){
            transportDtoList.add(toTransportDto(transport));
        }
        return transportDtoList;
    }

    // обработка если в парковке нет машин
    public List<TransportDto> findByParking(String name){
        List<TransportDto> transportDtoList = new ArrayList<>();
        for (Transport transport : vehicleRepository.findByCurrentParking_name(name)){
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

    public TransportDto toTransportDto(Transport transport){

        TransportDto transportDto = new TransportDto();

        if (transport.getType() == Type.ELECTRIC_SCOOTER) {
            ElectricScooter electricScooter = (ElectricScooter) transport;

            transportDto.setId(electricScooter.getId());
            transportDto.setNumber(transport.getNumber());
            transportDto.setType(electricScooter.getType());
            transportDto.setCurrentParking(ParkingServiceImpl.toParkingDto(electricScooter.getCurrentParking()));
            transportDto.setCondition(electricScooter.getCondition());
            transportDto.setBattery(electricScooter.getBattery());
            transportDto.setMaxSpeed(electricScooter.getMaxSpeed());
        } else {
            transportDto.setId(transport.getId());
            transportDto.setNumber(transport.getNumber());
            transportDto.setType(transport.getType());
            transportDto.setCurrentParking(ParkingServiceImpl.toParkingDto(transport.getCurrentParking()));
            transportDto.setCondition(transport.getCondition());
        }
        return transportDto;
    }


}
