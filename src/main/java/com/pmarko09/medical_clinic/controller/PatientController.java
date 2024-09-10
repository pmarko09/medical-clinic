package com.pmarko09.medical_clinic.controller;

import com.pmarko09.medical_clinic.model.ChangePasswordCommand;
import com.pmarko09.medical_clinic.model.Patient;
import com.pmarko09.medical_clinic.model.PatientDTO;
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
    public List<PatientDTO> getPatients() {
        return patientService.getPatients();
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatientByEmail(patient);
    }

    @GetMapping("/{email}")
    public PatientDTO getPatient(@PathVariable String email) {
        return patientService.getPatientDtoByEmail(email);
    }

    @DeleteMapping("/{email}")
    public PatientDTO deletePatient(@PathVariable String email) {
        return patientService.deletePatientDtoByEmail(email);
    }

    @PutMapping("/{email}")
    public PatientDTO editPatient(@PathVariable String email, @RequestBody Patient updatedPatient) {
        return patientService.editPatientByEmail(email, updatedPatient);
    }

    @PatchMapping("/{email}/password")
    public PatientDTO changePatientPassword(@PathVariable("email") String email, @RequestBody ChangePasswordCommand newPassword) {
        return patientService.changePassword(email, newPassword.getPassword());
    }

    @PostMapping("/{patientId}/doctors/{doctorId}")
    public PatientDTO assignDoctorToPatient(@PathVariable Long patientId, @PathVariable Long doctorId) {
        return patientService.addDoctorToPatient(patientId, doctorId);
    }

    @DeleteMapping("/{patientId}/doctors/{doctorId}")
    public PatientDTO removeDoctorFromPatient(@PathVariable Long patientId, @PathVariable Long doctorId) {
        return patientService.removeDoctorFromPatient(patientId, doctorId);
    }
}
