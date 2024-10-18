package com.pmarko09.medical_clinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Long id;
    private LocalDateTime appStartTime;
    private LocalDateTime appFinishTime;
    private Long doctorId;
    private Long patientId;

}
