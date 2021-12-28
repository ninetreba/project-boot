package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

// клиент - поиск по статусу, типу, парковке
    // admin - создание всех типов, удаление(изм статуса), поиск по `статусу`, типу, парковке

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CreateTransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.TransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Transport;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.TransportStatus;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;

import java.util.List;

public interface VehicleService {
// createBicycle
// createElectricScooter
// создание всех типов

    List<TransportDto> getTransportAll();
    TransportDto getTransport(Long id);
    TransportDto deleteTransport(Long id);
    List<TransportDto> findByType(Type type);
    List<TransportDto> findByParking(Long id);
    List<TransportDto> findByStatus(Status status);
    List<TransportDto> findByTransportStatusAndStatus(TransportStatus transportStatus, Status status);
    TransportDto createTransport(CreateTransportDto createTransportDto);
    Transport getById(Long aLong);
    void save(Transport transport);
    Transport findByNumber(String number);

}


