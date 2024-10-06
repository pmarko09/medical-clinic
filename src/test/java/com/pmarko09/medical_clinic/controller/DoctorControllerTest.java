package com.pmarko09.medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.medical_clinic.model.dto.DoctorDTO;
import com.pmarko09.medical_clinic.model.model.ChangePasswordCommand;
import com.pmarko09.medical_clinic.model.model.Doctor;
import com.pmarko09.medical_clinic.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {

    @MockBean
    DoctorService doctorService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getDoctors_DataCorrect_ReturnStatus200() throws Exception {
        //given
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(1L);
        doctorDTO.setFirstName("Jan");
        doctorDTO.setLastName("X");
        doctorDTO.setEmail("998@");

        DoctorDTO doctorDTO2 = new DoctorDTO();
        doctorDTO2.setId(2L);
        doctorDTO2.setFirstName("Ola");
        doctorDTO2.setLastName("C");
        doctorDTO2.setEmail("999@");

        List<DoctorDTO> doctors = List.of(doctorDTO, doctorDTO2);

        when(doctorService.getDoctors()).thenReturn(doctors);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/doctors")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("Jan"))
                .andExpect(jsonPath("$[0].lastName").value("X"))
                .andExpect(jsonPath("$[0].email").value("998@"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].firstName").value("Ola"))
                .andExpect(jsonPath("$[1].lastName").value("C"))
                .andExpect(jsonPath("$[1].email").value("999@"));
    }

    @Test
    void addDoctor_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Doctor doctor = Doctor.builder()
                .firstName("A")
                .lastName("B")
                .email("ab@")
                .specialization("Z")
                .build();

        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .firstName("A")
                .lastName("B")
                .email("ab@")
                .specialization("Z")
                .build();

        when(doctorService.addDoctor(any(Doctor.class))).thenReturn(doctorDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/doctors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(doctor))
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("A"))
                .andExpect(jsonPath("$.lastName").value("B"))
                .andExpect(jsonPath("$.email").value("ab@"))
                .andExpect(jsonPath("$.specialization").value("Z"));
    }

    @Test
    void getDoctor_DataCorrect_ReturnStatus200() throws Exception {
        //given
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .firstName("A")
                .lastName("B")
                .email("ab@")
                .specialization("Z")
                .build();

        when(doctorService.getDoctorByEmail("ab@")).thenReturn(doctorDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/doctors/ab@")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("A"))
                .andExpect(jsonPath("$.lastName").value("B"))
                .andExpect(jsonPath("$.email").value("ab@"))
                .andExpect(jsonPath("$.specialization").value("Z"));
    }

    @Test
    void deleteDoctor_DataCorrect_ReturnStatus200() throws Exception {
        //given
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .firstName("A")
                .lastName("B")
                .email("ab@")
                .specialization("Z")
                .build();

        when(doctorService.deleteDoctorByEmail("ab@")).thenReturn(doctorDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/doctors/ab@")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("A"))
                .andExpect(jsonPath("$.lastName").value("B"))
                .andExpect(jsonPath("$.email").value("ab@"))
                .andExpect(jsonPath("$.specialization").value("Z"));
    }

    @Test
    void editDoctor_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Doctor doctor = Doctor.builder()
                .firstName("C")
                .lastName("D")
                .email("cd@")
                .specialization("L")
                .build();

        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .firstName("A")
                .lastName("B")
                .email("ab@")
                .specialization("Z")
                .build();

        when(doctorService.editDoctorByEmail(eq("cd@"), any(Doctor.class))).thenReturn(doctorDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/doctors/cd@")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(doctor))
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("A"))
                .andExpect(jsonPath("$.lastName").value("B"))
                .andExpect(jsonPath("$.email").value("ab@"))
                .andExpect(jsonPath("$.specialization").value("Z"));
    }

    @Test
    void changeDoctorPassword_DataCorrect_ReturnStatus200() throws Exception {
        //given
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .firstName("A")
                .lastName("B")
                .email("ab@")
                .specialization("Z")
                .build();

        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand();
        changePasswordCommand.setPassword("1234");

        when(doctorService.changeDoctorPassword("ab@", "1234")).thenReturn(doctorDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/doctors/ab@/password")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(changePasswordCommand))
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("A"))
                .andExpect(jsonPath("$.lastName").value("B"))
                .andExpect(jsonPath("$.email").value("ab@"))
                .andExpect(jsonPath("$.specialization").value("Z"));
    }

    @Test
    void assignDoctorToHospital_DataCorrect_ReturnStatus200() throws Exception {
        //given
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .firstName("A")
                .lastName("B")
                .email("ab@")
                .specialization("Z")
                .hospitalsIds(Set.of(2L))
                .build();

        when(doctorService.addDoctorToHospital(1L, 2L)).thenReturn(doctorDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/doctors/1/hospitals/2")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("A"))
                .andExpect(jsonPath("$.lastName").value("B"))
                .andExpect(jsonPath("$.email").value("ab@"))
                .andExpect(jsonPath("$.specialization").value("Z"))
                .andExpect(jsonPath("$.hospitalsIds[0]").value(2L));
    }
}
