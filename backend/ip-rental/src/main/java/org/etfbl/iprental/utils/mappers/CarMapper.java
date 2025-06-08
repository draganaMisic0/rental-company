package org.etfbl.iprental.utils.mappers;

import org.etfbl.iprental.models.CarEntity;
import org.etfbl.iprental.models.DTO.CarDTO;
import org.etfbl.iprental.models.ManufacturerEntity;
import org.etfbl.iprental.models.VehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public CarDTO toDto(CarEntity car, VehicleEntity vehicle) {
        CarDTO dto = new CarDTO();
        dto.setId(vehicle.getId());
        dto.setModel(vehicle.getModel());
        dto.setPurchasePrice(vehicle.getPurchasePrice());
        dto.setStatus(vehicle.getStatus());
        dto.setPhotoUrl(vehicle.getPhotoUrl());
        dto.setRentalPrice(vehicle.getRentalPrice());
        dto.setManufacturerId(vehicle.getManufacturer().getId());

        dto.setPurchaseDate(car.getPurchaseDate());
        dto.setDescription(car.getDescription());

        return dto;
    }

    public VehicleEntity toVehicleEntity(CarDTO dto) {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setId(dto.getId());
        vehicle.setModel(dto.getModel());
        vehicle.setPurchasePrice(dto.getPurchasePrice());
        vehicle.setStatus(dto.getStatus());
        vehicle.setPhotoUrl(dto.getPhotoUrl());
        vehicle.setRentalPrice(dto.getRentalPrice());

        ManufacturerEntity tempEntity = new ManufacturerEntity();
        tempEntity.setId(dto.getManufacturerId());

        vehicle.setManufacturer(tempEntity);

        return vehicle;
    }

    /// Sets the Car specific attributes (for code-writing efficiency)
    public CarEntity toCarEntity(CarDTO dto) {
        CarEntity car = new CarEntity();
        car.setPurchaseDate(dto.getPurchaseDate());
        car.setDescription(dto.getDescription());
        return car;
    }
}