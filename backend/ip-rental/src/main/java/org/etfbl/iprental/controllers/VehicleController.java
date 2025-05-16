package org.etfbl.iprental.controllers;


import org.etfbl.iprental.models.DTO.VehicleDTO;
import org.etfbl.iprental.models.VehicleEntity;
import org.etfbl.iprental.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable String id) {
        return ResponseEntity.ok(vehicleService.getVehicle(id));
    }

    @PostMapping
    public ResponseEntity<VehicleEntity> createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        VehicleEntity created = vehicleService.addVehicle(vehicleDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllVehicles() {
        vehicleService.deleteAllVehicles();
        return ResponseEntity.noContent().build();
    }
}
