package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.BicycleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BicycleRepository extends JpaRepository<BicycleEntity, String> {
}
