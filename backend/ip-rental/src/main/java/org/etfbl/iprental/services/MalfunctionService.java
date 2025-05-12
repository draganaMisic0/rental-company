package org.etfbl.iprental.services;

import org.etfbl.iprental.models.DTO.MalfunctionDTO;
import org.etfbl.iprental.models.MalfunctionEntity;
import org.etfbl.iprental.models.VehicleEntity;
import org.etfbl.iprental.repositories.MalfunctionRepository;
import org.etfbl.iprental.repositories.VehicleRepository;
import org.etfbl.iprental.utils.mappers.MalfunctionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MalfunctionService {

    private final MalfunctionRepository malfunctionRepository;
    private final VehicleRepository vehicleRepository;
    private final MalfunctionMapper malfunctionMapper;

    public MalfunctionService(MalfunctionRepository malfunctionRepository,
                              VehicleRepository vehicleRepository,
                              MalfunctionMapper malfunctionMapper) {
        this.malfunctionRepository = malfunctionRepository;
        this.vehicleRepository = vehicleRepository;
        this.malfunctionMapper = malfunctionMapper;
    }

    public MalfunctionDTO addMalfunction(MalfunctionDTO dto) {
        MalfunctionEntity entity = malfunctionMapper.toEntity(dto);
        VehicleEntity vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + dto.getVehicleId()));
        entity.setVehicle(vehicle);

        MalfunctionEntity saved = malfunctionRepository.save(entity);
        return malfunctionMapper.toDto(saved);
    }

    public List<MalfunctionDTO> getAllMalfunctions() {
        return malfunctionRepository.findAll().stream()
                .map(malfunctionMapper::toDto)
                .collect(Collectors.toList());
    }

    public MalfunctionDTO getMalfunctionById(Integer id) {
        MalfunctionEntity malfunction = malfunctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Malfunction not found with ID: " + id));
        return malfunctionMapper.toDto(malfunction);
    }

    public MalfunctionDTO updateMalfunction(Integer id, MalfunctionDTO dto) {
        MalfunctionEntity existing = malfunctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Malfunction not found with ID: " + id));

        existing.setDescription(dto.getDescription());
        existing.setDateAndTime(dto.getDateAndTime());

        if (dto.getVehicleId() != null && !dto.getVehicleId().equals(existing.getVehicle().getId())) {
            VehicleEntity vehicle = vehicleRepository.findById(dto.getVehicleId())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + dto.getVehicleId()));
            existing.setVehicle(vehicle);
        }

        MalfunctionEntity saved = malfunctionRepository.save(existing);
        return malfunctionMapper.toDto(saved);
    }

    public void deleteMalfunction(Integer id) {
        malfunctionRepository.deleteById(id);
    }

    public void deleteAllMalfunctions() {
        malfunctionRepository.deleteAll();
    }
}
