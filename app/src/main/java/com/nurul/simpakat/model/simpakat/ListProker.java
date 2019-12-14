package com.nurul.simpakat.model.simpakat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListProker {

    @Expose
    @SerializedName("id_proker")
    private String idProker;

    @Expose
    @SerializedName("id_user")
    private String idUser;

    @Expose
    @SerializedName("kode_unit_kerja")
    private String kodeUnitKerja;

    @Expose
    @SerializedName("nama_proker")
    private String namaProker;

    @Expose
    @SerializedName("keterangan")
    private String keterangan;

    @Expose
    @SerializedName("id_coa")
    private String idCOA;

    @Expose
    @SerializedName("jenis_pembiayaan")
    private String jenisPembiayaan;

    @Expose
    @SerializedName("biaya")
    private String biaya;

    @Expose
    @SerializedName("bulan_kegiatan")
    private String bulanKegiatan;

    @Expose
    @SerializedName("status_proker")
    private String statusProker;

    public String getIdProker() {
        return idProker;
    }

    public void setIdProker(String idProker) {
        this.idProker = idProker;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getKodeUnitKerja() {
        return kodeUnitKerja;
    }

    public void setKodeUnitKerja(String kodeUnitKerja) {
        this.kodeUnitKerja = kodeUnitKerja;
    }

    public String getNamaProker() {
        return namaProker;
    }

    public void setNamaProker(String namaProker) {
        this.namaProker = namaProker;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getIdCOA() {
        return idCOA;
    }

    public void setIdCOA(String idCOA) {
        this.idCOA = idCOA;
    }

    public String getJenisPembiayaan() {
        return jenisPembiayaan;
    }

    public void setJenisPembiayaan(String jenisPembiayaan) {
        this.jenisPembiayaan = jenisPembiayaan;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public String getBulanKegiatan() {
        return bulanKegiatan;
    }

    public void setBulanKegiatan(String bulanKegiatan) {
        this.bulanKegiatan = bulanKegiatan;
    }

    public String getStatusProker() {
        return statusProker;
    }

    public void setStatusProker(String statusProker) {
        this.statusProker = statusProker;
    }
}
