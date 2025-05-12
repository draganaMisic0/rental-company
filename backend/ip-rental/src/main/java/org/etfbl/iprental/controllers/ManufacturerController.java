package org.etfbl.iprental.controllers;

import org.etfbl.iprental.models.ManufacturerEntity;
import org.etfbl.iprental.services.ManufacturerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @PostMapping
    public ResponseEntity<ManufacturerEntity> addManufacturer(@RequestBody ManufacturerEntity manufacturer) {
        return ResponseEntity.ok(manufacturerService.addManufacturer(manufacturer));
    }

    @GetMapping
    public ResponseEntity<List<ManufacturerEntity>> getAllManufacturers() {
        return ResponseEntity.ok(manufacturerService.getAllManufacturers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerEntity> getManufacturerById(@PathVariable Integer id) {
        return ResponseEntity.ok(manufacturerService.getManufacturerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerEntity> updateManufacturer(@RequestBody ManufacturerEntity manufacturer) {
        if(manufacturer.getId() == null || manufacturer.getId() == 0){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(manufacturerService.updateManufacturer(manufacturer.getId(), manufacturer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Integer id) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllManufacturers() {
        manufacturerService.deleteAllManufacturers();
        return ResponseEntity.noContent().build();
    }
}
