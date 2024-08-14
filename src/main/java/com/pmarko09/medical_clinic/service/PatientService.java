package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.PatientNotFoundException;
import com.pmarko09.medical_clinic.exception.PatientAlreadyExistException;
import com.pmarko09.medical_clinic.mapper.PatientMapper;
import com.pmarko09.medical_clinic.model.Patient;
import com.pmarko09.medical_clinic.model.PatientDTO;
import com.pmarko09.medical_clinic.validation.PatientValidation;
import com.pmarko09.medical_clinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDTO> getPatients() {
        return patientRepository.getPatients().stream()
                .map(patientMapper::toDto)
                .toList();
    }

    public Patient addPatient(Patient patient) {
        PatientValidation.patientEmailInUse(patientRepository, patient.getEmail());
        PatientValidation.validatePatientData(patient);
        return patientRepository.addPatient(patient);
    }

    public PatientDTO getPatientDto(String email) {
        Patient patient = patientRepository.getPatient(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
        return patientMapper.toDto(patient);
    }

    public PatientDTO deletePatientDto(String email) {
        Patient patient = patientRepository.deletePatient(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
        return patientMapper.toDto(patient);
    }

    public PatientDTO editPatient(String email, Patient editedPatient) {
        if (patientRepository.patientExists(editedPatient.getEmail()) && !email.equals(editedPatient.getEmail())) {
            throw new PatientAlreadyExistException(editedPatient.getEmail());
        }
        PatientValidation.validatePatientData(editedPatient);

        Patient editedPatient1 = patientRepository.editPatient(email, editedPatient)
                .orElseThrow(() -> new PatientNotFoundException(email));
        return patientMapper.toDto(editedPatient1);
    }

    public PatientDTO changePassword(String email, String newPassword) {
        Patient patient = patientRepository.changePassword(email, newPassword)
                .orElseThrow(() -> new PatientNotFoundException(email));

        return patientMapper.toDto(patient);
    }

}
