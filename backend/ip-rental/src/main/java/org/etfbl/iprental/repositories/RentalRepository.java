package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<RentalEntity, Integer> {
}
