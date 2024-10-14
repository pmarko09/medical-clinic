package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.hospital.HospitalNotFoundException;
import com.pmarko09.medical_clinic.mapper.HospitalMapper;
import com.pmarko09.medical_clinic.model.model.Hospital;
import com.pmarko09.medical_clinic.model.dto.HospitalDTO;
import com.pmarko09.medical_clinic.repository.HospitalRepository;
import com.pmarko09.medical_clinic.validation.HospitalValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;

    public List<HospitalDTO> getHospitals() {
        return hospitalRepository.findAll().stream()
                .map(hospitalMapper::toDto)
                .toList();
    }

    public HospitalDTO getHospitalById(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new HospitalNotFoundException(id));
        return hospitalMapper.toDto(hospital);
    }

    @Transactional
    public HospitalDTO addHospital(Hospital hospital) {
        HospitalValidation.hospitalNameExists(hospitalRepository, hospital);
        HospitalValidation.validateHospitalData(hospital);
        return hospitalMapper.toDto(hospitalRepository.save(hospital));
    }

    @Transactional
    public HospitalDTO updateHospital(Long id, Hospital editedHospital) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new HospitalNotFoundException(id));
        Hospital.update(hospital, editedHospital);
        return hospitalMapper.toDto(hospitalRepository.save(hospital));
    }

    @Transactional
    public HospitalDTO deleteHospital(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new HospitalNotFoundException(id));
        hospitalRepository.delete(hospital);
     return hospitalMapper.toDto(hospital);
    }
}
