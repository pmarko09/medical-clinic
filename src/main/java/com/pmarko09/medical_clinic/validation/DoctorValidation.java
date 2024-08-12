package com.pmarko09.medical_clinic.validation;

import com.pmarko09.medical_clinic.exception.IllegalDoctorDataException;
import com.pmarko09.medical_clinic.model.Doctor;

public class DoctorValidation {

    public static void validateDoctorData(Doctor doctor) {
        if (doctor.getFirstName() == null) {
            throw new IllegalDoctorDataException("Doctor's firstname can not be null.");
        }
        if (doctor.getLastName() == null) {
            throw new IllegalDoctorDataException("Doctor's lastname can not be null.");
        }
        if (doctor.getEmail() == null) {
            throw new IllegalDoctorDataException("Doctor's email can not be null.");
        }
    }
}
