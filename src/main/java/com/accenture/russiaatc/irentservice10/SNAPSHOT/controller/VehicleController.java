package com.accenture.russiaatc.irentservice10.SNAPSHOT.controller;


import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.CreateTransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.TransportDto;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.transport.Type;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;


    @GetMapping
    public List<TransportDto> getVehicles(){
        return vehicleService.getVehicles();
    }


    @GetMapping("/{id}")
    public TransportDto getVehicle(@PathVariable Long id){
        return vehicleService.getTransport(id);
    }

    @PostMapping
    public TransportDto createTransport(@RequestBody CreateTransportDto createTransportDto){
        return vehicleService.createTransport(createTransportDto);
    }

    @DeleteMapping("/{id}")
    public TransportDto deleteParking(@PathVariable Long id){
        return vehicleService.deleteTransport(id);
    }

    @GetMapping("/type/{type}")
    public List<TransportDto> findByType(@PathVariable Type type){
        return vehicleService.findByType(type);
    }

    @GetMapping("/parking/{id}")
    public List<TransportDto> findByParking(@PathVariable Long id){
        return vehicleService.findByParking(id);
    }

    @GetMapping("/status/{status}")
    public List<TransportDto> findByParking(@PathVariable Status status){
        return vehicleService.findByStatus(status);
    }

}
