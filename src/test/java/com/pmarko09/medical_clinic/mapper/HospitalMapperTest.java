package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.dto.HospitalDTO;
import com.pmarko09.medical_clinic.model.model.Doctor;
import com.pmarko09.medical_clinic.model.model.Hospital;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalMapperTest {

    HospitalMapper hospitalMapper = Mappers.getMapper(HospitalMapper.class);

    @Test
    void mapHospitalToHospitalDTO () {

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowal");
        doctor.setEmail("12@");

        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setName("a");
        hospital.setCity("wro");
        hospital.setBuildingNumber("11");
        hospital.setDoctors(Set.of(doctor));

        HospitalDTO result = hospitalMapper.toDto(hospital);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("a", result.getName());
        assertEquals("wro", result.getCity());
        assertEquals("11", result.getBuildingNumber());
        assertTrue(result.getDoctorsIds().contains(doctor.getId()));
    }

    @Test
    void mapHospitalToHospitalDTO_DoctorsNull() {

        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setName("a");
        hospital.setCity("wro");
        hospital.setBuildingNumber("11");
        hospital.setDoctors(null);

        HospitalDTO result = hospitalMapper.toDto(hospital);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertTrue(result.getDoctorsIds().isEmpty());
    }
}
