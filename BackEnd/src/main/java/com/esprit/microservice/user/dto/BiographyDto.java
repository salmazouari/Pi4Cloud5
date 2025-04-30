package com.esprit.microservice.user.dto;

import com.esprit.microservice.user.entities.Biography;
import com.esprit.microservice.user.entities.Hobby;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BiographyDto {

    private Long id;
    private String bio;
    private Hobby hobby;



    public BiographyDto(final Biography biography) {
        this.id = biography.getId();
        this.bio = biography.getBio();
        this.hobby = biography.getHobby();
    }



    public Biography toEntity() {
        Biography biography = new Biography();
        biography.setId(this.id);
        biography.setBio(this.bio);
        biography.setHobby(this.hobby);

        return biography;
    }



    public Biography toEntity(final Long id) {
        Biography biography = new Biography();
        biography.setId(id);
        biography.setBio(this.bio);
        biography.setHobby(this.hobby);

        return biography;
    }


}
