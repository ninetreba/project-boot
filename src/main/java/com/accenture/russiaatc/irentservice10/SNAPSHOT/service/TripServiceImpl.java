package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.RentActionDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.RentDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.rent.Rent;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.rent.StatusRent;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Transport;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.TripRepository;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.UserRepository;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class TripServiceImpl implements TripService{
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;


    @Autowired
    public TripServiceImpl(TripRepository tripRepository, VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    public RentDto createRent(RentActionDto rentActionDto){

        // проверки баланса и тс


        Rent rent = new Rent();
        Transport transport = vehicleRepository.findById(rentActionDto.getIdTransport()).orElseThrow();

        rent.setUser(userRepository.getById(rentActionDto.getIdTransport()));

        // IN_PROGRESS
        rent.setStatusRent(StatusRent.IN_PROGRESS);
        rent.setStartRent(LocalDateTime.now());
        rent.setTransport(transport);
        rent.setStartParking(transport.getCurrentParking());


// to RentDto


    return null;
    }

    public void closeRent(Long id){
        // в настройках сделать цена за минуту и нач цена для начала поездки
        Rent rent = tripRepository.findByIdAndStatusRent(id, StatusRent.IN_PROGRESS);
        rent.setStatusRent(StatusRent.CLOSED);
        rent.setEndRent(LocalDateTime.now());

        long diff = MINUTES.between(rent.getStartRent(), rent.getEndRent());

        if (rent.getTransport().getType() == Type.ELECTRIC_SCOOTER){
            rent.setCost(new BigDecimal(diff * 10));
        } else rent.setCost(new BigDecimal(diff * 6));



    }



}
