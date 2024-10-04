package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.mapper.DoctorMapper;
import com.pmarko09.medical_clinic.model.dto.DoctorDTO;
import com.pmarko09.medical_clinic.model.model.Doctor;
import com.pmarko09.medical_clinic.model.model.Hospital;
import com.pmarko09.medical_clinic.repository.DoctorRepository;
import com.pmarko09.medical_clinic.repository.HospitalRepository;
import com.pmarko09.medical_clinic.validation.DoctorValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {

    DoctorService doctorService;
    DoctorRepository doctorRepository;
    DoctorMapper doctorMapper;
    HospitalRepository hospitalRepository;

    @BeforeEach
    void setup() {
        this.doctorMapper = Mappers.getMapper(DoctorMapper.class);
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.hospitalRepository = Mockito.mock(HospitalRepository.class);
        this.doctorService = new DoctorService(doctorRepository, hospitalRepository, doctorMapper);
    }

    @Test
    void getDoctors_DataCorrect_DoctorsDtoReturned() {
        //given
        Doctor doctor1 = Doctor.builder()
                .id(1L)
                .email("123")
                .hospitals(new HashSet<>())
                .firstName("Jan")
                .lastName("Nowak")
                .specialization("Dentist")
                .password("1234")
                .build();

        Doctor doctor2 = Doctor.builder()
                .id(2L)
                .email("@@")
                .hospitals(new HashSet<>())
                .firstName("Maria")
                .lastName("Wolna")
                .specialization("Cardiolog")
                .password("5555")
                .build();

        when(doctorRepository.findAll()).thenReturn(List.of(doctor1, doctor2));

        //when
        List<DoctorDTO> result = doctorService.getDoctors();

        //then
        assertEquals(2, result.size());
        assertEquals("123", result.get(0).getEmail());
        assertEquals("@@", result.get(1).getEmail());
        assertEquals("Jan", result.get(0).getFirstName());
        assertEquals("Maria", result.get(1).getFirstName());
        assertEquals("Dentist", result.get(0).getSpecialization());
        assertEquals("Cardiolog", result.get(1).getSpecialization());
        assertNotNull(result);
    }

    @Test
    void addDoctor_DataCorrect_DoctorDtoReturned() {
        //given
        Doctor doctor1 = Doctor.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("W")
                .email("w@")
                .specialization("Q")
                .password("333")
                .hospitals(new HashSet<>())
                .build();

        DoctorValidation.doctorEmailInUse(doctorRepository, doctor1.getEmail());
        DoctorValidation.validateDoctorData(doctor1);

        when(doctorRepository.save(any())).thenReturn(doctor1);
        doctorMapper.toDto(doctor1);

        //when
        DoctorDTO result = doctorService.addDoctor(doctor1);

        //then
        assertEquals("Robert", result.getFirstName());
        assertEquals("W", result.getLastName());
        assertEquals("w@", result.getEmail());
        assertEquals("Q", result.getSpecialization());

    }

    @Test
    void getDoctorByEmail_EmailCorrect_DoctorDtoReturned() {

        //given
        Doctor doctor1 = Doctor.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("W")
                .email("w@")
                .specialization("Q")
                .password("333")
                .hospitals(new HashSet<>())
                .build();

        when(doctorRepository.findByEmail("PL@")).thenReturn(Optional.of(doctor1));

        //when
        DoctorDTO result = doctorService.getDoctorByEmail("PL@");

        //then
        assertEquals("Robert", result.getFirstName());
        assertEquals("W", result.getLastName());
        assertEquals("w@", result.getEmail());
        assertEquals("Q", result.getSpecialization());

    }

    @Test
    void deleteDoctorByEmail_EmailCorrect_DoctorDtoReturned() {

        //given
        String email = "123@";

        Hospital hospital = new Hospital(1L, "Szpital", "Wro", "1111",
                "Wuwu", "59", new HashSet<>());

        Set<Hospital> hospitals = Set.of(hospital);

        Doctor doctor = new Doctor(1L, "Jan", "L",
                "E", "J@", "1234", hospitals);

        when(doctorRepository.findByEmail(email)).thenReturn(Optional.of(doctor));
        doNothing().when(doctorRepository).delete(doctor);

        //when
        DoctorDTO result = doctorService.deleteDoctorByEmail(email);

        //then
        assertEquals(doctor.getEmail(), result.getEmail());
        assertEquals(doctor.getSpecialization(), result.getSpecialization());
        assertEquals(doctor.getFirstName(), result.getFirstName());

    }

}
