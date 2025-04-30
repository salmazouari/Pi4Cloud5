package com.esprit.microservice.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(mappedBy = "contact")
    private Student student;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private String linkedin;

    @Column
    private String address;
}
