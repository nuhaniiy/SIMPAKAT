package com.nurul.simpakat.model.simpakat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPengajuan {

    @Expose
    @SerializedName("id_pengajuan")
    private String idPengajuan;

    @Expose
    @SerializedName("NIP")
    private String nip;

    @Expose
    @SerializedName("nama")
    private String namaKaryawan;

    @Expose
    @SerializedName("id_proker")
    private String idProker;

    @Expose
    @SerializedName("nama_proker")
    private String namaProker;

    @Expose
    @SerializedName("biaya")
    private String biaya;

    @Expose
    @SerializedName("biaya_terpakai")
    private String biayaTerpakai;

    @Expose
    @SerializedName("sisa_biaya")
    private String sisaBiaya;

    @Expose
    @SerializedName("biaya_diajukan")
    private String biayaDiajukan;

    @Expose
    @SerializedName("dibayar_kepada")
    private String dibayarKepada;

    @Expose
    @SerializedName("jabatan")
    private String jabatan;

    @Expose
    @SerializedName("no_rekening")
    private String noRekening;

    @Expose
    @SerializedName("tanggal_pengajuan")
    private String tanggalPengajuan;

    @Expose
    @SerializedName("persetujuan_dekan")
    private String persetujuanDekan;

    @Expose
    @SerializedName("persetujuan_warek1")
    private String persetujuanWarek1;

    @Expose
    @SerializedName("persetujuan_warek2")
    private String persetujuanWarek2;

    @Expose
    @SerializedName("persetujuan_rektor")
    private String persetujuanRektor;

    @Expose
    @SerializedName("status_pengajuan")
    private String statusPengajuan;

    public String getIdPengajuan() {
        return idPengajuan;
    }

    public void setIdPengajuan(String idPengajuan) {
        this.idPengajuan = idPengajuan;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }

    public String getIdProker() {
        return idProker;
    }

    public void setIdProker(String idProker) {
        this.idProker = idProker;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public String getBiayaTerpakai() {
        return biayaTerpakai;
    }

    public void setBiayaTerpakai(String biayaTerpakai) {
        this.biayaTerpakai = biayaTerpakai;
    }

    public String getSisaBiaya() {
        return sisaBiaya;
    }

    public void setSisaBiaya(String sisaBiaya) {
        this.sisaBiaya = sisaBiaya;
    }

    public String getBiayaDiajukan() {
        return biayaDiajukan;
    }

    public void setBiayaDiajukan(String biayaDiajukan) {
        this.biayaDiajukan = biayaDiajukan;
    }

    public String getDibayarKepada() {
        return dibayarKepada;
    }

    public void setDibayarKepada(String dibayarKepada) {
        this.dibayarKepada = dibayarKepada;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }

    public String getTanggalPengajuan() {
        return tanggalPengajuan;
    }

    public void setTanggalPengajuan(String tanggalPengajuan) {
        this.tanggalPengajuan = tanggalPengajuan;
    }

    public String getPersetujuanDekan() {
        return persetujuanDekan;
    }

    public void setPersetujuanDekan(String persetujuanDekan) {
        this.persetujuanDekan = persetujuanDekan;
    }

    public String getPersetujuanWarek1() {
        return persetujuanWarek1;
    }

    public void setPersetujuanWarek1(String persetujuanWarek1) {
        this.persetujuanWarek1 = persetujuanWarek1;
    }

    public String getPersetujuanWarek2() {
        return persetujuanWarek2;
    }

    public void setPersetujuanWarek2(String persetujuanWarek2) {
        this.persetujuanWarek2 = persetujuanWarek2;
    }

    public String getPersetujuanRektor() {
        return persetujuanRektor;
    }

    public void setPersetujuanRektor(String persetujuanRektor) {
        this.persetujuanRektor = persetujuanRektor;
    }

    public String getStatusPengajuan() {
        return statusPengajuan;
    }

    public void setStatusPengajuan(String statusPengajuan) {
        this.statusPengajuan = statusPengajuan;
    }

    public String getNamaProker() {
        return namaProker;
    }

    public void setNamaProker(String namaProker) {
        this.namaProker = namaProker;
    }
}
