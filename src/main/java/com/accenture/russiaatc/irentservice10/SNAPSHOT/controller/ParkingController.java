package com.accenture.russiaatc.irentservice10.SNAPSHOT.controller;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CreateParkingDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.ParkingDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.service.ParkingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/parkings")
public class ParkingController {
    private final ParkingService parkingService;


    @GetMapping
    public List<ParkingDto> getParkings(){
        return parkingService.getParkings();
    }

    @PutMapping
    public Parking updateParking(@RequestBody ParkingDto parkingDto){
        return parkingService.updateParking(parkingDto);
    }

    @PostMapping
    public void createParking(@RequestBody CreateParkingDto parking){
        parkingService.createParking(parking);
    }

    @DeleteMapping("/{id}")
    public Parking deleteParking(@PathVariable Long id){
        return parkingService.deleteParking(id);
    }


}
