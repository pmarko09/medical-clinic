package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.model.Patient;
import com.pmarko09.medical_clinic.model.dto.PatientDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDto(Patient patient);

}
