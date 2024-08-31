package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.DoctorNotFoundException;
import com.pmarko09.medical_clinic.exception.DoctorAlreadyExistException;
import com.pmarko09.medical_clinic.exception.IllegalDoctorDataException;
import com.pmarko09.medical_clinic.mapper.DoctorMapper;
import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.model.DoctorDTO;
import com.pmarko09.medical_clinic.repository.DoctorRepository;
import com.pmarko09.medical_clinic.validation.DoctorValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
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

    public DoctorDTO getDoctorDto(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        return doctorMapper.toDto(doctor);
    }

    public DoctorDTO deleteDoctorDto(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        doctorRepository.delete(doctor);
        return doctorMapper.toDto(doctor);
    }

    public DoctorDTO editDoctor(String email, Doctor updatedDoctor) {
        doctorRepository.findByEmail(updatedDoctor.getEmail())
                .filter(existingDoctor -> !existingDoctor.getEmail().equals(email))
                .ifPresent(existingDoctor -> {
                    throw new DoctorAlreadyExistException(updatedDoctor.getEmail());
                });

        DoctorValidation.validateDoctorData(updatedDoctor);

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        doctor.setFirstName(updatedDoctor.getFirstName());
        doctor.setLastName(updatedDoctor.getLastName());
        doctor.setEmail(updatedDoctor.getEmail());
        doctor.setPassword(updatedDoctor.getPassword());
        doctor.setSpecialization(updatedDoctor.getSpecialization());
        doctor.setPatientList(updatedDoctor.getPatientList());

        return doctorMapper.toDto(doctorRepository.save(doctor));

    }

    public DoctorDTO changeDoctorPassword(String email, String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalDoctorDataException(("Password can not be null or empty."));
        }

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        doctor.setPassword(newPassword);

        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

}
