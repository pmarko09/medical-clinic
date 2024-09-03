package com.pmarko09.medical_clinic.validation;

import com.pmarko09.medical_clinic.exception.IllegalPatientDataException;

public class PasswordValidation {

    public static void validate(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalPatientDataException("Password can not be null or empty.");
        }
    }
}
