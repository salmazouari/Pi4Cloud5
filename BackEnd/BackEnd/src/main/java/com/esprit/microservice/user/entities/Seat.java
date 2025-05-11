package com.esprit.microservice.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "Seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "placement", nullable = false)
    private String placement;  // Seat placement (e.g., "Row A, Seat 1")

    @ManyToOne

    @JoinColumn(name = "event_id", referencedColumnName = "EventID")
    private Events event;  // Associate the seat with an event

    @Column(name = "is_booked", nullable = false)
    private Boolean isBooked = false;
    @ManyToOne
    @JoinColumn(name = "registry_id")
    private Registry registry;

}
