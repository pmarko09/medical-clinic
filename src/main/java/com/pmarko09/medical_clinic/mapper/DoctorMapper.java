package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.model.Doctor;
import com.pmarko09.medical_clinic.model.dto.DoctorDTO;
import com.pmarko09.medical_clinic.model.model.Hospital;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class DoctorMapper {

    public abstract DoctorDTO toDto(Doctor doctor);

    public abstract Doctor toDoctor(DoctorDTO doctorDTO);

    @Mapping(source = "hospitals", target = "hospitalsIds", qualifiedByName = "mapHospitalsToIds")
    public abstract DoctorDTO toDtoWithHospitals(Doctor doctor);

    @Named("mapHospitalsToIds")
    protected Set<Long> mapHospitalsToIds(Set<Hospital> hospitals) {
        return hospitals.stream()
                .map(Hospital::getId)
                .collect(Collectors.toSet());
    }
}
