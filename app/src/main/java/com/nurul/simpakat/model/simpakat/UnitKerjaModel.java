package com.nurul.simpakat.model.simpakat;

public class UnitKerjaModel {
    private int kodeUnitKerj;
    private String namaUnitKerj;
    private String statusUnitKerja;

    public UnitKerjaModel(int kodeUnitKerj, String namaUnitKerj, String statusUnitKerja) {
        this.setKodeUnitKerj(kodeUnitKerj);
        this.setNamaUnitKerj(namaUnitKerj);
        this.setStatusUnitKerja(statusUnitKerja);
    }

    public int getKodeUnitKerj() {
        return kodeUnitKerj;
    }

    public void setKodeUnitKerj(int kodeUnitKerj) {
        this.kodeUnitKerj = kodeUnitKerj;
    }

    public String getNamaUnitKerj() {
        return namaUnitKerj;
    }

    public void setNamaUnitKerj(String namaUnitKerj) {
        this.namaUnitKerj = namaUnitKerj;
    }

    public String getStatusUnitKerja() {
        return statusUnitKerja;
    }

    public void setStatusUnitKerja(String statusUnitKerja) {
        this.statusUnitKerja = statusUnitKerja;
    }
}
