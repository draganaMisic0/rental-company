package org.etfbl.iprental.utils.mappers;

import org.etfbl.iprental.models.ClientEntity;
import org.etfbl.iprental.models.DTO.RentalDTO;
import org.etfbl.iprental.models.RentalEntity;
import org.etfbl.iprental.models.VehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {

    public RentalDTO toDto(RentalEntity entity) {
        RentalDTO dto = new RentalDTO();
        dto.setId(entity.getId());
        dto.setDateAndTime(entity.getDateAndTime());
        dto.setStartLat(entity.getStartLat());
        dto.setStartLon(entity.getStartLon());
        dto.setEndLat(entity.getEndLat());
        dto.setEndLon(entity.getEndLon());
        dto.setDuration(entity.getDuration());
        dto.setTotalPrice(entity.getTotalPrice());

        if (entity.getClient() != null) {
            dto.setClientId(entity.getClient().getId());
            dto.setClientName(entity.getClient().getFirstName() + " " + entity.getClient().getLastName());
        }
        if (entity.getVehicle() != null) {
            dto.setVehicleId(entity.getVehicle().getId());
            dto.setVehicleModel(entity.getVehicle().getModel());
        }

        return dto;
    }

    public RentalEntity toEntity(RentalDTO dto) {
        RentalEntity entity = new RentalEntity();
        entity.setId(dto.getId());
        entity.setDateAndTime(dto.getDateAndTime());
        entity.setStartLat(dto.getStartLat());
        entity.setStartLon(dto.getStartLon());
        entity.setEndLat(dto.getEndLat());
        entity.setEndLon(dto.getEndLon());
        entity.setDuration(dto.getDuration());
        entity.setTotalPrice(dto.getTotalPrice());

        return entity;
    }

    public void setVehicle(RentalEntity entity, VehicleEntity vehicle) {
        entity.setVehicle(vehicle);
    }

    public void setClient(RentalEntity entity, ClientEntity client) {
        entity.setClient(client);
    }
}
