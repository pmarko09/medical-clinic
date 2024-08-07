package com.pmarko09.medical_clinic.exception;

public class WrongDoctorDataException extends RuntimeException{
    public WrongDoctorDataException(String message) {
        super(message);
    }
}
