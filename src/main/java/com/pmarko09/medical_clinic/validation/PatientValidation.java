package com.pmarko09.medical_clinic.validation;

import com.pmarko09.medical_clinic.exception.patient.IllegalPatientDataException;
import com.pmarko09.medical_clinic.exception.patient.PatientAlreadyExistException;
import com.pmarko09.medical_clinic.exception.patient.PatientNotFound;
import com.pmarko09.medical_clinic.model.model.Patient;
import com.pmarko09.medical_clinic.repository.PatientRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidation {

    public static void validatePatientData(Patient patient) {
        if (patient.getFirstName() == null || patient.getFirstName().isEmpty()) {
            throw new IllegalPatientDataException("Patient's firstname can not be null.");
        }
        if (patient.getLastName() == null || patient.getLastName().isEmpty()) {
            throw new IllegalPatientDataException("Patient's lastname can not be null.");
        }
        if (patient.getEmail() == null || patient.getEmail().isEmpty()) {
            throw new IllegalPatientDataException("Patient's email can not be null.");
        }
    }

    public static void patientEmailInUse(PatientRepository patientRepository, String email) {
        if (patientRepository.findByEmail(email).isPresent()) {
            throw new PatientAlreadyExistException(email);
        }
    }

    public static void patientAlreadyExist(PatientRepository patientRepository, String email, Patient editedPatient) {
        patientRepository.findByEmail(editedPatient.getEmail())
                .filter(existingPatient -> !existingPatient.getEmail().equals(email))
                .ifPresent(existingPatient -> {
                    throw new PatientAlreadyExistException(editedPatient.getEmail());
                });
    }

    public static void patientExists(PatientRepository patientRepository, Long patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFound(patientId));
    }
}
