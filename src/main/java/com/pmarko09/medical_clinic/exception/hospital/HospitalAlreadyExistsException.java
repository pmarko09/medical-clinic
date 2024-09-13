package com.pmarko09.medical_clinic.exception.hospital;

public class HospitalAlreadyExistsException extends RuntimeException {

    public HospitalAlreadyExistsException(Long id) {
        super("Hospital with id " + id + " already exists.");
    }
}
