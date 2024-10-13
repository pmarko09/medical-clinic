package com.pmarko09.medical_clinic.model.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "DOCTORS")
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "DOCTOR_HOSPITAL",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "hospital_id"))
    private Set<Hospital> hospitals = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Doctor other)) {
            return false;
        }

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", specialization=" + specialization +
                ", email=" + email +
                ", password=" + password +
                ", hospitals=" + hospitals.stream().map(Hospital::getId).toList();
    }

    public static void update(Doctor doctor, Doctor updatedDoctor) {
        doctor.setFirstName(updatedDoctor.getFirstName());
        doctor.setLastName(updatedDoctor.getLastName());
        doctor.setEmail(updatedDoctor.getEmail());
        doctor.setPassword(updatedDoctor.getPassword());
        doctor.setSpecialization(updatedDoctor.getSpecialization());
    }
}
