package com.nurul.simpakat.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nurul.simpakat.model.BaseResponse;
import com.nurul.simpakat.model.simpakat.ListKegiatan;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListKegiatanResponse extends BaseResponse {

    @Expose
    @SerializedName("data_kegiatan")
    private ArrayList<ListKegiatan> listKegiatans = new ArrayList<>();

    @Expose
    @SerializedName("count")
    public int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<ListKegiatan> getListKegiatans() {
        return listKegiatans;
    }

    public void setListKegiatans(ArrayList<ListKegiatan> listKegiatans) {
        this.listKegiatans = listKegiatans;
    }
}
