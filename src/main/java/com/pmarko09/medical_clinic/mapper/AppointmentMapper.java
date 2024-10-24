package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.dto.AppointmentDTO;
import com.pmarko09.medical_clinic.model.model.Appointment;
import com.pmarko09.medical_clinic.model.model.Doctor;
import com.pmarko09.medical_clinic.model.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "doctor", target = "doctorId", qualifiedByName = "mapDoctorToDoctorId")
    @Mapping(source = "patient", target = "patientId", qualifiedByName = "mapPatientToPatientId")
    AppointmentDTO toDto(Appointment appointment);

    @Named("mapDoctorToDoctorId")
    default Long mapDoctorToDoctorId(Doctor doctor) {
        return doctor != null ? doctor.getId() : null;
    }

    @Named("mapPatientToPatientId")
    default Long mapPatientToPatientId(Patient patient) {
        return patient != null ? patient.getId() : null;
    }
}
