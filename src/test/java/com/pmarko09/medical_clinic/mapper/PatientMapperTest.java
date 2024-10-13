package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.dto.PatientDTO;
import com.pmarko09.medical_clinic.model.model.Patient;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class PatientMapperTest {

    PatientMapper patientMapper = Mappers.getMapper(PatientMapper.class);

    @Test
    void mapPatientToDTO() {

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Jan");
        patient.setLastName("X");
        patient.setPhoneNumber("1234");
        patient.setIdCardNo("0000");

        PatientDTO result = patientMapper.toDto(patient);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jan", result.getFirstName());
        assertEquals("X", result.getLastName());
        assertEquals("1234", result.getPhoneNumber());
    }
}
