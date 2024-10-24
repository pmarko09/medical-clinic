package com.pmarko09.medical_clinic.controller;

import com.pmarko09.medical_clinic.model.model.ChangePasswordCommand;
import com.pmarko09.medical_clinic.model.model.Patient;
import com.pmarko09.medical_clinic.model.dto.PatientDTO;
import com.pmarko09.medical_clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<PatientDTO> getPatients(Pageable pageable) {
        return patientService.getPatients(pageable);
    }

    @PostMapping
    public PatientDTO addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @GetMapping("/{email}")
    public PatientDTO getPatient(@PathVariable String email) {
        return patientService.getPatientByEmail(email);
    }

    @DeleteMapping("/{email}")
    public PatientDTO deletePatient(@PathVariable String email) {
        return patientService.deletePatientByEmail(email);
    }

    @PutMapping("/{email}")
    public PatientDTO editPatient(@PathVariable String email, @RequestBody Patient updatedPatient) {
        return patientService.editPatientByEmail(email, updatedPatient);
    }

    @PatchMapping("/{email}/password")
    public PatientDTO changePatientPassword(@PathVariable("email") String email, @RequestBody ChangePasswordCommand newPassword) {
        return patientService.changePassword(email, newPassword.getPassword());
    }
}
