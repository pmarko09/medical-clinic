package com.pmarko09.medical_clinic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.medical_clinic.model.dto.PatientDTO;
import com.pmarko09.medical_clinic.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @MockBean
    PatientService patientService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getPatients_DataCorrect_ReturnStatus200() throws Exception {
        //given
        List<PatientDTO> patientDTOS = List.of(
                PatientDTO.builder()
                        .id(1L)
                        .firstName("A")
                        .lastName("B")
                        .email("ab@")
                        .build(),
                PatientDTO.builder()
                        .id(2L)
                        .firstName("C")
                        .lastName("D")
                        .email("cd@")
                        .build()
        );

        when(patientService.getPatients()).thenReturn(patientDTOS);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/patients")
                ).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("A"))
                .andExpect(jsonPath("$[0].lastName").value("B"))
                .andExpect(jsonPath("$[0].email").value("ab@"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].firstName").value("C"))
                .andExpect(jsonPath("$[1].lastName").value("D"))
                .andExpect(jsonPath("$[1].email").value("cd@"));
    }

//    @Test
//    void addPatient_DataCorrect_ReturnStatus200() {
//        Patient patient = new Patient();
//        patient.setId(1L);
//        patient
    }

