package com.pmarko09.medical_clinic.controller;

import com.pmarko09.medical_clinic.model.dto.AppointmentDTO;
import com.pmarko09.medical_clinic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/patient/{patientId}")
    public List<AppointmentDTO> getAllPatientAppointments(@PathVariable Long patientId, Pageable pageable) {
        return appointmentService.getAllPatientAppointments(pageable, patientId);
    }

    @PostMapping("/doctor/{doctorId}/{appStartTime}/{appFinishTime}")
    public AppointmentDTO createAppointment(@PathVariable Long doctorId, @PathVariable LocalDateTime appStartTime, @PathVariable LocalDateTime appFinishTime) {
        return appointmentService.scheduleAppointment(doctorId, appStartTime, appFinishTime);
    }

    @PostMapping("/register/{patientId}/{appStartTime}/{appFinishTime}")
    public AppointmentDTO registerPatientToAppointment(Long patientId, LocalDateTime appStartTime, LocalDateTime appFinishTime) {
        return appointmentService.registerPatientToAppointment(patientId, appStartTime, appFinishTime);
    }
}
