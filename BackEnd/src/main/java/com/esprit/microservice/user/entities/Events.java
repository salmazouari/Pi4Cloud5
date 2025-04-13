package com.esprit.microservice.user.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Events implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EventID")
    private Long EventID;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Location")
    private String location;

    @Column(name = "StartTime")
    private LocalDateTime startTime;

    @Column(name = "EndTime")
    private LocalDateTime endTime;

    @Column(name = "MaxAttendees")
    private Long maxAttendees;

    @Column(name = "HostedBy")
    private String hostedBy;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user1;

}
