package com.nurul.simpakat.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nurul.simpakat.model.BaseResponse;
import com.nurul.simpakat.model.simpakat.ListEmployee;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListDataEmployeeResponse extends BaseResponse {

    @Expose
    @SerializedName("data_karyawan")
    private ArrayList<ListEmployee> listEmployees = new ArrayList<>();

    @Expose
    @SerializedName("count")
    public int count;

    public ArrayList<ListEmployee> getListEmployees() {
        return listEmployees;
    }

    public void setListEmployees(ArrayList<ListEmployee> listEmployees) {
        this.listEmployees = listEmployees;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
