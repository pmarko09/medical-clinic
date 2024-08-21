package com.pmarko09.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
    private String password;
    private List<Patient> patientList;

}
