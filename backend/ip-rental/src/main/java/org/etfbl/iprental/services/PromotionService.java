package org.etfbl.iprental.services;


import org.etfbl.iprental.models.DTO.PromotionDTO;
import org.etfbl.iprental.models.EmployeeEntity;
import org.etfbl.iprental.models.PromotionEntity;
import org.etfbl.iprental.repositories.EmployeeRepository;
import org.etfbl.iprental.repositories.PromotionRepository;
import org.etfbl.iprental.utils.mappers.PromotionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final EmployeeRepository employeeRepository;
    private final PromotionMapper promotionMapper;

    public PromotionService(PromotionRepository promotionRepository, EmployeeRepository employeeRepository, PromotionMapper promotionMapper) {
        this.promotionRepository = promotionRepository;
        this.employeeRepository = employeeRepository;
        this.promotionMapper = promotionMapper;
    }

    public PromotionDTO addPromotion(PromotionDTO dto) {
        PromotionEntity entity = promotionMapper.toEntity(dto);

        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + dto.getEmployeeId()));

        promotionMapper.setEmployee(entity, employee);

        PromotionEntity saved = promotionRepository.save(entity);
        return promotionMapper.toDto(saved);
    }

    public List<PromotionDTO> getAllPromotions() {
        return promotionRepository.findAll().stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    public PromotionDTO getPromotionById(Integer id) {
        PromotionEntity promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found with ID: " + id));
        return promotionMapper.toDto(promotion);
    }

    public PromotionDTO updatePromotion(Integer id, PromotionDTO dto) {
        PromotionEntity existing = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found with ID: " + id));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setExpirationDate(dto.getExpirationDate());

        if (dto.getEmployeeId() != null && (existing.getEmployee() == null || !existing.getEmployee().getId().equals(dto.getEmployeeId()))) {
            EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + dto.getEmployeeId()));
            existing.setEmployee(employee);
        }

        PromotionEntity saved = promotionRepository.save(existing);
        return promotionMapper.toDto(saved);
    }

    public void deletePromotion(Integer id) {
        promotionRepository.deleteById(id);
    }

    public void deleteAllPromotions() {
        promotionRepository.deleteAll();
    }
}
