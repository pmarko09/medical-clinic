package com.pmarko09.medical_clinic.validation;

import com.pmarko09.medical_clinic.exception.appointment.AppointmentInThePastException;
import com.pmarko09.medical_clinic.exception.appointment.AppointmentNotAvailableException;
import com.pmarko09.medical_clinic.exception.appointment.AppointmentFullQuarterException;
import com.pmarko09.medical_clinic.exception.appointment.AppointmentTimeErrorException;
import com.pmarko09.medical_clinic.exception.patient.PatientHasBookedAppointmentException;
import com.pmarko09.medical_clinic.model.dto.CreateAppointmentDTO;
import com.pmarko09.medical_clinic.model.model.Appointment;
import com.pmarko09.medical_clinic.model.model.Patient;
import com.pmarko09.medical_clinic.repository.AppointmentRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentValidation {

    public static void appTimeValidation(LocalDateTime appointmentTime) {
        if (appointmentTime.isBefore(LocalDateTime.now())) {
            throw new AppointmentInThePastException();
        }
        if (appointmentTime.getMinute() % 15 != 0) {
            throw new AppointmentFullQuarterException(appointmentTime);
        }
    }

    public static void appointmentAvailable(Appointment appointment) {
        if (appointment.getPatient() != null) {
            throw new AppointmentNotAvailableException();
        }
    }

    public static void patientHasThisAppointmentAlready(Appointment appointment, Patient patient) {
        if ( appointment.getPatient() != null && appointment.getPatient().getId().equals(patient.getId())) {
            throw new PatientHasBookedAppointmentException(patient.getId(), appointment.getId());
        }
    }

    public static void appOverlappingForDoctor(AppointmentRepository appRepo, CreateAppointmentDTO createAppDTO) {
        List<Appointment> overlappingAppointmentsForDoctor = appRepo.findOverlappingAppointmentsForDoctor(createAppDTO.getDoctorId(),
                createAppDTO.getStartApp(), createAppDTO.getEndApp());
        if (!overlappingAppointmentsForDoctor.isEmpty()) {
            throw new AppointmentTimeErrorException(createAppDTO.getStartApp());
        }
    }

    public static void appOverLappingForPatient(AppointmentRepository appRepo, Long patientId, LocalDateTime appStart, LocalDateTime appFinish) {
        List<Appointment> overlappingAppointmentsForPatient = appRepo.findOverlappingAppointmentsForPatient(patientId, appStart, appFinish);
        if (!overlappingAppointmentsForPatient.isEmpty()) {
            throw new AppointmentTimeErrorException(appStart);
        }
    }
}
