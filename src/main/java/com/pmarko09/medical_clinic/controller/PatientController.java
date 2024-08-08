package com.pmarko09.medical_clinic.controller;

import com.pmarko09.medical_clinic.model.ChangePasswordCommand;
import com.pmarko09.medical_clinic.model.Patient;
import com.pmarko09.medical_clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @GetMapping("/{email}")
    public Patient getPatient(@PathVariable String email) {
        return patientService.getPatient(email);
    }

    @DeleteMapping("/{email}")
    public Patient deleteDoctor(@PathVariable String email) {
        return patientService.deletePatient(email);
    }

    @PutMapping("/{email}")
    public Patient editPatient(@PathVariable String email, @RequestBody Patient updatedPatient) {
        return patientService.editPatient(email, updatedPatient);
    }

    @PatchMapping("/{email}/change-password")
    public Patient changePatientPassword(@PathVariable("email") String email, @RequestBody ChangePasswordCommand newPassword) {
        return patientService.changePassword(email, newPassword.getPassword());
    }
}
