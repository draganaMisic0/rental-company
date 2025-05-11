package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.ScooterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScooterRepository extends JpaRepository<ScooterEntity, String> {
}
