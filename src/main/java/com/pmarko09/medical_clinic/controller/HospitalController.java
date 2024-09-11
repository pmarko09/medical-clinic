package com.pmarko09.medical_clinic.controller;

import com.pmarko09.medical_clinic.model.model.Hospital;
import com.pmarko09.medical_clinic.model.DTO.HospitalDTO;
import com.pmarko09.medical_clinic.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping
    public List<HospitalDTO> getHospitals() {
        return hospitalService.getHospital();
    }

    @GetMapping("/{id}")
    public HospitalDTO getHospital(@PathVariable Long id) {
        return hospitalService.getHospitalById(id);
    }

    @PostMapping
    public HospitalDTO addHospital(@RequestBody Hospital hospital) {
        return hospitalService.addHospital(hospital);
    }

    @PutMapping("/{id}")
    public HospitalDTO updateHospital(@PathVariable Long id, @RequestBody Hospital hospital) {
        return hospitalService.updateHospital(id, hospital);
    }

    @DeleteMapping("/{id}")
    public void deleteHospital(@PathVariable Long id) {
        hospitalService.deleteHospital(id);
    }
}
