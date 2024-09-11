package com.pmarko09.medical_clinic.model.model;

import lombok.Data;

@Data
public class ChangePasswordCommand {

    private String password;

    public String getPassword() {
        return password;
    }
}
