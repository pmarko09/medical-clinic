package com.pmarko09.medical_clinic.exception.doctor;

public class DoctorAlreadyExistException extends RuntimeException {
    public DoctorAlreadyExistException(String email) {
        super(String.format("Doctor with email %s already exists.", email));
    }
}
