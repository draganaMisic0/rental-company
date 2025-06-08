package org.etfbl.iprental.controllers;

import org.etfbl.iprental.models.DTO.PromotionDTO;
import org.etfbl.iprental.services.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping
    public ResponseEntity<PromotionDTO> addPromotion(@RequestBody PromotionDTO dto) {
        return ResponseEntity.ok(promotionService.addPromotion(dto));
    }

    @GetMapping
    public ResponseEntity<List<PromotionDTO>> getAllPromotions() {
        return ResponseEntity.ok(promotionService.getAllPromotions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionDTO> getPromotionById(@PathVariable Integer id) {
        return ResponseEntity.ok(promotionService.getPromotionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionDTO> updatePromotion(@PathVariable Integer id, @RequestBody PromotionDTO dto) {
        return ResponseEntity.ok(promotionService.updatePromotion(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Integer id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPromotions() {
        promotionService.deleteAllPromotions();
        return ResponseEntity.noContent().build();
    }
}
