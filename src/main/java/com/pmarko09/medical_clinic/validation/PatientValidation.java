package com.pmarko09.medical_clinic.validation;

import com.pmarko09.medical_clinic.exception.IllegalPatientDataException;
import com.pmarko09.medical_clinic.exception.PatientAlreadyExistException;
import com.pmarko09.medical_clinic.model.Patient;
import com.pmarko09.medical_clinic.repository.PatientRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidation {

    public static void validatePatientData(Patient patient) {
        if (patient.getFirstName() == null) {
            throw new IllegalPatientDataException("Patient's firstname can not be null.");
        }
        if (patient.getLastName() == null) {
            throw new IllegalPatientDataException("Patient's lastname can not be null.");
        }
        if (patient.getEmail() == null) {
            throw new IllegalPatientDataException("Patient's email can not be null.");
        }
    }

    public static void patientEmailInUse(PatientRepository patientRepository, String email) {
        if (patientRepository.patientExists(email)) {
            throw new PatientAlreadyExistException(email);
        }
    }

}
