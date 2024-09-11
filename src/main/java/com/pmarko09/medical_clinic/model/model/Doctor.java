package com.pmarko09.medical_clinic.model.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
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

    @ManyToMany
    @JoinTable(
            name = "DOCTOR_HOSPITAL",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "hospital_id"))
    private Set<Hospital> hospitals;

    public static void update(Doctor doctor, Doctor updatedDoctor) {
        doctor.setFirstName(updatedDoctor.getFirstName());
        doctor.setLastName(updatedDoctor.getLastName());
        doctor.setEmail(updatedDoctor.getEmail());
        doctor.setPassword(updatedDoctor.getPassword());
        doctor.setSpecialization(updatedDoctor.getSpecialization());
    }
}
