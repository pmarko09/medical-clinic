package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.Hospital;
import com.pmarko09.medical_clinic.model.HospitalDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HospitalMapper {

    HospitalDTO toDto(Hospital hospital);

    Hospital toHospital(HospitalDTO hospitalDTO);
}
