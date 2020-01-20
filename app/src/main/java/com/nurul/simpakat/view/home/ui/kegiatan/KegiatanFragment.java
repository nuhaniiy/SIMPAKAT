package com.nurul.simpakat.view.home.ui.kegiatan;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.model.simpakat.KegiatanModel;
import com.nurul.simpakat.model.simpakat.ListKegiatan;
import com.nurul.simpakat.presenter.KegiatanPresenter;
import com.nurul.simpakat.view.KegiatanView;
import com.nurul.simpakat.view.home.adapter.ListKegiatanAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class KegiatanFragment extends AbstractFragmentView<KegiatanModel> implements KegiatanView,ListKegiatanAdapter.ListKegiatanClicked {

    @BindView(R.id.fab_add_kegiatan)
    FloatingActionButton fabAddKegiatan;

    @BindView(R.id.kegiatan_list_loading)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @BindView(R.id.kegiatan_layout)
    RelativeLayout layoutDataKegiatan;

    @BindView(R.id.no_list_data_kegiatan)
    RelativeLayout layoutNoDataKegiatan;

    @BindView(R.id.recycler_kegiatan)
    RecyclerView rvDataKegiatan;

    @BindView(R.id.layout_bottom_sheet)
    RelativeLayout bottomSheetLayout;

    @BindView(R.id.vw_nama_kegiatan)
    TextView namaKegiatan;

    @BindView(R.id.vw_nama_pengaju)
    TextView namaPengaju;

    @BindView(R.id.vw_nama_proker)
    TextView namaProker;

    @BindView(R.id.vw_nominal)
    TextView nominalAnggaran;

    @BindView(R.id.vw_tanggal_kegiatan)
    TextView tanggalKegiatan;

    private Boolean flagView = false;

    private KegiatanPresenter kegiatanPresenter;

    public KegiatanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("KegiatanBroadcast"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_kegiatan, container, false);

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        fabAddKegiatan.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AddKegiatanActivity.class));
        });

        super.viewModel = new KegiatanModel();
        kegiatanPresenter = new KegiatanPresenter();
        kegiatanPresenter.init(super.viewModel, this, new ApiProvider());

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        kegiatanPresenter.requestDataKegiatanFromServer(getAppPreference().getString(Constanta.PREF_ID, ""));
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, Intent intent) {
//            String topic = intent.getAction();
            String from = intent.getStringExtra("from");
            if(from.equals("added")) {
                kegiatanPresenter.requestDataKegiatanFromServer(getAppPreference().getString(Constanta.PREF_ID, ""));
            }
        }
    };

    @Override
    public void updateModel(KegiatanModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }

    @Override
    public void onKegiatanAdded() {

    }

    @Override
    public void onKegiatanFailed(String message) {

    }

    @Override
    public void onKegiatanNull() {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataKegiatan.setVisibility(View.GONE);
        layoutNoDataKegiatan.setVisibility(View.VISIBLE);
    }

    @Override
    public void onKegiatanRetrieves(ArrayList<ListKegiatan> listKegiatans) {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataKegiatan.setVisibility(View.VISIBLE);
        layoutNoDataKegiatan.setVisibility(View.GONE);

        setDataToRecyclerView(listKegiatans);
    }

    public void setDataToRecyclerView(ArrayList<ListKegiatan> listKegiatanArrayList) {
        ListKegiatanAdapter adapter = new ListKegiatanAdapter(listKegiatanArrayList, this);
        rvDataKegiatan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDataKegiatan.setItemAnimator(new DefaultItemAnimator());
        rvDataKegiatan.setAdapter(adapter);
    }

    @Override
    public void ListKegiatanClicked(ListKegiatan list) {
        if(flagView) {
            return;
        } else {
            flagView = true;
//            bottomSheetLayout.bringToFront();
            TranslateAnimation slide = new TranslateAnimation(0, 0, 2400, 0);
            slide.setDuration(1000);
//            slide.setFillAfter(true);
            bottomSheetLayout.startAnimation(slide);
            bottomSheetLayout.setVisibility(View.VISIBLE);

            clearData();

            namaKegiatan.setText(list.getNamaKegiatan());
            namaPengaju.setText(": "+list.getNamaPengaju());
            namaProker.setText(": "+list.getNamaProker());
            nominalAnggaran.setText(": "+list.getNominal());
            tanggalKegiatan.setText(": "+list.getTanggalKegiatan());
        }
    }

    @OnClick(R.id.tv_slide_down)
    void onSlideDownData() {
//        Toast.makeText(getActivity().getApplicationContext(), "click", Toast.LENGTH_LONG).show();
        flagView = false;
        TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 2400);
        slide.setDuration(1000);
//        slide.setFillAfter(true);
        bottomSheetLayout.startAnimation(slide);
        bottomSheetLayout.setVisibility(View.GONE);
    }

    private void clearData() {
        namaKegiatan.setText("");
        namaPengaju.setText("");
        namaProker.setText("");
        nominalAnggaran.setText("");
        tanggalKegiatan.setText("");
    }

    @OnClick(R.id.btn_laporkan)
    void onDataReported() {
//        name = namaPengaju.getText().toString();
//        TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1200);
//        slide.setDuration(1000);
//        slide.setFillAfter(true);
//        bottomSheetLayout.startAnimation(slide);
//        bottomSheetLayout.setVisibility(View.GONE);
//        clearData();
//        avLoadingIndicatorView.setVisibility(View.VISIBLE);
//        layoutDataPengajuan.setVisibility(View.GONE);
//        updateApprovalSubmission("Setuju", idPengajuan, getAppPreference().getString(Constanta.PREF_JABATAN, ""));
    }
}
