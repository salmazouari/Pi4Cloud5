package com.esprit.microservice.user.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Registry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Registry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "school")
    private String school;

    // One Registry can have multiple Seats
    @OneToMany(mappedBy = "registry", cascade = CascadeType.ALL)
    private Set<Seat> seats;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Expects {id} in JSON

}
