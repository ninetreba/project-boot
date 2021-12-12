package com.accenture.russiaatc.irentservice10.SNAPSHOT.controller;


import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.TransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
// Реализовать VehicleController с операциями получение списка ТС
// с поиском по типу и статусу, создание ТС, удаление ТС.


@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) { this.vehicleService = vehicleService;}

    @GetMapping("/all")
    public List<TransportDto> getParkingList(){
        return vehicleService.getTransportAll();
    }


    @GetMapping("/{id}")
    public TransportDto getUser(@PathVariable Long id){
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
