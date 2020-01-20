package com.nurul.simpakat.view;

import com.nurul.simpakat.BaseView;
import com.nurul.simpakat.model.simpakat.DataUnitKerjaModel;
import com.nurul.simpakat.model.simpakat.ListUnitKerja;

import java.util.ArrayList;

public interface UnitKerjaView extends BaseView<DataUnitKerjaModel>  {
    void onUnitKerjaAdded();
    void onUnitKerjaFailed(String message);
    void onUnitKerjaNull();
    void onUnitKerjaRetrieves(ArrayList<ListUnitKerja> listUnitKerjas);
}
