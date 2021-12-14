package com.accenture.russiaatc.irentservice10.SNAPSHOT.controller;


import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CreateTransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.TransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Реализовать VehicleController с операциями получение списка ТС
// с поиском по типу и статусу, создание ТС, удаление ТС.

// клиент - поиск по статусу, типу, парковке
// admin - создание всех типов, удаление(изм статуса), поиск по статусу, типу, парковке

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public TransportDto createTransport(@RequestBody CreateTransportDto createTransportDto){
        return vehicleService.createTransport(createTransportDto);
    }

    @Autowired
    public VehicleController(VehicleService vehicleService) { this.vehicleService = vehicleService;}

    @GetMapping("/all") // говорит, что это гет запрос
    public List<TransportDto> getParkingList(){
        return vehicleService.getTransportAll();
    }


    @GetMapping("/{id}")
    public TransportDto getParking(@PathVariable Long id){
        return vehicleService.getById(id);
    }

    @GetMapping("/type/{type}")
    public List<TransportDto> findByType(@PathVariable Type type){
        return vehicleService.findByType(type);
    }

    @GetMapping("/parking/{name}")
    public List<TransportDto> findByParking(@PathVariable String name){
        return vehicleService.findByParking(name);
    }

    @GetMapping("/status/{status}")
    public List<TransportDto> findByParking(@PathVariable Status status){
        return vehicleService.findByStatus(status);
    }

}
