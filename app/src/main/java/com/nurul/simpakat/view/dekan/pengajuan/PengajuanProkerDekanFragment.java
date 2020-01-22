package com.nurul.simpakat.view.dekan.pengajuan;


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
import com.nurul.simpakat.model.simpakat.ListPengajuan;
import com.nurul.simpakat.presenter.PengajuanPresenter;
import com.nurul.simpakat.view.dekan.adapter.ListPengajuanDosenAdapter;
import com.nurul.simpakat.view.home.adapter.ListPengajuanAdapter;
import com.nurul.simpakat.view.home.ui.pengajuan.AddPengajuanProkerActivity;
import com.nurul.simpakat.model.simpakat.PengajuanModel;
import com.nurul.simpakat.view.PengajuanView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PengajuanProkerDekanFragment extends AbstractFragmentView<PengajuanModel> implements PengajuanView, ListPengajuanAdapter.ListPengajuanClicked {

    @BindView(R.id.fab_add_pengajuan_program_kerja)
    FloatingActionButton fabAddSubmission;

    @BindView(R.id.pengajuan_list_loading)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @BindView(R.id.pengajuan_program_kerja_layout)
    RelativeLayout layoutDataPengajuan;

    @BindView(R.id.no_list_data_pengajuan)
    RelativeLayout layoutNoDataPengajuan;

    @BindView(R.id.recycler_pengajuan_prgram_kerja)
    RecyclerView rvDataPengajuan;

    @BindView(R.id.layout_bottom_sheet)
    RelativeLayout bottomSheetLayout;

    @BindView(R.id.vw_nama_proker)
    TextView namaProker;

    @BindView(R.id.vw_nama_pengaju)
    TextView namaPengaju;

    @BindView(R.id.vw_biaya)
    TextView biaya;

    @BindView(R.id.vw_biaya_terpakai)
    TextView biayaTerpakai;

    @BindView(R.id.vw_sisa_biaya)
    TextView sisaBiaya;

    @BindView(R.id.vw_biaya_diajukan)
    TextView biayaDiajukan;

    @BindView(R.id.vw_dibayar_kepada)
    TextView dibayarKepada;

    @BindView(R.id.vw_jabatan)
    TextView jabatan;

    @BindView(R.id.vw_no_rekening)
    TextView noRekening;

    @BindView(R.id.vw_tanggal_pengajuan)
    TextView tanggalPengajuan;

    private PengajuanPresenter pengajuanPresenter;
    private Boolean flagView = false;

    public PengajuanProkerDekanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("PengajuanProkerBroadcast"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pengajuan_proker_dekan, container, false);

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        fabAddSubmission.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AddPengajuanDekanActivity.class));
        });

        super.viewModel = new PengajuanModel();
        pengajuanPresenter = new PengajuanPresenter();
        pengajuanPresenter.init(super.viewModel, this, new ApiProvider());

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pengajuanPresenter.requestDataPengajuanFromServer(getAppPreference().getString(Constanta.PREF_ID, ""));
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, Intent intent) {
//            String topic = intent.getAction();
            String from = intent.getStringExtra("from");
            if(from.equals("added")) {
                pengajuanPresenter.requestDataPengajuanFromServer(getAppPreference().getString(Constanta.PREF_ID, ""));
            }
        }
    };

    @Override
    public void onPengajuanAdded() {

    }

    @Override
    public void onPengajuanFailed(String message) {

    }

    @Override
    public void onPengajuanNull() {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataPengajuan.setVisibility(View.GONE);
        layoutNoDataPengajuan.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPengajuanRetrieves(ArrayList<ListPengajuan> listPengajuans) {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataPengajuan.setVisibility(View.VISIBLE);
        layoutNoDataPengajuan.setVisibility(View.GONE);

        setDataToRecyclerView(listPengajuans);
    }

    @Override
    public void updateModel(PengajuanModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }

    public void setDataToRecyclerView(ArrayList<ListPengajuan> listPengajuanArrayList) {
//        ListPengajuanDosenAdapter adapter = new ListPengajuanDosenAdapter(listPengajuanArrayList, this);
        ListPengajuanAdapter adapter = new ListPengajuanAdapter(listPengajuanArrayList, this);
        rvDataPengajuan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDataPengajuan.setItemAnimator(new DefaultItemAnimator());
        rvDataPengajuan.setAdapter(adapter);
    }

    @Override
    public void ListPengajuanClicked(ListPengajuan list) {
        if(flagView) {
            return;
        } else {
            flagView = true;
            TranslateAnimation slide = new TranslateAnimation(0, 0, 1200, 0);
            slide.setDuration(1000);
//            slide.setFillAfter(true);
            bottomSheetLayout.startAnimation(slide);
            bottomSheetLayout.setVisibility(View.VISIBLE);
            bottomSheetLayout.bringToFront();

            clearData();

            namaProker.setText(list.getNamaProker());
            namaPengaju.setText(list.getNamaKaryawan() + " - " + list.getNip());
            biaya.setText(list.getBiaya());
            biayaTerpakai.setText(list.getBiayaTerpakai());
            sisaBiaya.setText(list.getSisaBiaya());
            biayaDiajukan.setText(list.getBiayaDiajukan());
            dibayarKepada.setText(list.getDibayarKepada());
            jabatan.setText(list.getJabatan());
            noRekening.setText(list.getNoRekening());
        }
    }

    @OnClick(R.id.tv_slide_down)
    void onSlideDownData() {
        flagView = false;
//        Toast.makeText(getActivity().getApplicationContext(), "click", Toast.LENGTH_LONG).show();
        TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1200);
        slide.setDuration(1000);
//        slide.setFillAfter(true);
        bottomSheetLayout.startAnimation(slide);
        bottomSheetLayout.setVisibility(View.GONE);
    }

    private void clearData() {
        namaProker.setText("");
        namaPengaju.setText("");
        biaya.setText("");
        biayaTerpakai.setText("");
        sisaBiaya.setText("");
        biayaDiajukan.setText("");
        dibayarKepada.setText("");
        jabatan.setText("");
        noRekening.setText("");
    }
}
