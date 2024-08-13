package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.model.DoctorDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DoctorMapper {

    public static DoctorDTO toDto(Doctor doctor) {
        return new DoctorDTO(doctor.getFirstName(), doctor.getLastName(), doctor.getSpecialization(), doctor.getEmail());
    }
}
