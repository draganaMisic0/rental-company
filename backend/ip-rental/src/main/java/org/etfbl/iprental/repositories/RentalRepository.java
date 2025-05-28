package org.etfbl.iprental.repositories;

import org.etfbl.iprental.models.MalfunctionEntity;
import org.etfbl.iprental.models.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface RentalRepository extends JpaRepository<RentalEntity, Integer> {

    @Query("SELECT r FROM RentalEntity r WHERE r.vehicle.id = :vehicleId")
    List<RentalEntity> findAllByVehicleId(@Param("vehicleId") String vehicleId);

    @Query("""
    SELECT r FROM RentalEntity r 
    WHERE r.dateAndTime = (
        SELECT MAX(r2.dateAndTime) FROM RentalEntity r2 WHERE r2.vehicle.id = r.vehicle.id
    )
    """)
    List<RentalEntity> findLatestRentalsPerVehicle();

    @Query("SELECT r FROM RentalEntity r WHERE r.dateAndTime BETWEEN :start AND :end ORDER BY r.dateAndTime ASC")
    List<RentalEntity> findByDateAndTimeBetweenOrderByDateAndTimeAsc(Instant start, Instant end);
}
