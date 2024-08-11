package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.Patient;
import com.pmarko09.medical_clinic.model.PatientDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientMapper {

    public static PatientDTO toDto(Patient patient) {
        return new PatientDTO(patient.getEmail(), patient.getFirstName(), patient.getLastName(),
                patient.getPhoneNumber(), patient.getBirthday());
    }
}
