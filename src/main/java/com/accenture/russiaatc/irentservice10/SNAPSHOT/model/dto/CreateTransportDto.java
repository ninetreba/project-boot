package com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Condition;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTransportDto {
    private Type type;
    private Condition condition;
    private Long parkingId;
    private int battery;
    private int maxSpeed;
}
