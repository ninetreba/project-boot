package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.exception.BusinessRuntimeException;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.exception.ErrorCodeEnum;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.ParkingDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.ParkingDtoShort;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// пользователь - получение всех парковок
// admin - созд, измен, удаление(изм статуса), получение всех парковок

@Service
public class ParkingServiceImpl implements ParkingService{
    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }


    @Override
    public List<ParkingDtoShort> getParkingAll() {
        List<ParkingDtoShort> parkingDtoList = new ArrayList<>();
        for (Parking parking : parkingRepository.findAllByStatus(Status.WORKING)){
            parkingDtoList.add(toParkingDto(parking));
        }
        return parkingDtoList;
    }


    @Override
    public Parking getParking(Long id) {
        return parkingRepository.findById(id).orElseThrow(
                () -> new BusinessRuntimeException(ErrorCodeEnum.PARKING_NOT_FOUND)
        );
    }

    public Parking getParkingByName(String name){
        return parkingRepository.findByName(name).orElseThrow(
                () -> new BusinessRuntimeException(ErrorCodeEnum.PARKING_NOT_FOUND)
        );
    }


    @Override
    public Parking createParking(ParkingDto parkingDto){
        Parking parking = new Parking();
        parking.setParkingType(parkingDto.getParkingType());
        parking.setStatus(parkingDto.getStatus());
        parking.setCoordinateX(parkingDto.getCoordinateX());
        parking.setCoordinateY(parkingDto.getCoordinateY());
        parking.setRadius(parkingDto.getRadius());
        parking.setName(parkingDto.getName());
        parkingRepository.save(parking);
        return parking;
    }

    @Override
    public Parking updateParking(ParkingDto parkingDto) {
        if (!isValid(parkingDto)){
            throw new BusinessRuntimeException(ErrorCodeEnum.INCORRECT_DATA);
        }
        Parking parkingOld = getParking(parkingDto.getId());
        parkingOld.setName(parkingDto.getName());
        parkingOld.setCoordinateX(parkingDto.getCoordinateX());
        parkingOld.setCoordinateY(parkingDto.getCoordinateY());
        parkingOld.setRadius(parkingDto.getRadius());
        parkingOld.setParkingType(parkingDto.getParkingType());
        parkingOld.setStatus(parkingDto.getStatus());
        parkingRepository.save(parkingOld);
        return parkingOld;
    }


    @Override
    public Parking deleteParking(Long id) {
        Parking parking = getParking(id);
        parking.setStatus(Status.DELETED);
        parkingRepository.save(parking);
        return parking;
    }

    private boolean isValid(ParkingDto parkingDto){
        if (parkingDto.getCoordinateX() >= 0 && parkingDto.getCoordinateY() >= 0 && parkingDto.getRadius() > 0
        && parkingDto.getParkingType() != null && parkingDto.getStatus() != null) {
            return true;
        }
        return false;
    }


    public static ParkingDtoShort toParkingDto (Parking parking){
        ParkingDtoShort parkingDto = new ParkingDtoShort();
        parkingDto.setId(parking.getId());
        parkingDto.setName(parking.getName());
        parkingDto.setParkingType(parking.getParkingType());
        return parkingDto;
    }



}
