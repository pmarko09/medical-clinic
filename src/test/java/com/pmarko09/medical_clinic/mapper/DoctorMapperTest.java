package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.dto.DoctorDTO;
import com.pmarko09.medical_clinic.model.model.Doctor;
import com.pmarko09.medical_clinic.model.model.Hospital;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorMapperTest {

    DoctorMapper doctorMapper = Mappers.getMapper(DoctorMapper.class);

    @Test
    void mapDoctorToDoctorDTO() {

        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setName("POP");
        hospital.setCity("WRO");

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowal");
        doctor.setEmail("12@");
        doctor.setHospitals(Set.of(hospital));

        DoctorDTO result = doctorMapper.toDto(doctor);

        assertEquals(1L, result.getId());
        assertEquals("Jan", result.getFirstName());
        assertNotNull(result);
        assertNotNull(doctor.getHospitals());
        assertEquals(Set.of(1L), result.getHospitalsIds());
    }

    @Test
    void mapDoctorToDoctorDTO_HospitalsNUll() {

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowal");
        doctor.setEmail("12@");
        doctor.setHospitals(null);

        DoctorDTO result = doctorMapper.toDto(doctor);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertTrue(result.getHospitalsIds().isEmpty());
    }
}
