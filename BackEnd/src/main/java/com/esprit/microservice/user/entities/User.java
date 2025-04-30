package com.esprit.microservice.user.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;

    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean active = true;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Registry> registries;
    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Events> events;


    public enum Role {
        USER, ADMIN, MODERATOR
    }


}
