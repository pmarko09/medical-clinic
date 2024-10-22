package com.pmarko09.medical_clinic.exception.doctor;

public class DoctorNotFoundException extends RuntimeException {

    public DoctorNotFoundException(String email) {
        super(String.format("Doctor with given email %s not found.", email));
    }
}
