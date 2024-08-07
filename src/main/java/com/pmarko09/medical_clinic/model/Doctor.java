package com.pmarko09.medical_clinic.model;

import lombok.Data;

@Data
public class Doctor {

    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
    private String password;

}
