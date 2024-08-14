package com.pmarko09.medical_clinic.model;

import lombok.Data;

import java.util.List;

@Data
public class Doctor {

    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
    private String password;
    private List<Patient> patientList;

}
