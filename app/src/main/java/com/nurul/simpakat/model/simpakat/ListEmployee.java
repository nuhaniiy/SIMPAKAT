package com.nurul.simpakat.model.simpakat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListEmployee {

    @Expose
    @SerializedName("NIP")
    private String nip;

    @Expose
    @SerializedName("kode_unit_kerja")
    private String kodeUnitKerja;

    @Expose
    @SerializedName("nama_unit")
    private String namaUnit;

    @Expose
    @SerializedName("nama")
    private String namaKaryawan;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("jabatan")
    private String jabatan;

    @Expose
    @SerializedName("last_online")
    private String lastOnline;

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getKodeUnitKerja() {
        return kodeUnitKerja;
    }

    public void setKodeUnitKerja(String kodeUnitKerja) {
        this.kodeUnitKerja = kodeUnitKerja;
    }

    public String getNamaUnit() {
        return namaUnit;
    }

    public void setNamaUnit(String namaUnit) {
        this.namaUnit = namaUnit;
    }

    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline;
    }
}
