package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.appointment.AppointmentNotFoundException;
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
import com.pmarko09.medical_clinic.validation.AppointmentValidation;
import com.pmarko09.medical_clinic.validation.DoctorValidation;
import com.pmarko09.medical_clinic.validation.PatientValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;

    public List<AppointmentDTO> getAllAppointments(Pageable pageable) {
        return appointmentRepository.findAllApp(pageable).stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    public List<AppointmentDTO> getAllPatientAppointments(Pageable pageable, Long patientId) {
        Patient patient = PatientValidation.patientExists(patientRepository, patientId);
        PatientValidation.patientAppointmentsCheck(patient);

        return appointmentRepository.findAllApp(pageable).stream()
                .filter(appointment -> appointment.getPatient().getId().equals(patientId))
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Transactional
    public AppointmentDTO scheduleAppointment(CreateAppointmentDTO createAppDTO) {
        Doctor doctor = DoctorValidation.doctorExists(doctorRepository, createAppDTO.getDoctorId());

        AppointmentValidation.appTimeValidation(createAppDTO.getStartApp());
        AppointmentValidation.appOverlappingForDoctor(appointmentRepository, createAppDTO);

        Appointment newAppointment = new Appointment();
        newAppointment.setAppointmentStartTime(createAppDTO.getStartApp());
        newAppointment.setAppointmentFinishTime(createAppDTO.getEndApp());
        newAppointment.setDoctor(doctor);
        return appointmentMapper.toDto(appointmentRepository.save(newAppointment));
    }

    @Transactional
    public AppointmentDTO registerPatientForAppointment(Long appId, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(patientId));

        Appointment appointment = appointmentRepository.findById(appId)
                .orElseThrow(() -> new AppointmentNotFoundException(appId));

        AppointmentValidation.patientHasThisAppointmentAlready(appointment, patient);
        AppointmentValidation.appointmentAvailable(appointment);
        AppointmentValidation.appOverLappingForPatient(appointmentRepository, patientId, appointment.getAppointmentStartTime(), appointment.getAppointmentFinishTime());
        appointment.setPatient(patient);
        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }
}

