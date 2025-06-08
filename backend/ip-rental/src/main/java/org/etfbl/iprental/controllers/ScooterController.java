package org.etfbl.iprental.controllers;

import org.etfbl.iprental.models.DTO.ScooterDTO;
import org.etfbl.iprental.services.ScooterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles/scooters")
public class ScooterController {

    private final ScooterService scooterService;

    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @PostMapping
    public ResponseEntity<ScooterDTO> addScooter(@RequestBody ScooterDTO scooterDTO) {
        return ResponseEntity.ok(scooterService.addScooter(scooterDTO));
    }

    @GetMapping
    public ResponseEntity<List<ScooterDTO>> getAllScooters() {
        return ResponseEntity.ok(scooterService.getAllScooters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScooterDTO> getScooterById(@PathVariable String id) {
        return ResponseEntity.ok(scooterService.getScooterById(id));
    }

    @PutMapping
    public ResponseEntity<ScooterDTO> updateScooter(@RequestBody ScooterDTO scooterDTO) {
        return ResponseEntity.ok(scooterService.updateScooter(scooterDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScooter(@PathVariable String id) {
        scooterService.deleteScooter(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllScooters() {
        scooterService.deleteAllScooters();
        return ResponseEntity.noContent().build();
    }
}
