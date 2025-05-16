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
    protected String id;
    protected String model;
    protected BigDecimal purchasePrice;
    protected String photoUrl;
    protected String status;
    protected BigDecimal rentalPrice;
    protected Integer manufacturerId;
}
