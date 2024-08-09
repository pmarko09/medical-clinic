package com.pmarko09.medical_clinic.exception;

public class IllegalDoctorDataException extends RuntimeException{
    public IllegalDoctorDataException(String message) {
        super(message);
    }
}
