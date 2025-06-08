package org.etfbl.iprental.utils.mappers;


import org.etfbl.iprental.models.DTO.PromotionDTO;
import org.etfbl.iprental.models.EmployeeEntity;
import org.etfbl.iprental.models.PromotionEntity;
import org.springframework.stereotype.Component;

@Component
public class PromotionMapper {

    public PromotionDTO toDto(PromotionEntity entity) {
        PromotionDTO dto = new PromotionDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setExpirationDate(entity.getExpirationDate());

        if (entity.getEmployee() != null) {
            dto.setEmployeeId(entity.getEmployee().getId());
        }

        return dto;
    }

    public PromotionEntity toEntity(PromotionDTO dto) {
        PromotionEntity entity = new PromotionEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setExpirationDate(dto.getExpirationDate());
        return entity;
    }

    public void setEmployee(PromotionEntity entity, EmployeeEntity employee) {
        entity.setEmployee(employee);
    }
}
