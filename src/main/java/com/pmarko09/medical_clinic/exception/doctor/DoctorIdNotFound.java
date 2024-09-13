package com.pmarko09.medical_clinic.exception.doctor;

public class DoctorIdNotFound extends RuntimeException {

    public DoctorIdNotFound(Long id) {
        super(String.format("Doctor with id: %s not found.", id));
    }
}
