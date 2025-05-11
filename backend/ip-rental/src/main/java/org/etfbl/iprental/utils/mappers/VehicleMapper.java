package org.etfbl.iprental.utils.mappers;

import org.etfbl.iprental.models.DTO.VehicleDTO;
import org.etfbl.iprental.models.ManufacturerEntity;
import org.etfbl.iprental.models.VehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {
    public VehicleDTO toDto(VehicleEntity vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setModel(vehicle.getModel());
        dto.setPurchasePrice(vehicle.getPurchasePrice());
        dto.setPhotoUrl(vehicle.getPhotoUrl());
        dto.setRentalPrice(vehicle.getRentalPrice());
        dto.setManufacturerId(vehicle.getManufacturer().getId());
        return dto;
    }

    public VehicleEntity toEntity(VehicleDTO dto, ManufacturerEntity manufacturer) {
        VehicleEntity entity = new VehicleEntity();
        entity.setId(dto.getId());
        entity.setModel(dto.getModel());
        entity.setPurchasePrice(dto.getPurchasePrice());
        entity.setPhotoUrl(dto.getPhotoUrl());
        entity.setRentalPrice(dto.getRentalPrice());
        entity.setManufacturer(manufacturer);
        return entity;
    }
}
