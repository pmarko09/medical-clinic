package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.IllegalDoctorDataException;
import com.pmarko09.medical_clinic.exception.PatientNotFoundException;
import com.pmarko09.medical_clinic.exception.PatientAlreadyExistException;
import com.pmarko09.medical_clinic.exception.IllegalPatientDataException;
import com.pmarko09.medical_clinic.model.Patient;
import com.pmarko09.medical_clinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getPatients() {
        return patientRepository.getPatients();
    }

    public Patient addPatient(Patient patient) {
        if (patientRepository.patientExists(patient.getEmail())) {
            throw new PatientAlreadyExistException(patient.getEmail());
        }
        validatePatientData(patient);
        return patientRepository.addPatient(patient);
    }

    public Patient getPatient(String email) {
        return patientRepository.getPatient(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
    }

    public Patient deletePatient(String email) {
        return patientRepository.deletePatient(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
    }

    public Patient editPatient(String email, Patient editedPatient) {
        return patientRepository.editPatient(email, editedPatient)
                .orElseThrow(() -> new PatientNotFoundException(email));
    }

    public Patient changePassword(String email, String newPassword) {
        return patientRepository.changePassword(email, newPassword)
                .orElseThrow(() -> new PatientNotFoundException(email));
    }

    private void validatePatientData(Patient patient) {
        if (patient.getFirstName() == null) {
            throw new IllegalDoctorDataException("Firstname can not be null.");
        }
        if (patient.getLastName() == null) {
            throw new IllegalDoctorDataException("Lastname can not be null.");
        }
        if (patient.getEmail() == null) {
            throw new IllegalDoctorDataException("Email can not be null.");
        }
    }

}
