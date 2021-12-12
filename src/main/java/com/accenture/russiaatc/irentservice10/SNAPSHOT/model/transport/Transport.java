package com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport;


import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.TransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.parking.Parking;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRANSPORT", schema = "PUBLIC")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSPORT")
    protected Long id;

    //protected String number; I need to generate that
//    if (type == Type.ELECTRIC_SCOOTER){
//        number = "ЭСМ-" + id;
//    } else number = "ВЕЛ-" + id;



    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSPORT_TYPE")
    protected Type type;


    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSPORT_CONDITION")
    protected Condition condition;

    @Enumerated(EnumType.STRING)
    private Status status;


    @ManyToOne
    @JoinColumn(name = "CURRENT_PARKING")
    protected Parking currentParking;


    public static TransportDto toTransportDto(Transport transport){

        TransportDto transportDto = new TransportDto();

        if (transport.getType() == Type.ELECTRIC_SCOOTER) {
            ElectricScooter electricScooter = (ElectricScooter) transport;

            transportDto.setId(electricScooter.getId());
            transportDto.setType(electricScooter.getType());
            transportDto.setCurrentParking(Parking.toParkingDto(electricScooter.getCurrentParking()));
            transportDto.setCondition(electricScooter.getCondition());
            transportDto.setBattery(electricScooter.getBattery());
            transportDto.setMaxSpeed(electricScooter.getMaxSpeed());
        } else {
            transportDto.setId(transport.getId());
            transportDto.setType(transport.getType());
            transportDto.setCurrentParking(Parking.toParkingDto(transport.getCurrentParking()));
            transportDto.setCondition(transport.getCondition());
            transportDto.setBattery(-1);
            transportDto.setMaxSpeed(-1);
        }
        return transportDto;
    }



}