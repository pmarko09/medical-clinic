package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.patient.PatientEmailNotFoundException;
import com.pmarko09.medical_clinic.mapper.PatientMapper;
import com.pmarko09.medical_clinic.model.dto.PatientDTO;
import com.pmarko09.medical_clinic.model.model.Patient;
import com.pmarko09.medical_clinic.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        Pageable pageable = PageRequest.of(0, 1);

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("A");
        patient.setLastName("C");
        patient.setEmail("AC@");
        patient.setIdCardNo("997");

        when(patientRepository.findAllPatients(pageable)).thenReturn(List.of(patient));

        //when
        List<PatientDTO> result = patientService.getPatients(pageable);

        //then
        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().getId());
        assertEquals("A", result.getFirst().getFirstName());
        assertFalse(result.isEmpty());
    }

    @Test
    void addPatient_DataCorrect_PatientDtoReturned() {
        //given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("A");
        patient.setLastName("C");
        patient.setEmail("AC@");
        patient.setIdCardNo("997");

        when(patientRepository.save(patient)).thenReturn(patient);

        //when
        PatientDTO result = patientService.addPatient(patient);

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
        patient.setEmail("ab2");
        patient.setIdCardNo("997");

        when(patientRepository.findByEmail("ab2")).thenReturn(Optional.of(patient));

        //when
        PatientDTO result = patientService.getPatientByEmail("ab2");

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A", result.getFirstName());
        assertEquals("C", result.getLastName());
        assertEquals("ab2", result.getEmail());
    }

    @Test
    void getPatientByEmail_PatientNotFound_ThrowException() {
        //given
        when(patientRepository.findByEmail("A@")).thenReturn(Optional.empty());

        //when then
        PatientEmailNotFoundException aThrows = assertThrows(PatientEmailNotFoundException.class, () ->
                patientService.getPatientByEmail("A@"));
        assertEquals(aThrows.getMessage(), "Patient with email A@ not found.");
    }

    @Test
    void deletePatientByEmail_DataCorrect_PatientDtoReturned() {
        //given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("A");
        patient.setLastName("C");
        patient.setEmail("ab2");
        patient.setPhoneNumber("997");

        when(patientRepository.findByEmail("ab2")).thenReturn(Optional.of(patient));

        //when
        PatientDTO result = patientService.deletePatientByEmail("ab2");

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A", result.getFirstName());
        assertEquals("C", result.getLastName());
        assertEquals("997", result.getPhoneNumber());
    }

    @Test
    void deletePatientByEmail_PatientNotFound_ThrowException() {
        //given
        when(patientRepository.findByEmail("A@")).thenReturn(Optional.empty());

        //when then
        PatientEmailNotFoundException aThrows = assertThrows(PatientEmailNotFoundException.class, () ->
                patientService.deletePatientByEmail("A@"));
        assertEquals(aThrows.getMessage(), "Patient with email A@ not found.");
    }

    @Test
    void editPatientByEmail_DataCorrect_PatientDtoReturned() {
        //given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("A");
        patient.setLastName("C");
        patient.setEmail("ab2");
        patient.setPhoneNumber("997");

        Patient updated = new Patient();
        updated.setId(1L);
        updated.setFirstName("Z");
        updated.setLastName("CZ");
        updated.setEmail("a@");
        updated.setPhoneNumber("9977");

        when(patientRepository.findByEmail("ab2")).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(updated);

        //when
        PatientDTO result = patientService.editPatientByEmail("ab2", patient);

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Z", result.getFirstName());
        assertEquals("CZ", result.getLastName());
        assertEquals("a@", result.getEmail());
        assertEquals("9977", result.getPhoneNumber());
    }

    @Test
    void editPatientByEmail_PatientNotFound_ThrowException() {
        //given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("A");
        patient.setLastName("C");
        patient.setEmail("A@");
        patient.setPhoneNumber("997");

        when(patientRepository.findByEmail("A@")).thenReturn(Optional.empty());

        //when then
        PatientEmailNotFoundException aThrows = assertThrows(PatientEmailNotFoundException.class, () ->
                patientService.editPatientByEmail("A@", patient));
        assertEquals(aThrows.getMessage(), "Patient with email A@ not found.");
    }

    @Test
    void changePassword_DataCorrect_PatientDtoReturned() {
        //given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("A");
        patient.setLastName("C");
        patient.setEmail("ab2");
        patient.setPhoneNumber("997");

        when(patientRepository.findByEmail("ab2")).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);

        //when
        PatientDTO result = patientService.changePassword("ab2", "5555");

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A", result.getFirstName());
        assertEquals("C", result.getLastName());
        assertEquals("ab2", result.getEmail());
        assertEquals("997", result.getPhoneNumber());
    }

    @Test
    void changePassword_PatientNotFound_ThrowException() {
        //given

        when(patientRepository.findByEmail("A@")).thenReturn(Optional.empty());

        //when then
        PatientEmailNotFoundException aThrows = assertThrows(PatientEmailNotFoundException.class, () ->
                patientService.changePassword("A@", "1234"));
        assertEquals(aThrows.getMessage(), "Patient with email A@ not found.");
    }
}
