package com.nurul.simpakat.model.simpakat;

public class EmployeeModel {
    private String nip;
    private String kodeUnitKerja;
    private String namaUnitKerja;
    private String namaKaryawan;
    private String email;
    private String jabatan;
    private String lastOnline;

    public EmployeeModel() {
    }

    public EmployeeModel(String nip, String kodeUnitKerja, String namaUnitKerja, String namaKaryawan, String email, String jabatan, String lastOnline) {
        this.nip = nip;
        this.kodeUnitKerja = kodeUnitKerja;
        this.namaKaryawan = namaKaryawan;
        this.email = email;
        this.jabatan = jabatan;
        this.lastOnline = lastOnline;
    }

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

    public String getNamaUnitKerja() {
        return namaUnitKerja;
    }

    public void setNamaUnitKerja(String namaUnitKerja) {
        this.namaUnitKerja = namaUnitKerja;
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
