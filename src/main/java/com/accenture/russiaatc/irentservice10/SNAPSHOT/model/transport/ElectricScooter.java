package com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport;


import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ELECTRIC_SCOOTER", schema = "PUBLIC")
@PrimaryKeyJoinColumn(name = "TRANSPORT_ID_TRANSPORT")
public class ElectricScooter extends Transport {

    private int battery;

    @Column(name = "MAX_SPEED")
    private int maxSpeed;


    public ElectricScooter() {}

    public ElectricScooter(Long id, Type type, Condition condition, Status status, Parking currentParking,
                           String number, int battery, int maxSpeed) {
        super(id, number, type, condition, status, currentParking);
        this.battery = battery;
        this.maxSpeed = maxSpeed;
    }

}