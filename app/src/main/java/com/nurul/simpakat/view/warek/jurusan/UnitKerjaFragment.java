package com.nurul.simpakat.view.warek.jurusan;


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
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.model.simpakat.DataUnitKerjaModel;
import com.nurul.simpakat.model.simpakat.ListUnitKerja;
import com.nurul.simpakat.presenter.UnitKerjaPresenter;
import com.nurul.simpakat.view.UnitKerjaView;
import com.nurul.simpakat.view.warek.adapter.ListUnitKerjaAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnitKerjaFragment extends AbstractFragmentView<DataUnitKerjaModel> implements UnitKerjaView,
        ListUnitKerjaAdapter.ListUnitKerjaClicked {

    @BindView(R.id.fab_add_unitkerja)
    FloatingActionButton fabAddUnitkerja;

    @BindView(R.id.unitkerja_list_loading)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @BindView(R.id.data_unitkerja_layout)
    RelativeLayout layoutDataUnitkerja;

    @BindView(R.id.no_data_unitkerja)
    RelativeLayout layoutNoDataUnitkerja;

    @BindView(R.id.recycler_unitkerja)
    RecyclerView rvDataUnitkerja;

    private UnitKerjaPresenter unitKerjaPresenter;

    public UnitKerjaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("UnitKerjaBroadcast"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_unit_kerja, container, false);

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        fabAddUnitkerja.setOnClickListener(view -> {
//            startActivity(new Intent(getActivity(), AddPengajuanProkerActivity.class));
        });

        super.viewModel = new DataUnitKerjaModel();
        unitKerjaPresenter = new UnitKerjaPresenter();
        unitKerjaPresenter.init(super.viewModel, this, new ApiProvider());

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unitKerjaPresenter.requestDataUnitKerjaFromServer();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, Intent intent) {
//            String topic = intent.getAction();
            String from = intent.getStringExtra("from");
            if(from.equals("added")) {
                unitKerjaPresenter.requestDataUnitKerjaFromServer();
            }
        }
    };

    @Override
    public void updateModel(DataUnitKerjaModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }

    @Override
    public void onUnitKerjaAdded() {

    }

    @Override
    public void onUnitKerjaFailed(String message) {

    }

    @Override
    public void onUnitKerjaNull() {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataUnitkerja.setVisibility(View.GONE);
        layoutNoDataUnitkerja.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUnitKerjaRetrieves(ArrayList<ListUnitKerja> listUnitKerjas) {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataUnitkerja.setVisibility(View.VISIBLE);
        layoutNoDataUnitkerja.setVisibility(View.GONE);

        setDataToRecyclerView(listUnitKerjas);
    }

    public void setDataToRecyclerView(ArrayList<ListUnitKerja> listUnitKerjaArrayList) {
        ListUnitKerjaAdapter adapter = new ListUnitKerjaAdapter(listUnitKerjaArrayList, this);
        rvDataUnitkerja.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDataUnitkerja.setItemAnimator(new DefaultItemAnimator());
        rvDataUnitkerja.setAdapter(adapter);
    }

    @Override
    public void ListUnitKerjaClicked(ListUnitKerja list) {

    }
}
