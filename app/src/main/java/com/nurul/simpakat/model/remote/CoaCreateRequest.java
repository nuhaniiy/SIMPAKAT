package com.nurul.simpakat.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CoaCreateRequest {
    @Expose
    @SerializedName("no_account")
    private String noAccount;

    @Expose
    @SerializedName("kode_parent")
    private String kodeParent;

    @Expose
    @SerializedName("nama_account")
    private String namaAccount;

    public String getNoAccount() {
        return noAccount;
    }

    public void setNoAccount(String noAccount) {
        this.noAccount = noAccount;
    }

    public String getKodeParent() {
        return kodeParent;
    }

    public void setKodeParent(String kodeParent) {
        this.kodeParent = kodeParent;
    }

    public String getNamaAccount() {
        return namaAccount;
    }

    public void setNamaAccount(String namaAccount) {
        this.namaAccount = namaAccount;
    }
}
