package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.model.DoctorDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DoctorDoctorDTOMapper {
    DoctorDTO doctorToDoctorDto(Doctor doctor);

    Doctor doctorDtoToDoctor(DoctorDTO doctorDTO);
}

