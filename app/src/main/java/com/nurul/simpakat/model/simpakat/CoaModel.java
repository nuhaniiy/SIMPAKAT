package com.nurul.simpakat.model.simpakat;

public class CoaModel {
    private String kodeCoa;
    private String kodeParent;
    private String namaCoa;

    public CoaModel() {
    }

    public CoaModel(String kodeCoa, String kodeParent, String namaCoa) {
        this.kodeCoa = kodeCoa;
        this.kodeParent = kodeParent;
        this.namaCoa = namaCoa;
    }

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
