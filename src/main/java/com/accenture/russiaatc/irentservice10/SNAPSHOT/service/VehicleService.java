package com.accenture.russiaatc.irentservice10.SNAPSHOT.service;

// клиент - поиск по статусу, типу, парковке
    // admin - создание всех типов, удаление(изм статуса), поиск по `статусу`, типу, парковке

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.TransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Transport;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;

import java.util.List;

public interface VehicleService {
// createBicycle
// createElectricScooter
// создание всех типов

    List<TransportDto> getTransportAll();
    TransportDto getById(Long id);
    TransportDto deleteTransport(Long id);
    List<TransportDto> findByType(Type type);
    List<TransportDto> findByParking(String name);
    List<TransportDto> findByStatus(Status status);

}


