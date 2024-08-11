package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.DoctorNotFoundException;
import com.pmarko09.medical_clinic.exception.DoctorAlreadyExistException;
import com.pmarko09.medical_clinic.exception.IllegalDoctorDataException;
import com.pmarko09.medical_clinic.mapper.DoctorMapper;
import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.model.DoctorDTO;
import com.pmarko09.medical_clinic.patient_validation.DoctorValidation;
import com.pmarko09.medical_clinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<DoctorDTO> getDoctors() {
        List<Doctor> doctors = doctorRepository.getDoctors();
        return doctors.stream()
                .map(DoctorMapper::toDto)
                .toList();
    }

    public Doctor addDoctor(Doctor doctor) {
        if (doctorRepository.doctorExists(doctor.getEmail())) {
            throw new DoctorAlreadyExistException(doctor.getEmail());
        }
        DoctorValidation.validateDoctorData(doctor);
        return doctorRepository.addDoctor(doctor);
    }

    public DoctorDTO getDoctorDto(String email) {
        Doctor doctor = doctorRepository.getDoctor(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        return DoctorMapper.toDto(doctor);
    }

    public DoctorDTO deleteDoctorDto(String email) {
        Doctor doctor = doctorRepository.deleteDoctor(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        return DoctorMapper.toDto(doctor);
    }

    public DoctorDTO editDoctor(String email, Doctor updatedDoctor) {
        if (doctorRepository.doctorExists(updatedDoctor.getEmail()) && !email.equals(updatedDoctor.getEmail())) {
            throw new DoctorAlreadyExistException(updatedDoctor.getEmail());
        }
        DoctorValidation.validateDoctorData(updatedDoctor);

        Doctor doctor = doctorRepository.editDoctor(email, updatedDoctor)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        return DoctorMapper.toDto(doctor);
    }

    public DoctorDTO changeDoctorPassword(String email, String newPassword) {
        Doctor doctor = doctorRepository.changeDoctorPassword(email, newPassword)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        return DoctorMapper.toDto(doctor);
    }

}
