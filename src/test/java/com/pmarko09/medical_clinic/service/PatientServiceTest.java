package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.mapper.PatientMapper;
import com.pmarko09.medical_clinic.model.dto.PatientDTO;
import com.pmarko09.medical_clinic.model.model.Patient;
import com.pmarko09.medical_clinic.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    PatientRepository patientRepository;
    PatientMapper patientMapper;
    PatientService patientService;

    @BeforeEach
    void setup() {
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.patientMapper = Mappers.getMapper(PatientMapper.class);
        this.patientService = new PatientService(patientRepository,
                patientMapper);
    }

    @Test
    void getPatients_DataCorrect_PatientsDtoReturned() {
        //given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("A");
        patient.setLastName("C");
        patient.setEmail("AC@");
        patient.setIdCardNo("997");

        when(patientRepository.findAll()).thenReturn(List.of(patient));

        //when
        List<PatientDTO> result = patientService.getPatients();

        //then
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("A", result.get(0).getFirstName());
        assertFalse(result.isEmpty());
    }

    @Test
    void addPatientByEmail_DataCorrect_PatientDtoReturned() {
        //given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("A");
        patient.setLastName("C");
        patient.setEmail("AC@");
        patient.setIdCardNo("997");

        when(patientRepository.save(patient)).thenReturn(patient);

        //when
        PatientDTO result = patientService.addPatientByEmail(patient);

        //then
        assertEquals(1L, result.getId());
        assertEquals("A", result.getFirstName());
        assertEquals("C", result.getLastName());
        assertNotNull(result);
    }

    @Test
    void getPatientByEmail_DataCorrect_PatientDtoReturned() {
        //given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("A");
        patient.setLastName("C");
        patient.setEmail("AC@");
        patient.setIdCardNo("997");

        when(patientRepository.findByEmail("ab2")).thenReturn(Optional.of(patient));

        //when
        PatientDTO result = patientService.getPatientByEmail("ab2");

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A", result.getFirstName());
        assertEquals("C", result.getLastName());
    }
}
