package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository  extends JpaRepository<PromotionEntity, Integer> {
}
