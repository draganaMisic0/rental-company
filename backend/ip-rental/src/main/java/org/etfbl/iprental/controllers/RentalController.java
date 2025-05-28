package org.etfbl.iprental.controllers;

import org.etfbl.iprental.models.DTO.RentalDTO;
import org.etfbl.iprental.services.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<RentalDTO> addRental(@RequestBody RentalDTO dto) {
        return ResponseEntity.ok(rentalService.addRental(dto));
    }

    @GetMapping
    public ResponseEntity<List<RentalDTO>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    @GetMapping("/latest_unique")
    public ResponseEntity<List<RentalDTO>> getLatestRentalsPerVehicle() {
        return ResponseEntity.ok(rentalService.getLatestRentalsPerVehicle());
    }

    @GetMapping("/by_veh/{id}")
    public ResponseEntity<List<RentalDTO>> getRentalsByVehicleId(@PathVariable String id) {
        return ResponseEntity.ok(rentalService.getRentalsByVehicleId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable Integer id, @RequestBody RentalDTO dto) {
        return ResponseEntity.ok(rentalService.updateRental(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllRentals() {
        rentalService.deleteAllRentals();
        return ResponseEntity.noContent().build();
    }
}
