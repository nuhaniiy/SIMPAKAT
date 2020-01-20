package com.nurul.simpakat.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nurul.simpakat.model.BaseResponse;
import com.nurul.simpakat.model.simpakat.ListUnitKerja;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListDataUnitKerjaResponse extends BaseResponse {

    @Expose
    @SerializedName("data_unitkerja")
    private ArrayList<ListUnitKerja> listUnitKerjas = new ArrayList<>();

    @Expose
    @SerializedName("count")
    public int count;

    public ArrayList<ListUnitKerja> getListUnitKerja() {
        return listUnitKerjas;
    }

    public void setListUnitKerja(ArrayList<ListUnitKerja> listUnitKerjas) {
        this.listUnitKerjas = listUnitKerjas;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
