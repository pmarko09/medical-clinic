package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.doctor.DoctorNotFoundException;
import com.pmarko09.medical_clinic.mapper.DoctorMapper;
import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.model.DoctorDTO;
import com.pmarko09.medical_clinic.model.Patient;
import com.pmarko09.medical_clinic.repository.DoctorRepository;
import com.pmarko09.medical_clinic.repository.PatientRepository;
import com.pmarko09.medical_clinic.validation.DoctorValidation;
import com.pmarko09.medical_clinic.validation.PasswordValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorMapper doctorMapper;

    public List<DoctorDTO> getDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    public Doctor addDoctor(Doctor doctor) {
        DoctorValidation.doctorEmailInUse(doctorRepository, doctor.getEmail());
        DoctorValidation.validateDoctorData(doctor);
        return doctorRepository.save(doctor);
    }

    public DoctorDTO getDoctorDtoByEmail(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        return doctorMapper.toDto(doctor);
    }

    public DoctorDTO deleteDoctorDtoByEmail(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        doctorRepository.delete(doctor);
        return doctorMapper.toDto(doctor);
    }

    public DoctorDTO editDoctorDtoByEmail(String email, Doctor editedDoctor) {
        DoctorValidation.doctorAlreadyExists(doctorRepository, email, editedDoctor);
        DoctorValidation.validateDoctorData(editedDoctor);

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        Doctor.update(doctor, editedDoctor);
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    public DoctorDTO changeDoctorPassword(String email, String newPassword) {
        PasswordValidation.validate(newPassword);

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        doctor.setPassword(newPassword);

        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    public DoctorDTO addPatientToDoctor(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        doctor.getPatients().add(patient);
        doctorRepository.save(doctor);

        return doctorMapper.toDto(doctor);
    }

    public DoctorDTO removePatientFromDoctor(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        doctor.getPatients().remove(patient);
        doctorRepository.save(doctor);

        return doctorMapper.toDto(doctor);
    }
}
