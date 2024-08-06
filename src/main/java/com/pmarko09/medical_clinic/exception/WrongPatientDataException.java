package com.pmarko09.medical_clinic.exception;

public class WrongPatientDataException extends RuntimeException{

    public WrongPatientDataException (String message) {
        super(message);
    }
}
