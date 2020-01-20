package com.nurul.simpakat.model.simpakat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListKegiatan {

    @Expose
    @SerializedName("id_kegiatan")
    private String idKegiatan;

    @Expose
    @SerializedName("nama_kegiatan")
    private String namaKegiatan;

    @Expose
    @SerializedName("id_proker")
    private String idProker;

    @Expose
    @SerializedName("nama_proker")
    private String namaProker;

    @Expose
    @SerializedName("NIP")
    private String nip;

    @Expose
    @SerializedName("nama_pengaju")
    private String namaPengaju;

    @Expose
    @SerializedName("nominal_anggaran")
    private String nominal;

    @Expose
    @SerializedName("tanggal_kegiatan")
    private String tanggalKegiatan;

    @Expose
    @SerializedName("status_kegiatan")
    private String statusKegiatan;

    public String getIdKegiatan() {
        return idKegiatan;
    }

    public void setIdKegiatan(String idKegiatan) {
        this.idKegiatan = idKegiatan;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public void setNamaKegiatan(String namaKegiatan) {
        this.namaKegiatan = namaKegiatan;
    }

    public String getIdProker() {
        return idProker;
    }

    public void setIdProker(String idProker) {
        this.idProker = idProker;
    }

    public String getNamaProker() {
        return namaProker;
    }

    public void setNamaProker(String namaProker) {
        this.namaProker = namaProker;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNamaPengaju() {
        return namaPengaju;
    }

    public void setNamaPengaju(String namaPengaju) {
        this.namaPengaju = namaPengaju;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getTanggalKegiatan() {
        return tanggalKegiatan;
    }

    public void setTanggalKegiatan(String tanggalKegiatan) {
        this.tanggalKegiatan = tanggalKegiatan;
    }

    public String getStatusKegiatan() {
        return statusKegiatan;
    }

    public void setStatusKegiatan(String statusKegiatan) {
        this.statusKegiatan = statusKegiatan;
    }
}
