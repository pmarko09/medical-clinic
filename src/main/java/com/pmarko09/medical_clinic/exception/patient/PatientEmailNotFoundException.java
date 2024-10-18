package com.pmarko09.medical_clinic.exception.patient;

public class PatientEmailNotFoundException extends RuntimeException {
    public PatientEmailNotFoundException(String email) {
        super(String.format("Patient with email %s not found.", email));
    }
}
