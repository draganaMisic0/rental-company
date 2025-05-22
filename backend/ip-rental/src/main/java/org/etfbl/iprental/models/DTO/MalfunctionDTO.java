package org.etfbl.iprental.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MalfunctionDTO {
    private Integer id;
    private String description;
    private LocalDateTime dateAndTime;
    private String vehicleId;
    private String vehicleModel;
}
