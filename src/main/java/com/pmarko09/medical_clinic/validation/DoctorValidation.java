package com.pmarko09.medical_clinic.validation;

import com.pmarko09.medical_clinic.exception.doctor.DoctorAlreadyExistsException;
import com.pmarko09.medical_clinic.exception.doctor.IllegalDoctorDataException;
import com.pmarko09.medical_clinic.model.model.Doctor;
import com.pmarko09.medical_clinic.repository.DoctorRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DoctorValidation {

    public static void validateDoctorData(Doctor doctor) {
        if (doctor.getFirstName() == null || doctor.getFirstName().isEmpty()) {
            throw new IllegalDoctorDataException("Doctor's firstname can not be null.");
        }
        if (doctor.getLastName() == null || doctor.getLastName().isEmpty()) {
            throw new IllegalDoctorDataException("Doctor's lastname can not be null.");
        }
        if (doctor.getEmail() == null || doctor.getEmail().isEmpty()) {
            throw new IllegalDoctorDataException("Doctor's email can not be null.");
        }
    }

    public static void doctorEmailInUse(DoctorRepository doctorRepository, String email) {
        if (doctorRepository.findByEmail(email).isPresent()) {
            throw new DoctorAlreadyExistsException(email);
        }
    }

    public static void doctorAlreadyExists(DoctorRepository doctorRepository, String email, Doctor updatedDoctor) {
        doctorRepository.findByEmail(updatedDoctor.getEmail())
                .filter(existingDoctor -> !existingDoctor.getEmail().equals(email))
                .ifPresent(existingDoctor -> {
                    throw new DoctorAlreadyExistsException(updatedDoctor.getEmail());
                });
    }
}
