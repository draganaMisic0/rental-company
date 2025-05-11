package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<CarEntity, String> {
}
