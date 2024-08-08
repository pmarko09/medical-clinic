package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.DoctorNotFoundException;
import com.pmarko09.medical_clinic.exception.DoctorAlreadyExistException;
import com.pmarko09.medical_clinic.exception.IllegalDoctorDataException;
import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<Doctor> getDoctors() {
        return doctorRepository.getDoctors();
    }

    public Doctor addDoctor(Doctor doctor) {
        if (doctorRepository.doctorExists(doctor.getEmail())) {
            throw new DoctorAlreadyExistException(doctor.getEmail());
        }
        validateDoctorFirstName(doctor.getFirstName());
        validateDoctorLastName(doctor.getLastName());
        validateDoctorEmail(doctor.getEmail());
        return doctorRepository.addDoctor(doctor);
    }

    private void validateDoctorFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalDoctorDataException("Firstname can not be null.");
        }
    }

    private void validateDoctorLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalDoctorDataException("Lastname can not be null.");
        }
    }

    private void validateDoctorEmail(String email) {
        if (email == null) {
            throw new IllegalDoctorDataException("Email can not be null.");
        }
    }

    public Doctor getDoctor(String email) {
        return doctorRepository.getDoctor(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
    }

    public Doctor deleteDoctor(String email) {
        return doctorRepository.deleteDoctor(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
    }

    public Doctor editDoctor(String email, Doctor newDoctor) {
        return doctorRepository.editDoctor(email, newDoctor)
                .orElseThrow(() -> new DoctorNotFoundException(email));
    }

    public Doctor changeDoctorPassword(String email, String newPassword) {
        return doctorRepository.changeDoctorPassword(email, newPassword)
                .orElseThrow(() -> new DoctorNotFoundException(email));
    }
}
