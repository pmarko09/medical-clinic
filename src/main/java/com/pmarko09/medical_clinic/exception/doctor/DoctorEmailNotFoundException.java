package com.pmarko09.medical_clinic.exception.doctor;

public class DoctorEmailNotFoundException extends RuntimeException {

    public DoctorEmailNotFoundException(String email) {
        super(String.format("Doctor with given email %s not found.", email));
    }
}
