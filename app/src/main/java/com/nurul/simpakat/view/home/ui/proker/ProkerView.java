package com.nurul.simpakat.view.home.ui.proker;

import com.nurul.simpakat.BaseView;
import com.nurul.simpakat.model.simpakat.ListProker;

import java.util.ArrayList;

public interface ProkerView extends BaseView<ProkerModel> {
    void onProkerAdded();
    void onProkerNull();
    void onProkerRetrieve(ArrayList<ListProker> listProkers);
}
