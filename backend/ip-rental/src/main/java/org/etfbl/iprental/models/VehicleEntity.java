package org.etfbl.iprental.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "vehicle")
public class VehicleEntity {
    @Id
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Column(name = "model", nullable = false, length = 45)
    private String model;

    @Column(name = "purchase_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "photo_url", nullable = true, length = 256)
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private ManufacturerEntity manufacturer;

    @Column(name = "rental_price", nullable = false, precision = 8, scale = 2)
    private BigDecimal rentalPrice;

    @PrePersist     // Instructs the JPA to automatically set the ID before insertion
    public void generateId() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
    }

}