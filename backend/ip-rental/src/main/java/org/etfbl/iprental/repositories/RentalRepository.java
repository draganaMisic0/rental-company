package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.MalfunctionEntity;
import org.etfbl.iprental.models.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalRepository extends JpaRepository<RentalEntity, Integer> {

    @Query("SELECT r FROM RentalEntity r WHERE r.vehicle.id = :vehicleId")
    List<RentalEntity> findAllByVehicleId(@Param("vehicleId") String vehicleId);
}
