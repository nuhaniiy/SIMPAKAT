package com.nurul.simpakat.model.simpakat;

import lombok.Data;

@Data
public class ForgetPasswordModel {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
