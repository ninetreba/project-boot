package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CreateParkingDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.ParkingDto;

import java.util.List;

// пользователь - получение всех парковок
// admin - созд, измен, удаление(изм статуса), получение всех парковок

public interface ParkingService {
    Parking getById(Long id);
    List<ParkingDto> getParkingAll();
    Parking createParking(CreateParkingDto createParkingDto);
    Parking updateParking(Parking parkingToUpdate);
    Parking deleteParking(Long id);
}
