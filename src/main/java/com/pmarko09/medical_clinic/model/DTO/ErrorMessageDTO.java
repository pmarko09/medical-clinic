package com.pmarko09.medical_clinic.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessageDTO {

    private String message;
    private LocalDateTime time;
    private HttpStatus httpStatus;

}
