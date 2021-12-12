package com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.rent.StatusRent;
import java.math.BigDecimal;
import java.util.Date;

public class RentDto {
    private Long id;
    private StatusRent statusRent;
    private BigDecimal cost;
    private UserDto userDto;

    private TransportDto transportDto;
    private ParkingDto startParkingDto;
    private ParkingDto endParkingDto;

    private Date startRent;     // Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private Date endRent;

}
