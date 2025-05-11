package org.etfbl.iprental.models.requests;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
    private String firstName;
    private String lastName;
    private String idNumber;
    private String email;
    private String phone;
    private String avatarUrl;
    private String username;
    private String password;
    private String passportNumber;
    private String driverLicenceNumber;
    private String citizenship;
    private Byte active;
}
