package com.nurul.simpakat.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SignInUserCreateRequest {
    @Expose
    @SerializedName("nip")
    private String nip;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("device_token")
    private String dToken;

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getdToken() {
        return dToken;
    }

    public void setdToken(String dToken) {
        this.dToken = dToken;
    }
}
