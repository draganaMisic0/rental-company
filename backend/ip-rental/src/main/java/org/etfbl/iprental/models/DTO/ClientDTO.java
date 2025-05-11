package org.etfbl.iprental.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String email;
    private String phone;
    private String avatarUrl;
    private String username;
    private String passportNumber;
    private String driverLicenceNumber;
    private String citizenship;
    private Byte active;
}
