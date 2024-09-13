package com.pmarko09.medical_clinic.controller;

import com.pmarko09.medical_clinic.model.model.ChangePasswordCommand;
import com.pmarko09.medical_clinic.model.model.Doctor;
import com.pmarko09.medical_clinic.model.dto.DoctorDTO;
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
    public DoctorDTO addDoctor(@RequestBody Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }

    @GetMapping("/{email}")
    public DoctorDTO getDoctor(@PathVariable String email) {
        return doctorService.getDoctorByEmail(email);
    }

    @DeleteMapping("/{email}")
    public DoctorDTO deleteDoctor(@PathVariable String email) {
        return doctorService.deleteDoctorByEmail(email);
    }

    @PutMapping("/{email}")
    public DoctorDTO editDoctor(@PathVariable String email, @RequestBody Doctor updatedDoctor) {
        return doctorService.editDoctorByEmail(email, updatedDoctor);
    }

    @PatchMapping("/{email}/password")
    public DoctorDTO changeDoctorPassword(@PathVariable String email, @RequestBody ChangePasswordCommand newPassword) {
        return doctorService.changeDoctorPassword(email, newPassword.getPassword());
    }

    @PostMapping("/{doctorId}/hospitals/{hospitalId}")
    public DoctorDTO assignDoctorToHospital(@PathVariable Long doctorId, @PathVariable Long hospitalId) {
        return doctorService.addDoctorToHospital(doctorId, hospitalId);
    }
}
