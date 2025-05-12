package org.etfbl.iprental.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MalfunctionDTO {
    private Integer id;
    private String description;
    private Instant dateAndTime;
    private String vehicleId; // To avoid sending the full VehicleEntity
}
