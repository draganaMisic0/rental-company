package org.etfbl.iprental.controllers;

import org.etfbl.iprental.models.DTO.BicycleDTO;
import org.etfbl.iprental.services.BicycleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles/bicycles")
public class BicycleController {

    private final BicycleService bicycleService;

    public BicycleController(BicycleService bicycleService) {
        this.bicycleService = bicycleService;
    }

    @PostMapping
    public ResponseEntity<BicycleDTO> addBicycle(@RequestBody BicycleDTO bicycleDTO) {
        return ResponseEntity.ok(bicycleService.addBicycle(bicycleDTO));
    }

    @GetMapping
    public ResponseEntity<List<BicycleDTO>> getAllBicycles() {
        return ResponseEntity.ok(bicycleService.getAllBicycles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BicycleDTO> getBicycleById(@PathVariable String id) {
        return ResponseEntity.ok(bicycleService.getBicycleById(id));
    }

    @PutMapping
    public ResponseEntity<BicycleDTO> updateBicycle(@RequestBody BicycleDTO bicycleDTO) {
        return ResponseEntity.ok(bicycleService.updateBicycle(bicycleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBicycle(@PathVariable String id) {
        bicycleService.deleteBicycle(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllBicycles() {
        bicycleService.deleteAllBicycles();
        return ResponseEntity.noContent().build();
    }
}
