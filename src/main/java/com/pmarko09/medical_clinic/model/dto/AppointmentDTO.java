package com.pmarko09.medical_clinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDTO {

    private Long id;
    private LocalDateTime appointmentStartTime;
    private LocalDateTime appointmentFinishTime;
    private Long doctorId;
    private Long patientId;

}
