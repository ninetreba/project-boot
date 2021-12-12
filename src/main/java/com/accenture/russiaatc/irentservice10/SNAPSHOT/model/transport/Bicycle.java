package com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "BICYCLE", schema = "PUBLIC")
@PrimaryKeyJoinColumn(name = "TRANSPORT_ID_TRANSPORT")
public class Bicycle extends Transport {

//    @OneToOne(mappedBy = "bicycle")
//    private Transport transport;

    public Bicycle() {}

    public Bicycle(Long id, Type type, Condition condition, Status status, Parking currentParking) {
        super(id, type, condition, status, currentParking);
    }

}
