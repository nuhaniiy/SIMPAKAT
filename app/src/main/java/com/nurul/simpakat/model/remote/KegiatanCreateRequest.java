package com.nurul.simpakat.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class KegiatanCreateRequest {

    @Expose
    @SerializedName("id_proker")
    private String idProker;

    @Expose
    @SerializedName("NIP")
    private String nip;

    @Expose
    @SerializedName("nama_kegiatan")
    private String namaKegiatan;

    @Expose
    @SerializedName("nominal_anggaran")
    private String nominalAnggaran;

    @Expose
    @SerializedName("tanggal_kegiatan")
    private String tanggalKegiatan;

    @Expose
    @SerializedName("status_kegiatan")
    private String statusKegiatan;

    public String getIdProker() {
        return idProker;
    }

    public void setIdProker(String idProker) {
        this.idProker = idProker;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public void setNamaKegiatan(String namaKegiatan) {
        this.namaKegiatan = namaKegiatan;
    }

    public String getNominalAnggaran() {
        return nominalAnggaran;
    }

    public void setNominalAnggaran(String nominalAnggaran) {
        this.nominalAnggaran = nominalAnggaran;
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
