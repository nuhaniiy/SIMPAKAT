package com.nurul.simpakat.model.simpakat;

import com.nurul.simpakat.model.simpakat.User;

import lombok.Data;

@Data
public class LoginModel {
    private String nip;
    private String email;
    private String password;
    private String name;
    private User user;
    private String dToken;
    private String jabatan;
    private String kodeUnitKerja;

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getdToken() {
        return dToken;
    }

    public void setdToken(String dToken) {
        this.dToken = dToken;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getKodeUnitKerja() {
        return kodeUnitKerja;
    }

    public void setKodeUnitKerja(String kodeUnitKerja) {
        this.kodeUnitKerja = kodeUnitKerja;
    }
}
