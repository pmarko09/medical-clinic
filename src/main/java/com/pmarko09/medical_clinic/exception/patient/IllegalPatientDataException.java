package com.pmarko09.medical_clinic.exception.patient;

public class IllegalPatientDataException extends RuntimeException{
    public IllegalPatientDataException(String message) {
        super(message);
    }
}
