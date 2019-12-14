package com.nurul.simpakat.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nurul.simpakat.model.BaseResponse;
import com.nurul.simpakat.model.simpakat.ListProker;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListDatProkerResponse extends BaseResponse {

    @Expose
    @SerializedName("data_proker")
    private ArrayList<ListProker> listProkers = new ArrayList<>();

    @Expose
    @SerializedName("count")
    public int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<ListProker> getListProkers() {
        return listProkers;
    }

    public void setListProkers(ArrayList<ListProker> listProkers) {
        this.listProkers = listProkers;
    }
}
