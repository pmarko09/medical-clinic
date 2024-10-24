package com.pmarko09.medical_clinic.exception.patient;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(Long id) {
        super(String.format("Patient with id: %s not found", id));
    }
}
