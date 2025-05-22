package org.etfbl.iprental.utils.mappers;


import org.etfbl.iprental.models.DTO.MalfunctionDTO;
import org.etfbl.iprental.models.MalfunctionEntity;
import org.etfbl.iprental.models.VehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class MalfunctionMapper {

    public MalfunctionDTO toDto(MalfunctionEntity entity) {
        MalfunctionDTO dto = new MalfunctionDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setDateAndTime(entity.getDateAndTime());
        if (entity.getVehicle() != null) {
            dto.setVehicleId(entity.getVehicle().getId());
            dto.setVehicleModel(entity.getVehicle().getModel());
        }

        return dto;
    }

    public MalfunctionEntity toEntity(MalfunctionDTO dto) {
        MalfunctionEntity entity = new MalfunctionEntity();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setDateAndTime(dto.getDateAndTime());

        VehicleEntity tempVehicleEntity = new VehicleEntity();
        tempVehicleEntity.setId(tempVehicleEntity.getId());
        tempVehicleEntity.setModel(dto.getVehicleModel());

        entity.setVehicle(tempVehicleEntity);

        return entity;
    }

    public void setVehicle(MalfunctionEntity malfunction, VehicleEntity vehicle) {
        malfunction.setVehicle(vehicle);
    }
}
