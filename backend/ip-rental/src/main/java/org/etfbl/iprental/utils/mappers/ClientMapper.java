package org.etfbl.iprental.utils.mappers;

import org.etfbl.iprental.models.ClientEntity;
import org.etfbl.iprental.models.DTO.ClientDTO;
import org.etfbl.iprental.models.requests.ClientRequest;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDTO toDto(ClientEntity entity) {
        if (entity == null) {
            return null;
        }

        ClientDTO dto = new ClientDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setIdNumber(entity.getIdNumber());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAvatarUrl(entity.getAvatarUrl());
        dto.setUsername(entity.getUsername());
        dto.setPassportNumber(entity.getPassportNumber());
        dto.setDriverLicenceNumber(entity.getDriverLicenceNumber());
        dto.setCitizenship(entity.getCitizenship());
        dto.setActive(entity.getActive());

        return dto;
    }

    public ClientEntity toEntity(ClientRequest request) {
        if (request == null) {
            return null;
        }

        ClientEntity entity = new ClientEntity();

        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setIdNumber(request.getIdNumber());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setAvatarUrl(request.getAvatarUrl());
        entity.setUsername(request.getUsername());
        entity.setPassportNumber(request.getPassportNumber());
        entity.setDriverLicenceNumber(request.getDriverLicenceNumber());
        entity.setCitizenship(request.getCitizenship());
        entity.setActive(request.getActive());

        return entity;
    }
}
