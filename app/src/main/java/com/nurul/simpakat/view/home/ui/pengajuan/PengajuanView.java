package com.nurul.simpakat.view.home.ui.pengajuan;

import com.nurul.simpakat.BaseView;
import com.nurul.simpakat.model.simpakat.ListPengajuan;

import java.util.ArrayList;

public interface PengajuanView extends BaseView<PengajuanModel> {
    void onPengajuanAdded();
    void onPengajuanFailed(String message);
    void onPengajuanNull();
    void onPengajuanRetrieves(ArrayList<ListPengajuan> listPengajuans);
}
