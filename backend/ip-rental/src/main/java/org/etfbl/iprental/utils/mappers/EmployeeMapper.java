package org.etfbl.iprental.utils.mappers;

import org.etfbl.iprental.models.DTO.EmployeeDTO;
import org.etfbl.iprental.models.EmployeeEntity;
import org.etfbl.iprental.models.requests.EmployeeRequest;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDTO toDto(EmployeeEntity entity) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    public EmployeeEntity toEntity(EmployeeRequest requestDTO) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setFirstName(requestDTO.getFirstName());
        entity.setLastName(requestDTO.getLastName());
        entity.setRole(requestDTO.getRole());
        entity.setUsername(requestDTO.getUsername());
        entity.setPassword(requestDTO.getPassword());
        return entity;
    }
}
