package org.etfbl.iprental.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "id_number", length = 45)
    private String idNumber;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "phone", nullable = false, length = 45)
    private String phone;

    @Column(name = "avatar_url", length = 256)
    private String avatarUrl;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Column(name = "passport_number", length = 45)
    private String passportNumber;

    @Column(name = "driver_licence_number", nullable = false, length = 45)
    private String driverLicenceNumber;

    @Lob
    @Column(name = "citizenship", nullable = false)
    private String citizenship;

    @Column(name = "active", nullable = false)
    private Byte active;

}