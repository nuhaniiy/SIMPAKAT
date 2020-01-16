package com.nurul.simpakat.view;

import com.nurul.simpakat.BaseView;
import com.nurul.simpakat.model.simpakat.ListPengajuan;
import com.nurul.simpakat.model.simpakat.PengajuanModel;

import java.util.ArrayList;

public interface PengajuanView extends BaseView<PengajuanModel> {
    void onPengajuanAdded();
    void onPengajuanFailed(String message);
    void onPengajuanNull();
    void onPengajuanRetrieves(ArrayList<ListPengajuan> listPengajuans);
}
