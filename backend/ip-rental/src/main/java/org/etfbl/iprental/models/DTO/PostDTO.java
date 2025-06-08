package org.etfbl.iprental.models.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer employeeId;
}
