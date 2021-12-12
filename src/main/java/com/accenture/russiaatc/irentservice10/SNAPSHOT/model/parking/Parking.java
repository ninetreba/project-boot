package com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking;


import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.ParkingDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Transport;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Parking", schema = "PUBLIC")
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    @Column(name = "ID_PARKING")
    private Long id;

    private String name;

    @Column(name = "COORDINATE_X")
    private int coordinateX; // Double

    @Column(name = "COORDINATE_Y")
    private int coordinateY;

    private int radius;

    @Enumerated(EnumType.STRING)
    @Column(name = "PARKING_TYPE")
    private ParkingType parkingType;

    @Enumerated(EnumType.STRING)
    private Status status;


    @OneToMany(mappedBy = "currentParking")
    private List<Transport> transports;

    public Parking(){}

    public Parking(Long id, String name, int coordinateX, int coordinateY, int radius, ParkingType parkingType, Status status) {
        this.id = id;
        this.name = name;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.radius = radius;
        this.parkingType = parkingType;
        this.status = status;
    }

    public static ParkingDto toParkingDto (Parking parking){
        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setId(parking.getId());
        parkingDto.setName(parking.getName());
        return parkingDto;
    }


}
