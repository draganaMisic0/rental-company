package org.etfbl.iprental.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "rental")
public class RentalEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date_and_time", nullable = false)
    private Instant dateAndTime;

    @Column(name = "start_lon", nullable = false, precision = 9, scale = 6)
    private BigDecimal startLon;

    @Column(name = "start_lat", nullable = false, precision = 9, scale = 6)
    private BigDecimal startLat;

    @Column(name = "end_lon", nullable = false, precision = 9, scale = 6)
    private BigDecimal endLon;

    @Column(name = "end_lat", nullable = false, precision = 9, scale = 6)
    private BigDecimal endLat;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;

    @Column(name = "total_price", nullable = false, precision = 8, scale = 2)
    private BigDecimal totalPrice;

}