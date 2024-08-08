package com.pmarko09.medical_clinic.exception;

public class PatientAlreadyExistException extends RuntimeException {
    public PatientAlreadyExistException(String email) {
        super(String.format("Patient with given email %s already exists in the system.", email));
    }
}
