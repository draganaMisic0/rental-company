package org.etfbl.iprental.services;

import jakarta.transaction.Transactional;
import org.etfbl.iprental.models.CarEntity;
import org.etfbl.iprental.models.DTO.CarDTO;
import org.etfbl.iprental.models.ManufacturerEntity;
import org.etfbl.iprental.models.VehicleEntity;
import org.etfbl.iprental.repositories.CarRepository;
import org.etfbl.iprental.repositories.VehicleRepository;
import org.etfbl.iprental.utils.mappers.CarMapper;
import org.etfbl.iprental.utils.mappers.VehicleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;
    private final CarMapper carMapper;
    private final VehicleMapper vehicleMapper;
    private final ManufacturerService manufacturerService;

    public CarService(CarRepository carRepository, VehicleRepository vehicleRepository, VehicleService vehicleService, CarMapper carMapper, VehicleMapper vehicleMapper, ManufacturerService manufacturerService) {
        this.carRepository = carRepository;
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = vehicleService;
        this.carMapper = carMapper;
        this.vehicleMapper = vehicleMapper;
        this.manufacturerService = manufacturerService;
    }

    /// Adds both to the vehicle table, and the Car table its corresponding data
    @Transactional
    public CarDTO addCar(CarDTO carDTO) {
        VehicleEntity vehicle = carMapper.toVehicleEntity(carDTO);

        if(carDTO.getStatus() == null)
        {
            vehicle.setStatus("available");
        }
        else{
            vehicle.setStatus(carDTO.getStatus());
        }

        VehicleEntity savedVehicle = vehicleRepository.save(vehicle);

        CarEntity car = carMapper.toCarEntity(carDTO);
        car.setVehicle(savedVehicle);

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

        existingCar.setPurchaseDate(carDTO.getPurchaseDate());
        existingCar.setDescription(carDTO.getDescription());
        VehicleEntity vehicleFromCar = existingCar.getVehicle();
        vehicleFromCar.setStatus(carDTO.getStatus());
        vehicleFromCar.setModel(carDTO.getModel());
        vehicleFromCar.setPurchasePrice(carDTO.getPurchasePrice());
        vehicleFromCar.setRentalPrice(carDTO.getRentalPrice());
        ManufacturerEntity myManufacturer = new ManufacturerEntity();
        myManufacturer.setId(carDTO.getManufacturerId());

        vehicleFromCar.setManufacturer(myManufacturer);
        vehicleFromCar.setPhotoUrl(carDTO.getPhotoUrl());

        VehicleEntity updatedVehicle = vehicleRepository.save(vehicleFromCar);
        existingCar.setVehicle(updatedVehicle);


        CarEntity savedCar = carRepository.save(existingCar);
        return carMapper.toDto(savedCar, updatedVehicle);
    }

    public void deleteCar(String id) {
        carRepository.deleteById(id);
        vehicleRepository.deleteById(id);
    }

    public void deleteAllCars() {
        carRepository.deleteAll();

    }
}
