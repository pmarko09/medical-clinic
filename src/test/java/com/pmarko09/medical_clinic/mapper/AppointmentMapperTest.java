package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.dto.AppointmentDTO;
import com.pmarko09.medical_clinic.model.model.Appointment;
import com.pmarko09.medical_clinic.model.model.Doctor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentMapperTest {

    AppointmentMapper appointmentMapper = Mappers.getMapper(AppointmentMapper.class);

    @Test
    void mapAppointmentToDto() {
        Doctor doctor = new Doctor();
        doctor.setId(2L);
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowal");
        doctor.setEmail("12@");

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setAppointmentStartTime(LocalDateTime.of(2024, 11, 11, 20, 0, 0));
        appointment.setAppointmentFinishTime(LocalDateTime.of(2024, 11, 11, 20, 30, 0));
        appointment.setDoctor(doctor);

        AppointmentDTO result = appointmentMapper.toDto(appointment);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(LocalDateTime.of(2024, 11, 11, 20, 0, 0), result.getAppointmentStartTime());
        assertEquals(LocalDateTime.of(2024, 11, 11, 20, 30, 0), result.getAppointmentFinishTime());
        assertEquals(2L, result.getDoctorId());
    }
}
