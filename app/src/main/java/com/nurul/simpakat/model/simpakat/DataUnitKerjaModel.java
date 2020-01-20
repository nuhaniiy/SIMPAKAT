package com.nurul.simpakat.model.simpakat;

public class DataUnitKerjaModel {
    private String kodeUnitKerja;
    private String namaUnitKerja;
    private String statusUnitKerja;

    public DataUnitKerjaModel() {
    }

    public DataUnitKerjaModel(String kodeUnitKerja, String namaUnitKerja, String statusUnitKerja) {
        this.kodeUnitKerja = kodeUnitKerja;
        this.namaUnitKerja = namaUnitKerja;
        this.statusUnitKerja = statusUnitKerja;
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

    public String getStatusUnitKerja() {
        return statusUnitKerja;
    }

    public void setStatusUnitKerja(String statusUnitKerja) {
        this.statusUnitKerja = statusUnitKerja;
    }
}
