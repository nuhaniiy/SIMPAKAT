package com.nurul.simpakat.view;

import com.nurul.simpakat.BaseView;
import com.nurul.simpakat.model.simpakat.KegiatanModel;
import com.nurul.simpakat.model.simpakat.ListKegiatan;

import java.util.ArrayList;

public interface KegiatanView extends BaseView<KegiatanModel> {
    void onKegiatanAdded();
    void onKegiatanFailed(String message);
    void onKegiatanNull();
    void onKegiatanRetrieves(ArrayList<ListKegiatan> listKegiatans);
}
