package org.etfbl.iprental.services;

import jakarta.transaction.Transactional;
import org.etfbl.iprental.models.DTO.VehicleDTO;
import org.etfbl.iprental.models.ManufacturerEntity;
import org.etfbl.iprental.models.VehicleEntity;
import org.etfbl.iprental.repositories.ManufacturerRepository;
import org.etfbl.iprental.repositories.VehicleRepository;
import org.etfbl.iprental.utils.mappers.VehicleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepo;

    private final ManufacturerRepository manufacturerRepo;

    private final VehicleMapper vehicleMapper;

    public VehicleService(VehicleRepository vehicleRepo,
                          ManufacturerRepository manufacturerRepo,
                          VehicleMapper vehicleMapper ) {
        this.vehicleMapper = vehicleMapper;
        this.manufacturerRepo = manufacturerRepo;
        this.vehicleRepo = vehicleRepo;
    }

    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepo.findAll().stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    public VehicleDTO getVehicle(String id) {
        VehicleEntity vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        return vehicleMapper.toDto(vehicle);
    }

    @Transactional
    public VehicleEntity addVehicle(VehicleDTO dto) {
        ManufacturerEntity manufacturer = manufacturerRepo.findById(dto.getManufacturerId())
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));

        VehicleEntity vehicle = vehicleMapper.toEntity(dto, manufacturer);

        VehicleEntity returnedVehicle = vehicleRepo.save(vehicle);
        return (returnedVehicle);
    }

    public void deleteVehicle(String id) {
        vehicleRepo.deleteById(id);
    }

    public void deleteAllVehicles() {
        vehicleRepo.deleteAll();
    }
}
