package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, String> {
}
