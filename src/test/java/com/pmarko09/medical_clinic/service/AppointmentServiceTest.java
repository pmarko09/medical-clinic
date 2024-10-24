package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.appointment.AppointmentNotFoundException;
import com.pmarko09.medical_clinic.exception.doctor.DoctorIdNotFound;
import com.pmarko09.medical_clinic.exception.patient.PatientNotFoundException;
import com.pmarko09.medical_clinic.mapper.AppointmentMapper;
import com.pmarko09.medical_clinic.model.dto.AppointmentDTO;
import com.pmarko09.medical_clinic.model.dto.CreateAppointmentDTO;
import com.pmarko09.medical_clinic.model.model.Appointment;
import com.pmarko09.medical_clinic.model.model.Doctor;
import com.pmarko09.medical_clinic.model.model.Patient;
import com.pmarko09.medical_clinic.repository.AppointmentRepository;
import com.pmarko09.medical_clinic.repository.DoctorRepository;
import com.pmarko09.medical_clinic.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppointmentServiceTest {

    AppointmentRepository appointmentRepository;
    AppointmentMapper appointmentMapper;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    AppointmentService appointmentService;

    @BeforeEach
    void setup() {
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.appointmentMapper = Mappers.getMapper(AppointmentMapper.class);
        this.appointmentRepository = Mockito.mock(AppointmentRepository.class);
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.appointmentService = new AppointmentService(appointmentRepository, doctorRepository,
                patientRepository, appointmentMapper);
    }

    @Test
    void getAppointments_WithPatientId_PatientAppointmentsDtoReturned() {
        Pageable pageable = PageRequest.of(0, 1);

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

        Patient patient = new Patient();
        patient.setId(5L);
        patient.setFirstName("A");
        patient.setLastName("X");
        patient.setAppointments(Set.of(appointment));

        when(appointmentRepository.findByPatientId(5L)).thenReturn(List.of(appointment));

        List<AppointmentDTO> result = appointmentService.getAllAppointments(pageable, 5L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(0).getDoctorId());
        assertEquals(LocalDateTime.of(2024, 11, 11, 20, 0, 0), result.get(0).getAppointmentStartTime());
        assertEquals(LocalDateTime.of(2024, 11, 11, 20, 30, 0), result.get(0).getAppointmentFinishTime());
    }

    @Test
    void getAppointments_NoPatientId_PatientAppointmentsDtoReturned() {
        Pageable pageable = PageRequest.of(0, 1);

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

        when(appointmentRepository.findAllApp(pageable)).thenReturn(List.of(appointment));

        List<AppointmentDTO> result = appointmentService.getAllAppointments(pageable, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(0).getDoctorId());
        assertEquals(LocalDateTime.of(2024, 11, 11, 20, 0, 0), result.get(0).getAppointmentStartTime());
        assertEquals(LocalDateTime.of(2024, 11, 11, 20, 30, 0), result.get(0).getAppointmentFinishTime());
    }

    @Test
    void scheduleAppointment_DataCorrect_AppointmentDtoReturned() {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();
        createAppointmentDTO.setDoctorId(4L);
        createAppointmentDTO.setStartApp((LocalDateTime.of(2024, 11, 11, 20, 0, 0)));
        createAppointmentDTO.setEndApp(LocalDateTime.of(2024, 11, 11, 20, 30, 0));

        Doctor doctor = new Doctor();
        doctor.setId(4L);
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowal");
        doctor.setEmail("12@");

        Appointment appointment = new Appointment();
        appointment.setId(10L);
        appointment.setAppointmentStartTime(LocalDateTime.of(2024, 11, 11, 20, 0, 0));
        appointment.setAppointmentFinishTime(LocalDateTime.of(2024, 11, 11, 20, 30, 0));
        appointment.setDoctor(doctor);

        when(doctorRepository.findById(4L)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        AppointmentDTO result = appointmentService.scheduleAppointment(createAppointmentDTO);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(4L, result.getDoctorId());
        assertEquals(LocalDateTime.of(2024, 11, 11, 20, 0, 0), result.getAppointmentStartTime());
        assertEquals(LocalDateTime.of(2024, 11, 11, 20, 30, 0), result.getAppointmentFinishTime());
    }

    @Test
    void scheduleAppointment_DoctorNotFound_DoctorNotFoundExceptionThrown() {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();
        createAppointmentDTO.setDoctorId(1L);
        createAppointmentDTO.setStartApp((LocalDateTime.of(2024, 11, 11, 20, 0, 0)));
        createAppointmentDTO.setEndApp(LocalDateTime.of(2024, 11, 11, 20, 30, 0));

        when(doctorRepository.findById(4L)).thenReturn(Optional.empty());

        DoctorIdNotFound ex = assertThrows(DoctorIdNotFound.class, () ->
                appointmentService.scheduleAppointment(createAppointmentDTO));
        assertEquals(ex.getMessage(), "Doctor with id: 1 not found.");
    }

    @Test
    void registerPatientForAppointment_DataCorrect_AppointmentDtoReturned() {
        Doctor doctor = new Doctor();
        doctor.setId(4L);
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowal");
        doctor.setEmail("12@");

        Appointment appointment = new Appointment();
        appointment.setId(10L);
        appointment.setAppointmentStartTime(LocalDateTime.of(2024, 11, 11, 20, 0, 0));
        appointment.setAppointmentFinishTime(LocalDateTime.of(2024, 11, 11, 20, 30, 0));
        appointment.setDoctor(doctor);

        Patient patient = new Patient();
        patient.setId(5L);
        patient.setFirstName("A");
        patient.setLastName("X");
        patient.setAppointments(Set.of(appointment));

        when(patientRepository.findById(5L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findById(10L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        AppointmentDTO result = appointmentService.registerPatientForAppointment(10L, 5L);

        assertNotNull(result);
        assertEquals(5L, result.getPatientId());
        assertEquals(10L, result.getId());
    }

    @Test
    void registerPatientForAppointment_PatientNotFound_PatientNotFoundExceptionThrown() {
        when(patientRepository.findById(3L)).thenReturn(Optional.empty());

        PatientNotFoundException aThrows = assertThrows(PatientNotFoundException.class, () ->
                appointmentService.registerPatientForAppointment(5L, 3L));

        assertEquals(aThrows.getMessage(), "Patient with id: 3 not found");
    }

    @Test
    void registerPatientForAppointment_AppointmentNotFound_AppointmentNotFoundExceptionThrown() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("A");
        patient.setLastName("X");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findById(3L)).thenReturn(Optional.empty());

        AppointmentNotFoundException aThrows = assertThrows(AppointmentNotFoundException.class, () ->
                appointmentService.registerPatientForAppointment(3L, 1L));

        assertEquals(aThrows.getMessage(), "Appointment with id: 3 not found");
    }
}
