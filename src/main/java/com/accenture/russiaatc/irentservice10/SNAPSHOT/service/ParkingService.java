package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.ParkingDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.ParkingDtoShort;

import java.util.List;

// пользователь - получение всех парковок
// admin - созд, измен, удаление(изм статуса), получение всех парковок

public interface ParkingService {
    Parking getParking(Long id);
    Parking getParkingByName(String name);
    List<ParkingDtoShort> getParkingAll();
    Parking createParking(ParkingDto parkingDto);
    Parking updateParking(ParkingDto parkingDto);
    Parking deleteParking(Long id);
}
