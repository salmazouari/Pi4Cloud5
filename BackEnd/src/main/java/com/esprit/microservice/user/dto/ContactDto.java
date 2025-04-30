package com.esprit.microservice.user.dto;

import com.esprit.microservice.user.entities.Biography;
import com.esprit.microservice.user.entities.Contact;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactDto {

    private Long id;
    private String phoneNumber;
    private String email;
    private String linkedin;
    private String address;


    public ContactDto(final Contact contact) {
        this.id = contact.getId();
        this.phoneNumber = contact.getPhoneNumber();
        this.email = contact.getEmail();
        this.linkedin = contact.getLinkedin();
        this.address = contact.getAddress();
    }





    public Contact toEntity() {
        Contact contact = new Contact();
        contact.setId(this.id);
        contact.setPhoneNumber(this.phoneNumber);
        contact.setEmail(this.email);
        contact.setLinkedin(this.linkedin);
        contact.setAddress(this.address);

        return contact;
    }
}
