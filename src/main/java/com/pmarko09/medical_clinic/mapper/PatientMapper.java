package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.model.Appointment;
import com.pmarko09.medical_clinic.model.model.Patient;
import com.pmarko09.medical_clinic.model.dto.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(source = "appointments", target = "appointmentIds", qualifiedByName = "mapAppointmentsToAppointmentsIds")
    PatientDTO toDto(Patient patient);

    @Named("mapAppointmentsToAppointmentsIds")
    default Set<Long> mapAppointmentsToAppointmentsIds(Set<Appointment> appointments) {
        return appointments != null ? appointments.stream()
                .map(Appointment::getId)
                .collect(Collectors.toSet()) : null;
    }
}
