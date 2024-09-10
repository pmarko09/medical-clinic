package com.pmarko09.medical_clinic.exception.hospital;

public class HospitalNotFoundException extends RuntimeException{

    public HospitalNotFoundException(Long id) {
        super("Hospital not found.");
    }
}
