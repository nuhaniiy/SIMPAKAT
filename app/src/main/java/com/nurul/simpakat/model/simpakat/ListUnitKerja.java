package com.nurul.simpakat.model.simpakat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListUnitKerja {
    @Expose
    @SerializedName("kode_unit_kerja")
    private String kodeUnitKerja;

    @Expose
    @SerializedName("nama_unit_kerja")
    private String nama;

    @Expose
    @SerializedName("status")
    private String status;

    public String getKodeUnitKerja() {
        return kodeUnitKerja;
    }

    public void setKodeUnitKerja(String kodeUnitKerja) {
        this.kodeUnitKerja = kodeUnitKerja;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
