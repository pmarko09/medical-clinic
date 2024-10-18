package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.patient.PatientEmailNotFoundException;
import com.pmarko09.medical_clinic.mapper.PatientMapper;
import com.pmarko09.medical_clinic.model.model.Patient;
import com.pmarko09.medical_clinic.model.dto.PatientDTO;
import com.pmarko09.medical_clinic.repository.PatientRepository;
import com.pmarko09.medical_clinic.validation.PasswordValidation;
import com.pmarko09.medical_clinic.validation.PatientValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDTO> getPatients(Pageable pageable) {
        return patientRepository.findAllPatients(pageable).stream()
                .map(patientMapper::toDto)
                .toList();
    }

    @Transactional
    public PatientDTO addPatient(Patient patient) {
        PatientValidation.patientEmailInUse(patientRepository, patient.getEmail());
        PatientValidation.validatePatientData(patient);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    public PatientDTO getPatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientEmailNotFoundException(email));
        return patientMapper.toDto(patient);
    }

    @Transactional
    public PatientDTO deletePatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientEmailNotFoundException(email));
        patientRepository.delete(patient);
        return patientMapper.toDto(patient);
    }

    @Transactional
    public PatientDTO editPatientByEmail(String email, Patient editedPatient) {
        PatientValidation.patientAlreadyExist(patientRepository, email, editedPatient);
        PatientValidation.validatePatientData(editedPatient);

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientEmailNotFoundException(email));
        Patient.update(patient, editedPatient);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    @Transactional
    public PatientDTO changePassword(String email, String newPassword) {
        PasswordValidation.validate(newPassword);

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientEmailNotFoundException(email));
        patient.setPassword(newPassword);

        return patientMapper.toDto(patientRepository.save(patient));
    }
}
