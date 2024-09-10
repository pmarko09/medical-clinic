package com.pmarko09.medical_clinic.exception.doctor;

public class DoctorNotFoundException extends RuntimeException {

    public DoctorNotFoundException(String email) {
        super("Doctor  not found.");
    }
}
