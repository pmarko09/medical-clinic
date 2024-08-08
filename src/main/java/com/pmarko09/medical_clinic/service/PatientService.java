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
        validatePatientFirstName(patient.getFirstName());
        validatePatientLastName(patient.getLastName());
        validatePatientEmail(patient.getEmail());
        return patientRepository.addPatient(patient);
    }

    private void validatePatientFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalDoctorDataException("Firstname can not be null.");
        }
    }

    private void validatePatientLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalDoctorDataException("Lastname can not be null.");
        }
    }

    private void validatePatientEmail(String email) {
        if (email == null) {
            throw new IllegalDoctorDataException("Email can not be null.");
        }
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
}
