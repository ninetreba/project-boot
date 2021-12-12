package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.TransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Transport;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


// клиент - поиск по статусу, типу, парковке
// admin - создание всех типов, удаление(изм статуса), поиск по статусу, типу, парковке

@Service
public class VehicleServiceImpl implements VehicleService{
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }


    public List<TransportDto> getTransportAll(){
        List<TransportDto> transportDtoList = new ArrayList<>();
        for (Transport transport : vehicleRepository.findAll()){
            transportDtoList.add(Transport.toTransportDto(transport));
        }
        return transportDtoList;
    }

    // ok
    public TransportDto getById(Long id){
        return Transport.toTransportDto(vehicleRepository.findById(id).orElseThrow());
    }


    public TransportDto deleteTransport(Long id){
        Transport transport = vehicleRepository.findById(id).orElseThrow();
        transport.setStatus(Status.DELETED);
        vehicleRepository.save(transport);
        return Transport.toTransportDto(transport);
    }


    // ok
    public List<TransportDto> findByType(Type type){
        List<TransportDto> transportDtoList = new ArrayList<>();

        for (Transport transport : vehicleRepository.findByType(type)){
            transportDtoList.add(Transport.toTransportDto(transport));
        }
        return transportDtoList;
    }

    public List<TransportDto> findByParking(String name){
        List<TransportDto> transportDtoList = new ArrayList<>();
        for (Transport transport : vehicleRepository.findByCurrentParking_name(name)){
            transportDtoList.add(Transport.toTransportDto(transport));
        }
        return transportDtoList;
    }

    public List<TransportDto> findByStatus(Status status){
        List<TransportDto> transportDtoList = new ArrayList<>();
        for (Transport transport : vehicleRepository.findByStatus(status)){
            transportDtoList.add(Transport.toTransportDto(transport));
        }
        return transportDtoList;
    }

}
