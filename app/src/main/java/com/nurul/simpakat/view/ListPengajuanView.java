package com.nurul.simpakat.view;

import com.nurul.simpakat.BaseView;
import com.nurul.simpakat.model.simpakat.ListPengajuan;
import com.nurul.simpakat.model.simpakat.ListPengajuanModel;

import java.util.ArrayList;

public interface ListPengajuanView extends BaseView<ListPengajuanModel> {
    void onListPengajuanApprove();
    void onListPengajuanFailed(String message);
    void onListPengajuanNull();
    void onListPengajuanRetrieves(ArrayList<ListPengajuan> listPengajuans);
}
