package com.accenture.russiaatc.irentservice10.SNAPSHOT.controller;

// Реализовать ParkingController с операциями получение списка парковок,
// добавление парковки, обновление данных парковки, удаление парковки.

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CreateParkingDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.ParkingDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// пользователь - получение всех парковок
// admin - созд, измен, удаление(изм статуса), получение всех парковок

@RestController
@RequestMapping("/parking")
public class ParkingController {
    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }


    @GetMapping("/all")
    public List<ParkingDto> getParkingList(){
        return parkingService.getParkingAll();
    }

    @PutMapping
    public Parking updateParking(@RequestBody Parking parking){
        return parkingService.updateParking(parking);
    }

    @PostMapping
    public Parking createParking(@RequestBody CreateParkingDto createParkingDto){
        return parkingService.createParking(createParkingDto);
    }

    @DeleteMapping("/{id}")
    public Parking deleteParking(@PathVariable Long id){
        return parkingService.deleteParking(id);
    }


}
