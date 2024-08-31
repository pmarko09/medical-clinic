package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.IllegalPatientDataException;
import com.pmarko09.medical_clinic.exception.PatientNotFoundException;
import com.pmarko09.medical_clinic.exception.PatientAlreadyExistException;
import com.pmarko09.medical_clinic.mapper.PatientMapper;
import com.pmarko09.medical_clinic.model.Patient;
import com.pmarko09.medical_clinic.model.PatientDTO;
import com.pmarko09.medical_clinic.repository.PatientRepository;
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

    public Patient addPatient(Patient patient) {
        PatientValidation.patientEmailInUse(patientRepository, patient.getEmail());
        PatientValidation.validatePatientData(patient);
        return patientRepository.save(patient);
    }

    public PatientDTO getPatientDto(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
        return patientMapper.toDto(patient);
    }

    public PatientDTO deletePatientDto(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
        patientRepository.delete(patient);
        return patientMapper.toDto(patient);
    }

    public PatientDTO editPatient(String email, Patient editedPatient) {
        patientRepository.findByEmail(editedPatient.getEmail())
                .filter(existingPatient -> !existingPatient.getEmail().equals(email))
                .ifPresent(existingPatient -> {
                    throw new PatientAlreadyExistException(editedPatient.getEmail());
                });

        PatientValidation.validatePatientData(editedPatient);

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
        patient.setFirstName(editedPatient.getFirstName());
        patient.setLastName(editedPatient.getLastName());
        patient.setEmail(editedPatient.getEmail());
        patient.setPassword(editedPatient.getPassword());
        patient.setPhoneNumber(editedPatient.getPhoneNumber());
        patient.setIdCardNo(editedPatient.getIdCardNo());
        patient.setBirthday(editedPatient.getBirthday());

        return patientMapper.toDto(patientRepository.save(patient));
    }

    public PatientDTO changePassword(String email, String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalPatientDataException("Password can not be null or empty.");
        }

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
        patient.setPassword(newPassword);

        return patientMapper.toDto(patientRepository.save(patient));
    }

}
