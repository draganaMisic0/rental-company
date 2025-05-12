package org.etfbl.iprental.utils.mappers;

import org.etfbl.iprental.models.DTO.PostDTO;
import org.etfbl.iprental.models.EmployeeEntity;
import org.etfbl.iprental.models.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDTO toDto(PostEntity entity) {
        PostDTO dto = new PostDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());

        if (entity.getEmployee() != null) {
            dto.setEmployeeId(entity.getEmployee().getId());
        }

        return dto;
    }

    public PostEntity toEntity(PostDTO dto) {
        PostEntity entity = new PostEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        // Employee is set in service
        return entity;
    }

    public void setEmployee(PostEntity entity, EmployeeEntity employee) {
        entity.setEmployee(employee);
    }
}
