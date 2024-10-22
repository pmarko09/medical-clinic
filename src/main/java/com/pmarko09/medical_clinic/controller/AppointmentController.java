package com.pmarko09.medical_clinic.controller;

import com.pmarko09.medical_clinic.model.dto.AppointmentDTO;
import com.pmarko09.medical_clinic.model.dto.CreateAppointmentDTO;
import com.pmarko09.medical_clinic.model.dto.PatientDTO;
import com.pmarko09.medical_clinic.model.dto.PatientIdDTO;
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

    @GetMapping("/patient")
    public List<AppointmentDTO> getAllPatientAppointments(@RequestParam Long patientId, Pageable pageable) {
        return appointmentService.getAllPatientAppointments(pageable, patientId);
    }

    @PostMapping
    public AppointmentDTO createAppointment(@RequestBody CreateAppointmentDTO createAppDTO) {
        return appointmentService.scheduleAppointment(createAppDTO);
    }

    @PatchMapping("/{appId}")
    public AppointmentDTO registerPatientToAppointment(@PathVariable Long appId, @RequestBody PatientIdDTO patientIdDTO) {
        return appointmentService.registerPatientForAppointment(appId, patientIdDTO.getId());
    }
}
