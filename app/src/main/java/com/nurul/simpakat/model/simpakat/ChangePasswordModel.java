package com.nurul.simpakat.model.simpakat;

import lombok.Data;

@Data
public class ChangePasswordModel {
    private String NIP;
    private String password;

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
