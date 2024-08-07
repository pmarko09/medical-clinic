package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.DoctorNotFoundException;
import com.pmarko09.medical_clinic.exception.SameDoctorEmailException;
import com.pmarko09.medical_clinic.exception.WrongDoctorDataException;
import com.pmarko09.medical_clinic.model.ChangePasswordCommand;
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
        if (doctorRepository.emailDoctorExist(doctor.getEmail())) {
            throw new SameDoctorEmailException(doctor.getEmail());
        }
        if (doctor.getFirstName() == null) {
            throw new WrongDoctorDataException("Firstname can not be null.");
        }
        if (doctor.getLastName() == null) {
            throw new WrongDoctorDataException("Lastname can not be null.");
        }
        if (doctor.getEmail() == null) {
            throw new WrongDoctorDataException("Email can not be null.");
        }
        return doctorRepository.addDoctor(doctor);
    }

    public Doctor getDoctor(String email) {
        return doctorRepository.getDoctor(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
    }

    public Doctor removeDoctor(String email) {
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
