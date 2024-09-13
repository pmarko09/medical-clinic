package com.pmarko09.medical_clinic.exception.doctor;

public class DoctorAlreadyExistsException extends RuntimeException {
    public DoctorAlreadyExistsException(String email) {
        super(String.format("Doctor with email %s already exists.", email));
    }
}
