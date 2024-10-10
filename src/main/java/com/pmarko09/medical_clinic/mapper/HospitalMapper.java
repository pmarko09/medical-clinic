package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.model.Doctor;
import com.pmarko09.medical_clinic.model.model.Hospital;
import com.pmarko09.medical_clinic.model.dto.HospitalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class HospitalMapper {

    @Mapping(source = "doctors", target = "doctorsIds", qualifiedByName = "mapDoctorsToIds")
    public abstract HospitalDTO toDto(Hospital hospital);

    @Named("mapDoctorsToIds")
    protected Set<Long> mapDoctorsToIds(Set<Doctor> doctors) {
        if (doctors == null) {
            return Collections.emptySet();
        }
        return doctors.stream()
                .map(Doctor::getId)
                .collect(Collectors.toSet());
    }
}