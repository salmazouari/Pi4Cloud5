package com.esprit.microservice.user.dto;

import com.esprit.microservice.user.entities.Experience;
import com.esprit.microservice.user.entities.Student;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ExperienceDto {

    private Long id;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;



    public ExperienceDto(final Experience experience ) {

        this.id = experience.getId();
        this.description = experience.getDescription();
        this.startDate = experience.getStartDate();
        this.endDate = experience.getEndDate();
    }



    public Experience toEntity() {

        Experience experience = new Experience();
        experience.setId(this.id);
        experience.setDescription(this.description);
        experience.setStartDate(this.startDate);
        experience.setEndDate(this.endDate);

        return experience;
    }
}
