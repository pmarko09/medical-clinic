package com.pmarko09.medical_clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "PATIENTS")
@Data
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

    @ManyToMany(mappedBy = "patients")
    private Set<Doctor> doctors;

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
