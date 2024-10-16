package com.pmarko09.medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.medical_clinic.model.dto.HospitalDTO;
import com.pmarko09.medical_clinic.model.model.Hospital;
import com.pmarko09.medical_clinic.service.HospitalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class HospitalControllerTest {

    @MockBean
    HospitalService hospitalService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getHospitals_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0, 2);

        HospitalDTO hospitalDTO1 = new HospitalDTO();
        hospitalDTO1.setId(1L);
        hospitalDTO1.setName("A");
        hospitalDTO1.setCity("W");
        hospitalDTO1.setBuildingNumber("99");

        HospitalDTO hospitalDTO2 = new HospitalDTO();
        hospitalDTO2.setId(2L);
        hospitalDTO2.setName("X");
        hospitalDTO2.setCity("WQ");
        hospitalDTO2.setBuildingNumber("9");

        List<HospitalDTO> hospitalDTOS = List.of(hospitalDTO1, hospitalDTO2);

        when(hospitalService.getHospitals(pageable)).thenReturn(hospitalDTOS);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/hospitals?page=0&size=2")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[0].city").value("W"))
                .andExpect(jsonPath("$[0].buildingNumber").value("99"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("X"))
                .andExpect(jsonPath("$[1].city").value("WQ"))
                .andExpect(jsonPath("$[1].buildingNumber").value("9"));
    }

    @Test
    void getHospital_DataCorrect_ReturnStatus200() throws Exception {
        //given
        HospitalDTO hospitalDTO = new HospitalDTO();
        hospitalDTO.setId(1L);
        hospitalDTO.setName("A");
        hospitalDTO.setCity("W");
        hospitalDTO.setBuildingNumber("99");

        when(hospitalService.getHospitalById(1L)).thenReturn(hospitalDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/hospitals/1")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("A"))
                .andExpect(jsonPath("$.city").value("W"))
                .andExpect(jsonPath("$.buildingNumber").value("99"));
    }

    @Test
    void addHospital_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Hospital hospital = new Hospital();
        hospital.setName("A");
        hospital.setCity("C");
        hospital.setBuildingNumber("99");

        HospitalDTO hospitalDTO = new HospitalDTO();
        hospitalDTO.setId(1L);
        hospitalDTO.setName("A");
        hospitalDTO.setCity("C");
        hospitalDTO.setBuildingNumber("99");

        when(hospitalService.addHospital(any(Hospital.class))).thenReturn(hospitalDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/hospitals")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(hospital))
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("A"))
                .andExpect(jsonPath("$.city").value("C"))
                .andExpect(jsonPath("$.buildingNumber").value("99"));
    }

    @Test
    void updateHospital_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Hospital hospital = new Hospital();
        hospital.setName("A");
        hospital.setCity("C");
        hospital.setBuildingNumber("99");

        HospitalDTO hospitalDTO = new HospitalDTO();
        hospitalDTO.setId(1L);
        hospitalDTO.setName("S");
        hospitalDTO.setCity("P");
        hospitalDTO.setBuildingNumber("999");

        when(hospitalService.updateHospital(eq(1L), any(Hospital.class))).thenReturn(hospitalDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/hospitals/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(hospital))
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("S"))
                .andExpect(jsonPath("$.city").value("P"))
                .andExpect(jsonPath("$.buildingNumber").value("999"));
    }

    @Test
    void deleteHospital_DataCorrect_ReturnStatus200() throws Exception {
        //given
        HospitalDTO hospitalDTO = new HospitalDTO();
        hospitalDTO.setId(1L);
        hospitalDTO.setName("S");
        hospitalDTO.setCity("P");
        hospitalDTO.setBuildingNumber("999");

        when(hospitalService.deleteHospital(1L)).thenReturn(hospitalDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/hospitals/1")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("S"))
                .andExpect(jsonPath("$.city").value("P"))
                .andExpect(jsonPath("$.buildingNumber").value("999"));
    }
}
