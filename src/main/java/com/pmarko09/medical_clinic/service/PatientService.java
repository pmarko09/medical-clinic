package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.patient.PatientNotFoundException;
import com.pmarko09.medical_clinic.mapper.PatientMapper;
import com.pmarko09.medical_clinic.model.model.Patient;
import com.pmarko09.medical_clinic.model.DTO.PatientDTO;
import com.pmarko09.medical_clinic.repository.PatientRepository;
import com.pmarko09.medical_clinic.validation.PasswordValidation;
import com.pmarko09.medical_clinic.validation.PatientValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDTO> getPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();
    }

    public Patient addPatientByEmail(Patient patient) {
        PatientValidation.patientEmailInUse(patientRepository, patient.getEmail());
        PatientValidation.validatePatientData(patient);
        return patientRepository.save(patient);
    }

    public PatientDTO getPatientDtoByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
        return patientMapper.toDto(patient);
    }

    public PatientDTO deletePatientDtoByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
        patientRepository.delete(patient);
        return patientMapper.toDto(patient);
    }

    public PatientDTO editPatientByEmail(String email, Patient editedPatient) {
        PatientValidation.patientAlreadyExist(patientRepository, email, editedPatient);
        PatientValidation.validatePatientData(editedPatient);

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
        Patient.update(patient, editedPatient);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    public PatientDTO changePassword(String email, String newPassword) {
        PasswordValidation.validate(newPassword);

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
        patient.setPassword(newPassword);

        return patientMapper.toDto(patientRepository.save(patient));
    }
}
