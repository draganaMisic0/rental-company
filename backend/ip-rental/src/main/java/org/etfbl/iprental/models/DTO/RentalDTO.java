package org.etfbl.iprental.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO {
    private Integer id;
    private Instant dateAndTime;
    private BigDecimal startLon;
    private BigDecimal startLat;
    private BigDecimal endLon;
    private BigDecimal endLat;
    private Integer duration;
    private Integer clientId;
    private String  clientName;
    private String vehicleId;
    private String vehicleModel;
    private BigDecimal totalPrice;
}
