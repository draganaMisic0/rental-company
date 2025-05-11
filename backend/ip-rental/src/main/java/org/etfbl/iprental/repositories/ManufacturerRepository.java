package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.ManufacturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository  extends JpaRepository<ManufacturerEntity, Integer> {
}
