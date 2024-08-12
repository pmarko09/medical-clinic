package com.pmarko09.medical_clinic.validation;

import com.pmarko09.medical_clinic.exception.DoctorAlreadyExistException;
import com.pmarko09.medical_clinic.exception.IllegalDoctorDataException;
import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.repository.DoctorRepository;

public class DoctorValidation {

    public static void validateDoctorData(Doctor doctor) {
        if (doctor.getFirstName() == null) {
            throw new IllegalDoctorDataException("Doctor's firstname can not be null.");
        }
        if (doctor.getLastName() == null) {
            throw new IllegalDoctorDataException("Doctor's lastname can not be null.");
        }
        if (doctor.getEmail() == null) {
            throw new IllegalDoctorDataException("Doctor's email can not be null.");
        }
    }

    public static void doctorEmailInUse(DoctorRepository doctorRepository, String email) {
        if (doctorRepository.doctorExists(email)) {
            throw new DoctorAlreadyExistException(email);
        }
    }
}
