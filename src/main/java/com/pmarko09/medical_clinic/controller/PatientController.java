package com.pmarko09.medical_clinic.controller;

import ch.qos.logback.classic.pattern.LineSeparatorConverter;
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

    @GetMapping ("/{email}")
    public Patient getPatient(@PathVariable String email) {
        return patientService.getPatient(email);
    }

    @DeleteMapping ("/{email}")
    public Patient removePatient (@PathVariable String email) {
        return patientService.removePatient(email);
    }

    @PutMapping ("/{email}")
    public Patient editPatient (@PathVariable("email") String email, @RequestBody Patient editedPatient) {
        return patientService.editPatient(email, editedPatient);
    }

    @PutMapping("/{email}/change-password")
    public Patient changePatientPassword (@PathVariable ("email") String email, @RequestBody String newPassword) {
        return patientService.changePassword(email, newPassword);
    }
}
