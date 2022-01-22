package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CloseRentDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CreateRentDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.RentDto;

import java.util.List;

public interface TripService {

    List<RentDto> getTrips(Long id);
    List<RentDto> getUserTrips();
    RentDto createRent(CreateRentDto createRentDto);
    RentDto closeRent(CloseRentDto closeRentDto);
    List<RentDto> getAllRent();

}
