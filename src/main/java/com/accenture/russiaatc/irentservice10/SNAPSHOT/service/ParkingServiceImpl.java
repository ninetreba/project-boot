package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.ParkingDto;
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

    // ok
    @Override
    public Parking updateParking(Parking parkingToUpdate) {
        Parking parkingOld = getById(parkingToUpdate.getId());
        if (!isValid(parkingToUpdate)){
            throw new IllegalArgumentException("некорректные данные");
        }
        parkingOld.setName(parkingToUpdate.getName());
        parkingOld.setCoordinateX(parkingToUpdate.getCoordinateX());
        parkingOld.setCoordinateY(parkingToUpdate.getCoordinateY());
        parkingOld.setRadius(parkingToUpdate.getRadius());
        parkingOld.setParkingType(parkingToUpdate.getParkingType());
        parkingOld.setStatus(parkingToUpdate.getStatus());
        return parkingOld;
    }


    // ok
    @Override
    public List<ParkingDto> getParkingAll() {
        List<ParkingDto> parkingDtoList = new ArrayList<>();
        for (Parking parking : parkingRepository.findAll()){
            parkingDtoList.add(Parking.toParkingDto(parking));
        }
        return parkingDtoList;
    }

    // ok
    @Override
    public Parking getById(Long id) {

        return parkingRepository.findById(id).orElseThrow();
    }

    // ok
    @Override
    public Parking createParking(Parking parking){
        if (isValid(parking)){
            parkingRepository.save(parking);
            return parking;
        } else {
            throw new IllegalArgumentException("некорректные данные");
        }
    }

    // ok
    @Override
    public Parking deleteParking(Long id) {
        Parking parking = getById(id);
        parking.setStatus(Status.DELETED);
        parkingRepository.save(parking);
        return parking;
    }

    private boolean isValid(Parking parking){
        if (!parkingRepository.findById(parking.getId()).isPresent() && parking.getName() != null
        && parking.getCoordinateX() >= 0 && parking.getCoordinateY() >= 0 && parking.getRadius() > 0
        && parking.getParkingType() != null && parking.getStatus() != null) {
            return true;
        }
        return false;
    }


}
