package com.nurul.simpakat.model.simpakat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListDataCoa {
    @Expose
    @SerializedName("no_account")
    private String kodeCoa;

    @Expose
    @SerializedName("kode_parent")
    private String kodeParent;

    @Expose
    @SerializedName("nama_account")
    private String namaCoa;

    public String getKodeCoa() {
        return kodeCoa;
    }

    public void setKodeCoa(String kodeCoa) {
        this.kodeCoa = kodeCoa;
    }

    public String getKodeParent() {
        return kodeParent;
    }

    public void setKodeParent(String kodeParent) {
        this.kodeParent = kodeParent;
    }

    public String getNamaCoa() {
        return namaCoa;
    }

    public void setNamaCoa(String namaCoa) {
        this.namaCoa = namaCoa;
    }
}
