package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.MalfunctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MalfunctionRepository extends JpaRepository<MalfunctionEntity, Integer> {
}
