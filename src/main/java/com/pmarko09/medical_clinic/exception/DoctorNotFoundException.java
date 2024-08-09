package com.pmarko09.medical_clinic.exception;

public class DoctorNotFoundException extends RuntimeException {

    public DoctorNotFoundException(String email) {
        super(String.format("Doctor with email %s not found.", email));
    }
}
