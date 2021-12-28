package com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Condition;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.TransportStatus;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransportDto {
    private Long id;
    private String number;
    private Type type;
    private Condition condition;
    private Status status;
    private int battery = -1;
    private int maxSpeed = -1;
    private ParkingDtoShort currentParking;

}
