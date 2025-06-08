package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.MalfunctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MalfunctionRepository extends JpaRepository<MalfunctionEntity, Integer> {

        List<MalfunctionEntity> findByVehicleId(String id);
}
