package org.etfbl.iprental.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bicycle")
public class BicycleEntity {
    @Id
    @Column(name = "vehicle_id", nullable = false, length = 64)
    private String vehicleId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;

    @Column(name = "`range`", nullable = false)
    private Integer range;

}