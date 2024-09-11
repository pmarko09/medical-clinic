package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.doctor.DoctorIdNotFound;
import com.pmarko09.medical_clinic.exception.doctor.DoctorNotFoundException;
import com.pmarko09.medical_clinic.exception.hospital.HospitalNotFoundException;
import com.pmarko09.medical_clinic.mapper.DoctorMapper;
import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.model.DoctorDTO;
import com.pmarko09.medical_clinic.model.Hospital;
import com.pmarko09.medical_clinic.repository.DoctorRepository;
import com.pmarko09.medical_clinic.repository.HospitalRepository;
import com.pmarko09.medical_clinic.validation.DoctorValidation;
import com.pmarko09.medical_clinic.validation.PasswordValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final DoctorMapper doctorMapper;

    public List<DoctorDTO> getDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    public Doctor addDoctor(Doctor doctor) {
        DoctorValidation.doctorEmailInUse(doctorRepository, doctor.getEmail());
        DoctorValidation.validateDoctorData(doctor);
        return doctorRepository.save(doctor);
    }

    public DoctorDTO getDoctorDtoByEmail(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        return doctorMapper.toDto(doctor);
    }

    public DoctorDTO deleteDoctorDtoByEmail(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        doctorRepository.delete(doctor);
        return doctorMapper.toDto(doctor);
    }

    public DoctorDTO editDoctorDtoByEmail(String email, Doctor editedDoctor) {
        DoctorValidation.doctorAlreadyExists(doctorRepository, email, editedDoctor);
        DoctorValidation.validateDoctorData(editedDoctor);

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        Doctor.update(doctor, editedDoctor);
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    public DoctorDTO changeDoctorPassword(String email, String newPassword) {
        PasswordValidation.validate(newPassword);

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        doctor.setPassword(newPassword);

        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    public DoctorDTO addDoctorToHospital(Long doctorId, Long hospitalId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorIdNotFound(doctorId));

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new HospitalNotFoundException(hospitalId));

        doctor.getHospitals().add(hospital);
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }
}
