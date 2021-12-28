package com.accenture.russiaatc.irentservice10.SNAPSHOT.controller;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CloseRentDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CreateRentDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.ParkingDtoShort;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.RentDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Реализовать TripController с операциями «начать поездку» и «завершить поездку» -- rent
// client - создание, закрытие, получение всех своих аренд
// admin - поиск по статусу, клиенту, ТС с пагинацией

@RestController
@RequestMapping("/trip")
public class TripController {
    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
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
