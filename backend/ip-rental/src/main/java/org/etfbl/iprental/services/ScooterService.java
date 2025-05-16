package org.etfbl.iprental.services;

import org.etfbl.iprental.models.ScooterEntity;
import org.etfbl.iprental.models.DTO.ScooterDTO;
import org.etfbl.iprental.models.VehicleEntity;
import org.etfbl.iprental.repositories.ScooterRepository;
import org.etfbl.iprental.repositories.VehicleRepository;
import org.etfbl.iprental.utils.mappers.ScooterMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScooterService {

    private final ScooterRepository scooterRepository;
    private final VehicleRepository vehicleRepository;
    private final ScooterMapper scooterMapper;

    public ScooterService(ScooterRepository scooterRepository, VehicleRepository vehicleRepository, ScooterMapper scooterMapper) {
        this.scooterRepository = scooterRepository;
        this.vehicleRepository = vehicleRepository;
        this.scooterMapper = scooterMapper;
    }

    public ScooterDTO addScooter(ScooterDTO scooterDTO) {
        VehicleEntity vehicle = scooterMapper.toVehicleEntity(scooterDTO);

        if(scooterDTO.getStatus() == null)
        {
            vehicle.setStatus("available");
        }
        else{
            vehicle.setStatus(scooterDTO.getStatus());
        }

        VehicleEntity savedVehicle = vehicleRepository.save(vehicle);

        ScooterEntity scooter = scooterMapper.toScooterEntity(scooterDTO);
        scooter.setVehicle(savedVehicle);

        scooterRepository.save(scooter);
        return scooterMapper.toDto(scooter, savedVehicle);
    }

    public List<ScooterDTO> getAllScooters() {
        return scooterRepository.findAll().stream()
                .map(scooter -> scooterMapper.toDto(scooter, scooter.getVehicle()))
                .collect(Collectors.toList());
    }

    public ScooterDTO getScooterById(String id) {
        ScooterEntity scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scooter not found with ID: " + id));
        return scooterMapper.toDto(scooter, scooter.getVehicle());
    }

    public ScooterDTO updateScooter(ScooterDTO scooterDTO) {
        ScooterEntity existingScooter = scooterRepository.findById(scooterDTO.getId())
                .orElseThrow(() -> new RuntimeException("Scooter not found with ID: " + scooterDTO.getId()));

        VehicleEntity updatedVehicle = scooterMapper.toVehicleEntity(scooterDTO);
        updatedVehicle.setId(scooterDTO.getId());
        VehicleEntity savedVehicle = vehicleRepository.save(updatedVehicle);

        ScooterEntity updatedScooter = scooterMapper.toScooterEntity(scooterDTO);
        updatedScooter.setVehicle(savedVehicle);
        updatedScooter.setVehicleId(savedVehicle.getId());

        ScooterEntity savedScooter = scooterRepository.save(updatedScooter);
        return scooterMapper.toDto(savedScooter, savedVehicle);
    }

    public void deleteScooter(String id) {
        scooterRepository.deleteById(id);
        vehicleRepository.deleteById(id);
    }

    public void deleteAllScooters() {
        scooterRepository.deleteAll();
    }
}
