package com.pmarko09.medical_clinic.patient_validation;

import com.pmarko09.medical_clinic.exception.IllegalDoctorDataException;
import com.pmarko09.medical_clinic.model.Patient;

public class PatientValidation {

    public static void validatePatientData(Patient patient) {
        if (patient.getFirstName() == null) {
            throw new IllegalDoctorDataException("Patient's firstname can not be null.");
        }
        if (patient.getLastName() == null) {
            throw new IllegalDoctorDataException("Patient's lastname can not be null.");
        }
        if (patient.getEmail() == null) {
            throw new IllegalDoctorDataException("Patient's email can not be null.");
        }
    }
}
