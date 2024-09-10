package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.model.DoctorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor toDoctor(DoctorDTO doctorDTO);

    DoctorDTO toDto(Doctor doctor);
}
