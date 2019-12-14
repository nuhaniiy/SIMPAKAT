package com.nurul.simpakat.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nurul.simpakat.model.BaseResponse;
import com.nurul.simpakat.model.simpakat.ListPengajuan;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListDataPengajuanResponse extends BaseResponse {

    @Expose
    @SerializedName("data_pengajuan")
    private ArrayList<ListPengajuan> listPengajuans = new ArrayList<>();

    @Expose
    @SerializedName("count")
    public int count;

    public ArrayList<ListPengajuan> getListPengajuans() {
        return listPengajuans;
    }

    public void setListPengajuans(ArrayList<ListPengajuan> listPengajuans) {
        this.listPengajuans = listPengajuans;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
