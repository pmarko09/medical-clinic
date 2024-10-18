package com.pmarko09.medical_clinic.exception.patient;

public class PatientNotFound extends RuntimeException {
    public PatientNotFound(Long id) {
        super(String.format("Patient with id: %s not found", id));
    }
}
