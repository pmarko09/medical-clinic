package com.pmarko09.medical_clinic.repository;

import com.pmarko09.medical_clinic.exception.PatientAlreadyExistException;
import com.pmarko09.medical_clinic.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

    private final List<Patient> patients;

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public Patient addPatient(Patient patient) {
        patients.add(patient);
        return patient;
    }

    public boolean patientExists(String email) {
        return getPatient(email).isPresent();
    }

    public Optional<Patient> getPatient(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Patient> deletePatient(String email) {
        Optional<Patient> patientToBeRemoved = getPatient(email);

        patientToBeRemoved.ifPresent(patients::remove);

        return patientToBeRemoved;
    }

    public Optional<Patient> editPatient(String email, Patient editedPatient) {
        if (patientExists(editedPatient.getEmail()) && !email.equals(editedPatient.getEmail())) {
            throw new PatientAlreadyExistException(email);
        }
        return getPatient(email).map(patient -> {
            patient.setFirstName(editedPatient.getFirstName());
            patient.setLastName(editedPatient.getLastName());
            patient.setEmail(editedPatient.getEmail());
            patient.setPassword(editedPatient.getPassword());
            patient.setIdCardNo(editedPatient.getIdCardNo());
            patient.setPhoneNumber(editedPatient.getPhoneNumber());
            patient.setBirthday(editedPatient.getBirthday());
            return patient;
        });
    }

    public Optional<Patient> changePassword(String email, String newPassword) {
        Optional<Patient> patient = getPatient(email);

        patient.ifPresent(pt -> pt.setPassword(newPassword));

        return patient;
    }
}
