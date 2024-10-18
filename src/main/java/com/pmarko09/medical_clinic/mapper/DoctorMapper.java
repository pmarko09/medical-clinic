package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.model.Appointment;
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

    @Mapping(source = "hospitals", target = "hospitalsIds", qualifiedByName = "mapHospitalsToIds")
    @Mapping(source = "appointments", target = "appointmentIds", qualifiedByName = "mapAppointmentsToIds")
    public abstract DoctorDTO toDto(Doctor doctor);

    @Named("mapHospitalsToIds")
    protected Set<Long> mapHospitalsToIds(Set<Hospital> hospitals) {
        return hospitals != null ? hospitals.stream()
                .map(Hospital::getId)
                .collect(Collectors.toSet()) : null;
    }

    @Named("mapAppointmentsToIds")
    protected Set<Long> mapAppointmentsToIds(Set<Appointment> appointments) {
        return appointments != null ? appointments.stream()
                .map(Appointment::getId)
                .collect(Collectors.toSet()) : null;
    }
}
