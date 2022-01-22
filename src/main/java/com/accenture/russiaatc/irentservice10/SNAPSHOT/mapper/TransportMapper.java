package com.accenture.russiaatc.irentservice10.SNAPSHOT.mapper;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.TransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.ElectricScooter;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Transport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ParkingMapper.class)
public interface TransportMapper {
    @Mapping(source = "currentParking", target = "currentParking")
    TransportDto electricScooterToDto(ElectricScooter electricScooter);
    TransportDto bicycleToDto(Transport transport);
}
