package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.PatientNotFoundException;
import com.pmarko09.medical_clinic.exception.SamePatientEmailException;
import com.pmarko09.medical_clinic.exception.WrongPatientDataException;
import com.pmarko09.medical_clinic.model.Patient;
import com.pmarko09.medical_clinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
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
        if (patientRepository.emailAlreadyAdded(patient.getEmail())) {
            throw new SamePatientEmailException(patient.getEmail());
        }
        if (patient.getFirstName() == null || patient.getLastName() == null || patient.getIdCardNo() == null) {
            throw new WrongPatientDataException("Firstname, lastname or id card can not be empty.");
        }
        return patientRepository.addPatient(patient);
    }

    public Patient getPatient(String email) {
        return patientRepository.getPatient(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
    }

    public Patient removePatient(String email) {
        return patientRepository.removePatient(email)
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
