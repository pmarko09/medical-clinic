package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.DoctorNotFoundException;
import com.pmarko09.medical_clinic.exception.DoctorAlreadyExistException;
import com.pmarko09.medical_clinic.mapper.DoctorDoctorDTOMapper;
import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.model.DoctorDTO;
import com.pmarko09.medical_clinic.validation.DoctorValidation;
import com.pmarko09.medical_clinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorDoctorDTOMapper doctorDoctorDtoMapper;

    public List<DoctorDTO> getDoctors() {
        return doctorRepository.getDoctors().stream()
                .map(doctorDoctorDtoMapper::doctorToDoctorDto)
                .toList();
    }

    public Doctor addDoctor(Doctor doctor) {
        DoctorValidation.doctorEmailInUse(doctorRepository, doctor.getEmail());
        DoctorValidation.validateDoctorData(doctor);
        return doctorRepository.addDoctor(doctor);
    }

    public DoctorDTO getDoctorDto(String email) {
        Doctor doctor = doctorRepository.getDoctor(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        return doctorDoctorDtoMapper.doctorToDoctorDto(doctor);
    }

    public DoctorDTO deleteDoctorDto(String email) {
        Doctor doctor = doctorRepository.deleteDoctor(email)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        return doctorDoctorDtoMapper.doctorToDoctorDto(doctor);
    }

    public DoctorDTO editDoctor(String email, Doctor updatedDoctor) {
        if (doctorRepository.doctorExists(updatedDoctor.getEmail()) && !email.equals(updatedDoctor.getEmail())) {
            throw new DoctorAlreadyExistException(updatedDoctor.getEmail());
        }
        DoctorValidation.validateDoctorData(updatedDoctor);

        Doctor editedDoctor = doctorRepository.editDoctor(email, updatedDoctor)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        return doctorDoctorDtoMapper.doctorToDoctorDto(editedDoctor);
    }

    public DoctorDTO changeDoctorPassword(String email, String newPassword) {
        Doctor doctor = doctorRepository.changeDoctorPassword(email, newPassword)
                .orElseThrow(() -> new DoctorNotFoundException(email));
        return doctorDoctorDtoMapper.doctorToDoctorDto(doctor);
    }

}
