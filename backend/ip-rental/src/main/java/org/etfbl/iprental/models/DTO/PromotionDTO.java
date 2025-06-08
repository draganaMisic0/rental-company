package org.etfbl.iprental.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDate expirationDate;
    private Integer employeeId;
}
