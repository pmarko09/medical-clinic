package com.pmarko09.medical_clinic.model.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PATIENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<>();

    public static void update(Patient patient, Patient editedPatient) {
        patient.setFirstName(editedPatient.getFirstName());
        patient.setLastName(editedPatient.getLastName());
        patient.setEmail(editedPatient.getEmail());
        patient.setPassword(editedPatient.getPassword());
        patient.setPhoneNumber(editedPatient.getPhoneNumber());
        patient.setIdCardNo(editedPatient.getIdCardNo());
        patient.setBirthday(editedPatient.getBirthday());
    }
}
