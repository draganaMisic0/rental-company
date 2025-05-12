package org.etfbl.iprental.controllers;

import org.etfbl.iprental.models.DTO.CarDTO;
import org.etfbl.iprental.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // GET /cars
    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    // GET /cars/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable String id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    // POST /cars
    @PostMapping
    public ResponseEntity<CarDTO> addCar(@RequestBody CarDTO carDTO) {
        CarDTO created = carService.addCar(carDTO);
        return ResponseEntity.ok(created);
    }

    // PUT /cars/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable String id, @RequestBody CarDTO carDTO) {
        carDTO.setId(id);
        CarDTO updated = carService.updateCar(carDTO);
        return ResponseEntity.ok(updated);
    }

    // DELETE /cars/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable String id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    // DELETE /cars
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllCars() {
        carService.deleteAllCars();
        return ResponseEntity.noContent().build();
    }
}