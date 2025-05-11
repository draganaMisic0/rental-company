package org.etfbl.iprental.services;

import org.etfbl.iprental.models.CarEntity;
import org.etfbl.iprental.models.DTO.CarDTO;
import org.etfbl.iprental.models.VehicleEntity;
import org.etfbl.iprental.repositories.CarRepository;
import org.etfbl.iprental.repositories.VehicleRepository;
import org.etfbl.iprental.utils.mappers.CarMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final VehicleRepository vehicleRepository;
    private final CarMapper carMapper;

    public CarService(CarRepository carRepository, VehicleRepository vehicleRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.vehicleRepository = vehicleRepository;
        this.carMapper = carMapper;
    }

    /// Adds both to the vehicle table, and the Car table its corresponding data
    public CarDTO addCar(CarDTO carDTO) {
        VehicleEntity vehicle = carMapper.toVehicleEntity(carDTO);
        VehicleEntity savedVehicle = vehicleRepository.save(vehicle);

        CarEntity car = carMapper.toCarEntity(carDTO);
        car.setVehicle(savedVehicle);
        car.setVehicleId(savedVehicle.getId());

        carRepository.save(car);
        return carMapper.toDto(car, savedVehicle);
    }

    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(car -> carMapper.toDto(car, car.getVehicle()))
                .collect(Collectors.toList());
    }

    public CarDTO getCarById(String id) {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with ID: " + id));
        return carMapper.toDto(car, car.getVehicle());
    }

    public CarDTO updateCar(CarDTO carDTO) {
        CarEntity existingCar = carRepository.findById(carDTO.getId())
                .orElseThrow(() -> new RuntimeException("Car not found with ID: " + carDTO.getId()));

        VehicleEntity updatedVehicle = carMapper.toVehicleEntity(carDTO);
        updatedVehicle.setId(carDTO.getId());
        VehicleEntity savedVehicle = vehicleRepository.save(updatedVehicle);

        CarEntity updatedCar = carMapper.toCarEntity(carDTO);
        updatedCar.setVehicle(savedVehicle);
        updatedCar.setVehicleId(savedVehicle.getId());

        CarEntity savedCar = carRepository.save(updatedCar);
        return carMapper.toDto(savedCar, savedVehicle);
    }

    public void deleteCar(String id) {
        carRepository.deleteById(id);
        vehicleRepository.deleteById(id);
    }

    public void deleteAllCars() {
        carRepository.deleteAll();

    }
}
