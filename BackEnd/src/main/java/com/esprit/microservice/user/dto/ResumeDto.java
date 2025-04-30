package com.esprit.microservice.user.dto;

import com.esprit.microservice.user.entities.Resume;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResumeDto {

    private Long id;
    private boolean hasPhoto;
    private String color;

    public ResumeDto(final Resume resume) {
        this.id = resume.getId();
        this.color = resume.getColor();
        this.hasPhoto = resume.isHasPhoto();
    }

    public Resume toEntity() {
        Resume resume = new Resume();
        resume.setId(this.id);
        resume.setColor(this.color);
        resume.setHasPhoto(this.hasPhoto);

        return resume;
    }
}
