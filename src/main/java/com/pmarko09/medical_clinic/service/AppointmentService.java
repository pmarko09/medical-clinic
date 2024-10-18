package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.appointment.AppointmentTimeErrorException;
import com.pmarko09.medical_clinic.exception.patient.PatientNotFound;
import com.pmarko09.medical_clinic.mapper.AppointmentMapper;
import com.pmarko09.medical_clinic.model.dto.AppointmentDTO;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;

//    public List<AppointmentDTO> getAllAppointments(Pageable pageable) {
//        return appointmentRepository.findAllApp(pageable).stream()
//                .map(appointmentMapper::toDto)
//                .toList();
//    }

    public List<AppointmentDTO> getAllPatientAppointments(Pageable pageable, Long patientId) {

        PatientValidation.patientExists(patientRepository, patientId);

        return appointmentRepository.findAllApp(pageable).stream()
                .filter(appointment -> appointment.getPatient().getId().equals(patientId))
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Transactional
    public AppointmentDTO scheduleAppointment(Long doctorId, LocalDateTime appStartTime, LocalDateTime appEndTime) {

        Doctor doctor = DoctorValidation.doctorExists(doctorRepository, doctorId);

        AppointmentValidation.appTimeValidation(appStartTime);

        Set<Appointment> appointments = doctor.getAppointments();

        for (Appointment appointment : appointments) {
            if (isTimeError(appStartTime, appEndTime, appointment.getAppStartTime(), appointment.getAppFinishTime())) {
                throw new AppointmentTimeErrorException();
            }
        }

        Appointment newAppointment = new Appointment();
        newAppointment.setAppStartTime(appStartTime);
        newAppointment.setAppFinishTime(appEndTime);
        newAppointment.setDoctor(doctor);

        return appointmentMapper.toDto(appointmentRepository.save(newAppointment));
    }

    private boolean isTimeError(LocalDateTime newStartTime, LocalDateTime newEndTime,
                                LocalDateTime existingStartTime, LocalDateTime existingEndTime) {

        return (newStartTime.isBefore(existingEndTime) && newEndTime.isAfter(existingStartTime));
    }

    @Transactional
    public AppointmentDTO registerPatientToAppointment(Long patientId, LocalDateTime requestedAppStartTime, LocalDateTime requestedAppFinishTime) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFound(patientId));

        Appointment availableApp = AppointmentValidation.appointmentAvailable(appointmentRepository, requestedAppStartTime, requestedAppFinishTime);

        availableApp.setPatient(patient);

        return appointmentMapper.toDto(appointmentRepository.save(availableApp));
    }
}

