package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.model.DoctorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "string")
public interface DoctorMapper {

    DoctorDTO toDto(Doctor doctor);

    Doctor toDoctor(DoctorDTO doctorDTO);

}


