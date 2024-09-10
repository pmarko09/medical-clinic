package com.pmarko09.medical_clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToMany
    @JoinTable(
            name = "DOCTOR_PATIENT",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private Set<Patient> patients;

    public static void update(Doctor doctor, Doctor updatedDoctor) {
        doctor.setFirstName(updatedDoctor.getFirstName());
        doctor.setLastName(updatedDoctor.getLastName());
        doctor.setEmail(updatedDoctor.getEmail());
        doctor.setPassword(updatedDoctor.getPassword());
        doctor.setSpecialization(updatedDoctor.getSpecialization());
    }
}
