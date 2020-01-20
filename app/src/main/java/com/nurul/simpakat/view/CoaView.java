package com.nurul.simpakat.view;

import com.nurul.simpakat.BaseView;
import com.nurul.simpakat.model.simpakat.CoaModel;
import com.nurul.simpakat.model.simpakat.ListDataCoa;

import java.util.ArrayList;

public interface CoaView extends BaseView<CoaModel> {
    void onCoaAdded();
    void onCoaFailed(String message);
    void onCoaNull();
    void onCoaRetrieves(ArrayList<ListDataCoa> listDataCoas);
}
