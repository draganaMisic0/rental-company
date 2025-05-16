package org.etfbl.iprental.services;


import org.etfbl.iprental.models.BicycleEntity;
import org.etfbl.iprental.models.DTO.BicycleDTO;
import org.etfbl.iprental.models.VehicleEntity;
import org.etfbl.iprental.repositories.BicycleRepository;
import org.etfbl.iprental.repositories.VehicleRepository;
import org.etfbl.iprental.utils.mappers.BicycleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BicycleService {

    private final BicycleRepository bicycleRepository;
    private final VehicleRepository vehicleRepository;
    private final BicycleMapper bicycleMapper;

    public BicycleService(BicycleRepository bicycleRepository, VehicleRepository vehicleRepository, BicycleMapper bicycleMapper) {
        this.bicycleRepository = bicycleRepository;
        this.vehicleRepository = vehicleRepository;
        this.bicycleMapper = bicycleMapper;
    }

    public BicycleDTO addBicycle(BicycleDTO bicycleDTO) {
        VehicleEntity vehicle = bicycleMapper.toVehicleEntity(bicycleDTO);

        if(bicycleDTO.getStatus() == null)
        {
            vehicle.setStatus("available");
        }
        else{
            vehicle.setStatus(bicycleDTO.getStatus());
        }

        VehicleEntity savedVehicle = vehicleRepository.save(vehicle);

        BicycleEntity bicycle = bicycleMapper.toBicycleEntity(bicycleDTO);
        bicycle.setVehicle(savedVehicle);

        bicycleRepository.save(bicycle);
        return bicycleMapper.toDto(bicycle, savedVehicle);
    }

    public List<BicycleDTO> getAllBicycles() {
        return bicycleRepository.findAll().stream()
                .map(bicycle -> bicycleMapper.toDto(bicycle, bicycle.getVehicle()))
                .collect(Collectors.toList());
    }

    public BicycleDTO getBicycleById(String id) {
        BicycleEntity bicycle = bicycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bicycle not found with ID: " + id));
        return bicycleMapper.toDto(bicycle, bicycle.getVehicle());
    }

    public BicycleDTO updateBicycle(BicycleDTO bicycleDTO) {
        BicycleEntity existingBicycle = bicycleRepository.findById(bicycleDTO.getId())
                .orElseThrow(() -> new RuntimeException("Bicycle not found with ID: " + bicycleDTO.getId()));

        VehicleEntity updatedVehicle = bicycleMapper.toVehicleEntity(bicycleDTO);
        updatedVehicle.setId(bicycleDTO.getId());
        VehicleEntity savedVehicle = vehicleRepository.save(updatedVehicle);

        BicycleEntity updatedBicycle = bicycleMapper.toBicycleEntity(bicycleDTO);
        updatedBicycle.setVehicle(savedVehicle);
        updatedBicycle.setVehicleId(savedVehicle.getId());

        BicycleEntity savedBicycle = bicycleRepository.save(updatedBicycle);
        return bicycleMapper.toDto(savedBicycle, savedVehicle);
    }

    public void deleteBicycle(String id) {
        bicycleRepository.deleteById(id);
        vehicleRepository.deleteById(id);
    }

    public void deleteAllBicycles() {
        bicycleRepository.deleteAll();
    }
}
