package com.pmarko09.medical_clinic.exception;

public class SamePatientEmailException extends RuntimeException{

    public SamePatientEmailException (String email) {
        super("Patient with given email " + email + " already exists in the system.");
    }
}
