package com.pmarko09.medical_clinic.exception;

public class IllegalPatientDataException extends RuntimeException{
    public IllegalPatientDataException(String message) {
        super(message);
    }
}
