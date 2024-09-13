package com.pmarko09.medical_clinic.validation;

import com.pmarko09.medical_clinic.exception.hospital.IllegalHospitalDataException;
import com.pmarko09.medical_clinic.model.model.Hospital;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HospitalValidation {

    public static void validateHospitalData(Hospital hospital) {
        if (hospital.getName() == null || hospital.getName().isEmpty()) {
            throw new IllegalHospitalDataException("Hospital's name can not be null.");
        }
        if (hospital.getCity() == null || hospital.getCity().isEmpty()) {
            throw new IllegalHospitalDataException("Hospital's city can not be null.");
        }
    }
}