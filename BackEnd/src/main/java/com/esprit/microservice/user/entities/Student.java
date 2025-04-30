package com.esprit.microservice.user.entities;

import com.esprit.microservice.user.Speciality;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Student extends User {

    @Enumerated(EnumType.STRING)
    private Speciality speciality;


    @OneToOne
    @JoinColumn(name = "biography_id")
    private Biography biography;

    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @OneToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    private Set<Experience> experiences;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Student student = (Student) o;
        return getUserId() != null && Objects.equals(getUserId(), student.getUserId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
