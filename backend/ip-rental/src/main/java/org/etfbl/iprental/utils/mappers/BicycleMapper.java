package org.etfbl.iprental.utils.mappers;

import org.etfbl.iprental.models.BicycleEntity;
import org.etfbl.iprental.models.DTO.BicycleDTO;
import org.etfbl.iprental.models.ManufacturerEntity;
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
        vehicle.setRentalPrice(dto.getRentalPrice());
        vehicle.setStatus(dto.getStatus());
        vehicle.setPhotoUrl(dto.getPhotoUrl());


        ManufacturerEntity tempEntity = new ManufacturerEntity();
        tempEntity.setId(dto.getManufacturerId());
        vehicle.setManufacturer(tempEntity);

        return vehicle;
    }

    public BicycleEntity toBicycleEntity(BicycleDTO dto) {
        BicycleEntity bicycle = new BicycleEntity();
        bicycle.setRange(dto.getRange());
        return bicycle;
    }
}
