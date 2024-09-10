package com.pmarko09.medical_clinic.exception.patient;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String email) {
        super(String.format("Patient with email %s not found.", email));
    }
}
