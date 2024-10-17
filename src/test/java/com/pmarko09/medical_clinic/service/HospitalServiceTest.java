package com.pmarko09.medical_clinic.service;

import com.pmarko09.medical_clinic.exception.hospital.HospitalNotFoundException;
import com.pmarko09.medical_clinic.mapper.HospitalMapper;
import com.pmarko09.medical_clinic.model.dto.HospitalDTO;
import com.pmarko09.medical_clinic.model.model.Hospital;
import com.pmarko09.medical_clinic.repository.HospitalRepository;
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

public class HospitalServiceTest {

    HospitalRepository hospitalRepository;
    HospitalMapper hospitalMapper;
    HospitalService hospitalService;

    @BeforeEach
    void setup() {
        this.hospitalRepository = Mockito.mock(HospitalRepository.class);
        this.hospitalMapper = Mappers.getMapper(HospitalMapper.class);
        this.hospitalService = new HospitalService(hospitalRepository,
                hospitalMapper);
    }

    @Test
    void getHospitals_DataCorrect_HospitalsDtoReturned() {
        //given
        Pageable pageable = PageRequest.of(0, 1);

        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        hospital1.setCity("Wro");
        hospital1.setBuildingNumber("12");
        hospital1.setStreet("AA");

        Hospital hospital2 = new Hospital();
        hospital2.setId(2L);
        hospital2.setCity("CC");
        hospital2.setBuildingNumber("5");
        hospital2.setStreet("B");

        when(hospitalRepository.findAllHospitals(pageable)).thenReturn(List.of(hospital1, hospital2));
        //when
        List<HospitalDTO> result = hospitalService.getHospitals(pageable);

        //then
        assertNotNull(result);
        assertEquals(1L, result.get(0).getId());
        assertEquals("Wro", result.get(0).getCity());
        assertEquals("12", result.get(0).getBuildingNumber());
        assertEquals("AA", result.get(0).getStreet());
        assertEquals(2L, result.get(1).getId());
        assertEquals("CC", result.get(1).getCity());
        assertEquals("5", result.get(1).getBuildingNumber());
        assertEquals("B", result.get(1).getStreet());
    }

    @Test
    void getHospitalById_DataCorrect_HospitalDtoReturned() {
        //given
        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        hospital1.setCity("Wro");
        hospital1.setBuildingNumber("12");
        hospital1.setStreet("AA");

        when(hospitalRepository.findById(1L)).thenReturn(Optional.of(hospital1));

        //when
        HospitalDTO result = hospitalService.getHospitalById(1L);

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Wro", result.getCity());
        assertEquals("12", result.getBuildingNumber());
        assertEquals("AA", result.getStreet());
    }

    @Test
    void getHospitalById_HospitalNotFound_ThrowException() {
        //given
        when(hospitalRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        HospitalNotFoundException aThrows = assertThrows(HospitalNotFoundException.class, () ->
                hospitalService.getHospitalById(1L));
        assertEquals(aThrows.getMessage(), "Hospital with id: 1 not found.");
    }

    @Test
    void addHospital_DataCorrect_HospitalDtoReturned() {
        //given
        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        hospital1.setCity("Wro");
        hospital1.setBuildingNumber("12");
        hospital1.setStreet("AA");
        hospital1.setName("PP");

        when(hospitalRepository.save(hospital1)).thenReturn(hospital1);

        //when
        HospitalDTO result = hospitalService.addHospital(hospital1);

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Wro", result.getCity());
        assertEquals("12", result.getBuildingNumber());
        assertEquals("AA", result.getStreet());
        assertEquals("PP", result.getName());
    }

    @Test
    void updateHospital_DataCorrect_HospitalDtoReturned() {
        //given
        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        hospital1.setCity("Wro");
        hospital1.setBuildingNumber("12");
        hospital1.setStreet("AA");
        hospital1.setName("PP");

        Hospital updated = new Hospital();
        updated.setId(1L);
        updated.setCity("CC");
        updated.setBuildingNumber("5");
        updated.setStreet("B");
        updated.setName("WR");

        when(hospitalRepository.findById(1L)).thenReturn(Optional.of(hospital1));
        when(hospitalRepository.save(hospital1)).thenReturn(updated);

        //when
        HospitalDTO result = hospitalService.updateHospital(1L, hospital1);

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("CC", result.getCity());
        assertEquals("5", result.getBuildingNumber());
        assertEquals("B", result.getStreet());
        assertEquals("WR", result.getName());
    }

    @Test
    void updateHospital_HospitalNotFound_ThrowException() {
        //given
        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        hospital1.setCity("Wro");
        hospital1.setBuildingNumber("12");
        hospital1.setStreet("AA");
        hospital1.setName("PP");

        when(hospitalRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        HospitalNotFoundException aThrows = assertThrows(HospitalNotFoundException.class, () ->
                hospitalService.updateHospital(1L, hospital1));
        assertEquals(aThrows.getMessage(), "Hospital with id: 1 not found.");
    }

    @Test
    void deleteHospital_DataCorrect_HospitalDtoReturned() {
        //given
        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        hospital1.setCity("Wro");
        hospital1.setBuildingNumber("12");
        hospital1.setStreet("AA");
        hospital1.setName("PP");

        when(hospitalRepository.findById(1L)).thenReturn(Optional.of(hospital1));

        //when
        HospitalDTO result = hospitalService.deleteHospital(1L);

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Wro", result.getCity());
        assertEquals("12", result.getBuildingNumber());
        assertEquals("AA", result.getStreet());
        assertEquals("PP", result.getName());
    }

    @Test
    void deleteHospital_HospitalNotFound_ThrowException() {
        //given
        when(hospitalRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        HospitalNotFoundException aThrows = assertThrows(HospitalNotFoundException.class, () ->
                hospitalService.deleteHospital(1L));
        assertEquals(aThrows.getMessage(), "Hospital with id: 1 not found.");
    }
}