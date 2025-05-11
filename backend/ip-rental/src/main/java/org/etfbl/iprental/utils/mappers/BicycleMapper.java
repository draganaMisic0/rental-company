package org.etfbl.iprental.utils.mappers;

import org.etfbl.iprental.models.BicycleEntity;
import org.etfbl.iprental.models.DTO.BicycleDTO;
import org.etfbl.iprental.models.VehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class BicycleMapper {

    public BicycleDTO toDto(BicycleEntity bicycle, VehicleEntity vehicle) {
        BicycleDTO dto = new BicycleDTO();
        dto.setId(vehicle.getId());
        dto.setModel(vehicle.getModel());
        dto.setPurchasePrice(vehicle.getPurchasePrice());
        dto.setStatus(bicycle.getVehicle().getStatus());
        dto.setPhotoUrl(vehicle.getPhotoUrl());
        dto.setRentalPrice(vehicle.getRentalPrice());
        dto.setManufacturerId(vehicle.getManufacturer().getId());
        dto.setRange(bicycle.getRange());
        return dto;
    }

    public VehicleEntity toVehicleEntity(BicycleDTO dto) {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setId(dto.getId());
        vehicle.setModel(dto.getModel());
        vehicle.setPurchasePrice(dto.getPurchasePrice());
        vehicle.setStatus(dto.getStatus());
        vehicle.setPhotoUrl(dto.getPhotoUrl());
        vehicle.setRentalPrice(dto.getRentalPrice());
        return vehicle;
    }

    public BicycleEntity toBicycleEntity(BicycleDTO dto) {
        BicycleEntity bicycle = new BicycleEntity();
        bicycle.setVehicleId(dto.getId());
        bicycle.setRange(dto.getRange());
        return bicycle;
    }
}
