package com.pmarko09.medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.medical_clinic.model.dto.AppointmentDTO;
import com.pmarko09.medical_clinic.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {

    @MockBean
    AppointmentService appointmentService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAppointments_DataCorrect_ReturnStatus200() throws Exception {
        AppointmentDTO appointmentDTO1 = AppointmentDTO.builder()
                .id(1L)
                .appointmentStartTime(LocalDateTime.of(2024, 11, 11, 20, 0, 0))
                .appointmentFinishTime(LocalDateTime.of(2024, 11, 11, 20, 30, 0))
                .doctorId(1L)
                .patientId(null)
                .build();

        AppointmentDTO appointmentDTO2 = AppointmentDTO.builder()
                .id(2L)
                .appointmentStartTime(LocalDateTime.of(2024, 11, 12, 20, 0, 0))
                .appointmentFinishTime(LocalDateTime.of(2024, 11, 12, 20, 30, 0))
                .doctorId(2L)
                .patientId(1L)
                .build();

        List<AppointmentDTO> appointmentDTOS = List.of(appointmentDTO1, appointmentDTO2);
        Pageable pageable = PageRequest.of(0,2);

        when(appointmentService.getAllAppointments(pageable)).thenReturn(appointmentDTOS);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/appointments?page=0&size=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].appointmentStartTime").value("2024-11-11T20:00:00"))
                .andExpect(jsonPath("$[0].appointmentFinishTime").value("2024-11-11T20:30:00"))
                .andExpect(jsonPath("$[0].doctorId").value(1L))
                .andExpect(jsonPath("$[0].patientId").value(null))
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[1].appointmentStartTime").value("2024-11-12T20:00:00"))
                .andExpect(jsonPath("$[1].appointmentFinishTime").value("2024-11-12T20:30:00"))
                .andExpect(jsonPath("$[1].doctorId").value(2L))
                .andExpect(jsonPath("$[1].patientId").value(1L));



    }
}
