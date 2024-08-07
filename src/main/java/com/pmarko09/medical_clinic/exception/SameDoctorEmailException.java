package com.pmarko09.medical_clinic.exception;

public class SameDoctorEmailException extends RuntimeException{
    public SameDoctorEmailException(String email) {
        super("Doctor with email " + email + " already exist.");
    }
}
