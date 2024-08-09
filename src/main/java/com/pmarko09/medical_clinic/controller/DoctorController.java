package com.pmarko09.medical_clinic.controller;

import com.pmarko09.medical_clinic.model.ChangePasswordCommand;
import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public List<Doctor> getDoctors() {
        return doctorService.getDoctors();
    }

    @PostMapping
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }

    @GetMapping("/{email}")
    public Doctor getDoctor(@PathVariable String email) {
        return doctorService.getDoctor(email);
    }

    @DeleteMapping("/{email}")
    public Doctor deleteDoctor(@PathVariable String email) {
        return doctorService.deleteDoctor(email);
    }

    @PutMapping("/{email}")
    public Doctor editDoctor(@PathVariable String email, @RequestBody Doctor updatedDoctor) {
        return doctorService.editDoctor(email, updatedDoctor);
    }

    @PatchMapping("/{email}/password")
    public Doctor changeDoctorPassword(@PathVariable String email, @RequestBody ChangePasswordCommand newPassword) {
        return doctorService.changeDoctorPassword(email, newPassword.getPassword());
    }
}