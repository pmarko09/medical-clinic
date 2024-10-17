package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.doctor.DoctorNotFoundException;
import com.pmarko09.medical_clinic.mapper.DoctorMapper;
import com.pmarko09.medical_clinic.model.dto.DoctorDTO;
import com.pmarko09.medical_clinic.model.model.Doctor;
import com.pmarko09.medical_clinic.model.model.Hospital;
import com.pmarko09.medical_clinic.repository.DoctorRepository;
import com.pmarko09.medical_clinic.repository.HospitalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

        Pageable pageable = PageRequest.of(0, 2);

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

        when(doctorRepository.findAllDoctors(pageable)).thenReturn(List.of(doctor1, doctor2));

        //when
        List<DoctorDTO> result = doctorService.getDoctors(pageable);

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

        when(doctorRepository.save(doctor1)).thenReturn(doctor1);

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
                .password("333")
                .hospitals(new HashSet<>())
                .build();

        when(doctorRepository.findByEmail(doctor1.getEmail())).thenReturn(Optional.of(doctor1));

        //when
        DoctorDTO result = doctorService.getDoctorByEmail("w@");

        //then
        assertEquals("Robert", result.getFirstName());
        assertEquals("W", result.getLastName());
        assertEquals("w@", result.getEmail());
        assertNotNull(result);
    }

    @Test
    void getDoctorByEmail_DoctorNotFound_ThrowException() {
        //given
        when(doctorRepository.findByEmail("12@")).thenReturn(Optional.empty());

        //when then
        DoctorNotFoundException aThrows = assertThrows(DoctorNotFoundException.class, () ->
                doctorService.getDoctorByEmail("12@"));
        assertEquals(aThrows.getMessage(), "Doctor with given email 12@ not found.");
    }

    @Test
    void deleteDoctorByEmail_EmailCorrect_DoctorDtoReturned() {
        //given
        Doctor doctor1 = Doctor.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("W")
                .email("w@")
                .hospitals(new HashSet<>())
                .build();

        when(doctorRepository.findByEmail("w@")).thenReturn(Optional.of(doctor1));

        //when
        DoctorDTO result = doctorService.deleteDoctorByEmail("w@");

        //then
        assertEquals("w@", result.getEmail());
        assertEquals("W", result.getLastName());
        assertEquals("Robert", result.getFirstName());
        assertEquals(1L, result.getId());
        verify(doctorRepository, times(1)).delete(doctor1);
    }

    @Test
    void deleteDoctorByEmail_DoctorNotFound_ThrowException() {
        //given
        when(doctorRepository.findByEmail("12@")).thenReturn(Optional.empty());

        //when then
        DoctorNotFoundException aThrows = assertThrows(DoctorNotFoundException.class, () ->
                doctorService.deleteDoctorByEmail("12@"));
        assertEquals(aThrows.getMessage(), "Doctor with given email 12@ not found.");
    }

    @Test
    void editDoctorByEmail_DataCorrect_DoctorDtoReturned() {
        //given
        Doctor doctor1 = Doctor.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("W")
                .email("w@")
                .hospitals(new HashSet<>())
                .build();
        Doctor updated = Doctor.builder()
                .id(1L)
                .firstName("Jan")
                .lastName("X")
                .email("x@")
                .hospitals(new HashSet<>())
                .build();

        when(doctorRepository.findByEmail("w@")).thenReturn(Optional.of(doctor1));
        when(doctorRepository.save(doctor1)).thenReturn(updated);

        //when
        DoctorDTO result = doctorService.editDoctorByEmail("w@", doctor1);

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jan", result.getFirstName());
        assertEquals("X", result.getLastName());
        assertEquals("x@", result.getEmail());
    }

    @Test
    void editDoctorByEmail_DoctorNotFound_ThrowException() {
        //given
        String email = "12@";

        Doctor doctor1 = Doctor.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("W")
                .email("w@")
                .hospitals(new HashSet<>())
                .build();

        when(doctorRepository.findByEmail(email)).thenReturn(Optional.empty());

        //when then
        DoctorNotFoundException aThrows = assertThrows(DoctorNotFoundException.class, () ->
                doctorService.editDoctorByEmail("12@", doctor1));
        assertEquals(aThrows.getMessage(), "Doctor with given email 12@ not found.");
    }

    @Test
    void changeDoctorPassword_DataCorrect_DoctorDtoReturned() {
        //given
        Doctor doctor1 = Doctor.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("W")
                .email("w@")
                .hospitals(new HashSet<>())
                .build();

        when(doctorRepository.findByEmail("w@")).thenReturn(Optional.of(doctor1));
        when(doctorRepository.save(doctor1)).thenReturn(doctor1);

        //when
        DoctorDTO result = doctorService.changeDoctorPassword("w@", "000");

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Robert", result.getFirstName());
        assertEquals("W", result.getLastName());
    }

    @Test
    void changeDoctorPassword_DoctorNotFound_ThrowException() {
        //given
        String email = "12@";

        when(doctorRepository.findByEmail(email)).thenReturn(Optional.empty());

        //when then
        DoctorNotFoundException aThrows = assertThrows(DoctorNotFoundException.class, () ->
                doctorService.changeDoctorPassword("12@", "000"));
        assertEquals(aThrows.getMessage(), "Doctor with given email 12@ not found.");
    }

    @Test
    void addDoctorToHospital_DataCorrect_DoctorDtoReturned() {
        //given
        Doctor doctor = Doctor.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("W")
                .email("w@")
                .hospitals(new HashSet<>())
                .build();

        Hospital hospital = new Hospital();
        hospital.setId(2L);
        hospital.setCity("Wro");
        hospital.setStreet("W");
        hospital.setBuildingNumber("111");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(hospitalRepository.findById(2L)).thenReturn(Optional.of(hospital));
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        //when
        DoctorDTO result = doctorService.addDoctorToHospital(1L, 2L);

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Robert", result.getFirstName());
        assertTrue(result.getHospitalsIds().contains(hospital.getId()));
    }
}
