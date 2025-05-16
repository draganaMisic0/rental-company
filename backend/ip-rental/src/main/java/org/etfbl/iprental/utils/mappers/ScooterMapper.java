package org.etfbl.iprental.utils.mappers;

import org.etfbl.iprental.models.ScooterEntity;
import org.etfbl.iprental.models.ManufacturerEntity;
import org.etfbl.iprental.models.VehicleEntity;
import org.etfbl.iprental.models.DTO.ScooterDTO;
import org.springframework.stereotype.Component;

@Component
public class ScooterMapper {

    public ScooterDTO toDto(ScooterEntity scooter, VehicleEntity vehicle) {
        ScooterDTO dto = new ScooterDTO();
        dto.setId(vehicle.getId());
        dto.setModel(vehicle.getModel());
        dto.setPurchasePrice(vehicle.getPurchasePrice());
        dto.setStatus(vehicle.getStatus());
        dto.setPhotoUrl(vehicle.getPhotoUrl());
        dto.setRentalPrice(vehicle.getRentalPrice());
        dto.setManufacturerId(vehicle.getManufacturer().getId());
        dto.setMaxSpeed(scooter.getMaxSpeed());
        return dto;
    }

    public VehicleEntity toVehicleEntity(ScooterDTO dto) {
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

    public ScooterEntity toScooterEntity(ScooterDTO dto) {
        ScooterEntity scooter = new ScooterEntity();
        scooter.setMaxSpeed(dto.getMaxSpeed());
        return scooter;
    }
}
