package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CloseRentDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CreateRentDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.RentDto;

public interface TripService {

    RentDto createRent(CreateRentDto createRentDto);
    RentDto closeRent(CloseRentDto closeRentDto);

}
