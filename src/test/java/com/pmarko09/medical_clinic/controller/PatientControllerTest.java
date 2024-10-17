package com.pmarko09.medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.medical_clinic.model.dto.PatientDTO;
import com.pmarko09.medical_clinic.model.model.ChangePasswordCommand;
import com.pmarko09.medical_clinic.model.model.Patient;
import com.pmarko09.medical_clinic.service.PatientService;
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
        Pageable pageable = PageRequest.of(0, 2);

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

        when(patientService.getPatients(pageable)).thenReturn(patientDTOS);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/patients?page=0&size=2")
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

    @Test
    void addPatient_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Patient patient = new Patient();
        patient.setFirstName("jan");
        patient.setLastName("Wu");
        patient.setEmail("12@");
        patient.setPhoneNumber("997");

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setFirstName("jan");
        patientDTO.setLastName("Wu");
        patientDTO.setEmail("12@");
        patientDTO.setPhoneNumber("997");

        when(patientService.addPatient(any(Patient.class))).thenReturn(patientDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/patients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(patient))
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("jan"))
                .andExpect(jsonPath("$.lastName").value("Wu"))
                .andExpect(jsonPath("$.email").value("12@"))
                .andExpect(jsonPath("$.phoneNumber").value("997"));
    }

    @Test
    void getPatient_DataCorrect_ReturnStatus200() throws Exception {
        //given
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setFirstName("jan");
        patientDTO.setLastName("Wu");
        patientDTO.setEmail("12@");
        patientDTO.setPhoneNumber("997");

        when(patientService.getPatientByEmail("12@")).thenReturn(patientDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/patients/12@")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("jan"))
                .andExpect(jsonPath("$.lastName").value("Wu"))
                .andExpect(jsonPath("$.email").value("12@"))
                .andExpect(jsonPath("$.phoneNumber").value("997"));
    }

    @Test
    void deletePatient_DataCorrect_ReturnStatus200() throws Exception {
        //given
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setFirstName("jan");
        patientDTO.setLastName("Wu");
        patientDTO.setEmail("12@");
        patientDTO.setPhoneNumber("997");

        when(patientService.deletePatientByEmail("12@")).thenReturn(patientDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/patients/12@")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("jan"))
                .andExpect(jsonPath("$.lastName").value("Wu"))
                .andExpect(jsonPath("$.email").value("12@"))
                .andExpect(jsonPath("$.phoneNumber").value("997"));
    }

    @Test
    void editPatient_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Patient patient = new Patient();
        patient.setFirstName("jan");
        patient.setLastName("Wu");
        patient.setEmail("12@");
        patient.setPhoneNumber("997");

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setFirstName("jan");
        patientDTO.setLastName("Wu");
        patientDTO.setEmail("12@");
        patientDTO.setPhoneNumber("997");

        when(patientService.editPatientByEmail(eq("12@"), any(Patient.class))).thenReturn(patientDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/patients/12@")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(patient))
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("jan"))
                .andExpect(jsonPath("$.lastName").value("Wu"))
                .andExpect(jsonPath("$.email").value("12@"))
                .andExpect(jsonPath("$.phoneNumber").value("997"));
    }

    @Test
    void changePatientPassword_DataCorrect_ReturnStatus200() throws Exception {
        //given
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setFirstName("jan");
        patientDTO.setLastName("Wu");
        patientDTO.setEmail("12@");
        patientDTO.setPhoneNumber("997");

        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand();
        changePasswordCommand.setPassword("123");

        when(patientService.changePassword("12@", "123")).thenReturn(patientDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/patients/12@/password")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(changePasswordCommand))
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("jan"))
                .andExpect(jsonPath("$.lastName").value("Wu"))
                .andExpect(jsonPath("$.email").value("12@"))
                .andExpect(jsonPath("$.phoneNumber").value("997"));
    }
}


