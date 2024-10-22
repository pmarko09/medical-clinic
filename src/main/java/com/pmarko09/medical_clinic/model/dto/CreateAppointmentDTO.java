package com.pmarko09.medical_clinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentDTO {

    private Long doctorId;
    private LocalDateTime startApp;
    private LocalDateTime endApp;
}
