package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.hospital.HospitalNotFoundException;
import com.pmarko09.medical_clinic.mapper.HospitalMapper;
import com.pmarko09.medical_clinic.model.Hospital;
import com.pmarko09.medical_clinic.model.HospitalDTO;
import com.pmarko09.medical_clinic.repository.HospitalRepository;
import com.pmarko09.medical_clinic.validation.HospitalValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;

    public List<HospitalDTO> getHospital() {
        return hospitalRepository.findAll().stream()
                .map(hospitalMapper::toDto)
                .toList();
    }

    public HospitalDTO getHospitalById(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new HospitalNotFoundException(id));
        return hospitalMapper.toDto(hospital);
    }

    public HospitalDTO addHospital(Hospital hospital) {
        HospitalValidation.validateHospitalData(hospital);
        return hospitalMapper.toDto(hospitalRepository.save(hospital));
    }

    public HospitalDTO updateHospital(Long id, Hospital editedHospital) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new HospitalNotFoundException(id));
        Hospital.update(hospital, editedHospital);
        return hospitalMapper.toDto(hospitalRepository.save(hospital));
    }

    public void deleteHospital(Long id) {
        hospitalRepository.deleteById(id);
    }
}
