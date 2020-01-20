package com.nurul.simpakat.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nurul.simpakat.model.BaseResponse;
import com.nurul.simpakat.model.simpakat.ListDataCoa;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListDataCoaResponse extends BaseResponse {
    @Expose
    @SerializedName("data_coa")
    private ArrayList<ListDataCoa> listDataCoas = new ArrayList<>();

    @Expose
    @SerializedName("count")
    public int count;

    public ArrayList<ListDataCoa> getListDataCoas() {
        return listDataCoas;
    }

    public void setListDataCoas(ArrayList<ListDataCoa> listDataCoas) {
        this.listDataCoas = listDataCoas;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
