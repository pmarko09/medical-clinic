package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.model.Doctor;
import com.pmarko09.medical_clinic.model.model.Hospital;
import com.pmarko09.medical_clinic.model.DTO.HospitalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class HospitalMapper {

    public abstract HospitalDTO toDto(Hospital hospital);

    public abstract Hospital toHospital(HospitalDTO hospitalDTO);

    @Mapping(source = "doctors", target = "doctorsIds", qualifiedByName = "mapDoctorsToIds")
    public abstract HospitalDTO toDtoWithDoctors(Hospital hospital);

    @Named("mapDoctorsToIds")
    protected Set<Long> mapDoctorsToIds(Set<Doctor> doctors) {
        return doctors.stream()
                .map(Doctor::getId)
                .collect(Collectors.toSet());
    }
}