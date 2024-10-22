package com.pmarko09.medical_clinic.controller;

import com.pmarko09.medical_clinic.model.dto.AppointmentDTO;
import com.pmarko09.medical_clinic.model.dto.CreateAppointmentDTO;
import com.pmarko09.medical_clinic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentDTO> getAllAppointments(Pageable pageable) {
        return appointmentService.getAllAppointments(pageable);
    }

    @GetMapping("/patient/{patientId}")
    public List<AppointmentDTO> getAllPatientAppointments(@PathVariable Long patientId, Pageable pageable) {
        return appointmentService.getAllPatientAppointments(pageable, patientId);
    }

    @PostMapping()
    public AppointmentDTO createAppointment(@RequestBody CreateAppointmentDTO createAppDTO) {
        return appointmentService.scheduleAppointment(createAppDTO);
    }

    @PatchMapping("/visit/{appId}/patient/{patientId}")
    public AppointmentDTO registerPatientToAppointment(@PathVariable Long appId, @PathVariable Long patientId) {
        return appointmentService.registerPatientForAppointment(appId, patientId);
    }
}
