package org.etfbl.iprental.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "scooter")
public class ScooterEntity {
    @Id
    @Column(name = "vehicle_id", nullable = false, length = 64)
    private String vehicleId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;

    @Column(name = "max_speed", nullable = false)
    private Integer maxSpeed;

}