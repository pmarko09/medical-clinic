package com.pmarko09.medical_clinic.exception;

public class DoctorNotFoundException extends RuntimeException{

    public DoctorNotFoundException (String email) {
        super("Doctor with email " + email + " not found.");
    }
}
