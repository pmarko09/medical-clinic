package com.pmarko09.medical_clinic.controller;

import com.pmarko09.medical_clinic.model.ChangePasswordCommand;
import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.model.DoctorDTO;
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
    public List<DoctorDTO> getDoctors() {
        return doctorService.getDoctors();
    }

    @PostMapping
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }

    @GetMapping("/{email}")
    public DoctorDTO getDoctor(@PathVariable String email) {
        return doctorService.getDoctorDto(email);
    }

    @DeleteMapping("/{email}")
    public DoctorDTO deleteDoctor(@PathVariable String email) {
        return doctorService.deleteDoctorDto(email);
    }

    @PutMapping("/{email}")
    public DoctorDTO editDoctor(@PathVariable String email, @RequestBody Doctor updatedDoctor) {
        return doctorService.editDoctor(email, updatedDoctor);
    }

    @PatchMapping("/{email}/password")
    public DoctorDTO changeDoctorPassword(@PathVariable String email, @RequestBody ChangePasswordCommand newPassword) {
        return doctorService.changeDoctorPassword(email, newPassword.getPassword());
    }
}
