package com.accenture.russiaatc.irentservice10.SNAPSHOT.controller;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.*;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.service.TripService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/trips")
public class TripController {
    private final TripService tripService;

    @GetMapping
    public List<RentDto> getAllRent(){
        return tripService.getAllRent();
    }

    @PostMapping
    public RentDto createRent(@RequestBody CreateRentDto createRentDto){
        return tripService.createRent(createRentDto);
    }

    @PutMapping
    public RentDto closeRent(@RequestBody CloseRentDto closeRentDto){
        return tripService.closeRent(closeRentDto);
    }




}
