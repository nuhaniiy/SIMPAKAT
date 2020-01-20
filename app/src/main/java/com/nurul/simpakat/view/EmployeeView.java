package com.nurul.simpakat.view;

import com.nurul.simpakat.BaseView;
import com.nurul.simpakat.model.simpakat.EmployeeModel;
import com.nurul.simpakat.model.simpakat.ListEmployee;

import java.util.ArrayList;

public interface EmployeeView extends BaseView<EmployeeModel> {
    void onEmployeeAdded();
    void onEmployeeFailed(String message);
    void onEmployeeNull();
    void onEmployeeRetrieves(ArrayList<ListEmployee> listEmployees);
}
