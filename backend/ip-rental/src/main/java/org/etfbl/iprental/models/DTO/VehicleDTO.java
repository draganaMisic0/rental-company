package org.etfbl.iprental.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private String id;
    private String model;
    private BigDecimal purchasePrice;
    private String photoUrl;
    private String status;
    private BigDecimal rentalPrice;
    private Integer manufacturerId;
}
