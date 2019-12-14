package com.nurul.simpakat.view.home.ui.proker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.view.home.adapter.ListProkerAdapter;
import com.nurul.simpakat.model.simpakat.ListProker;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgramKerjaFragment extends AbstractFragmentView<ProkerModel> implements ProkerView {

    @BindView(R.id.proker_list_loading)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @BindView(R.id.fab_add_program_kerja)
    FloatingActionButton fabAddData;

    @BindView(R.id.program_kerja_layout)
    RelativeLayout layoutDataProker;

    @BindView(R.id.no_list_data)
    RelativeLayout layoutNoData;

    @BindView(R.id.recycler_prgram_kerja)
    RecyclerView rvListProker;

    private ProkerPresenter prokerPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("ProkerBroadcast"));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_program_kerja, container, false);

        ButterKnife.bind(this, root);

        fabAddData.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AddProgramKerjaActivity.class));
        });

        super.viewModel = new ProkerModel();
        prokerPresenter = new ProkerPresenter();
        prokerPresenter.init(super.viewModel, this, new ApiProvider());

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prokerPresenter.requestDataProkerFromServer();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, Intent intent) {
//            String topic = intent.getAction();
            String from = intent.getStringExtra("from");
            if(from.equals("added")) {
                prokerPresenter.requestDataProkerFromServer();
            }
        }
    };

    @Override
    public void onProkerAdded() {

    }

    @Override
    public void onProkerNull() {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataProker.setVisibility(View.GONE);
        layoutNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProkerRetrieve(ArrayList<ListProker> listProkers) {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataProker.setVisibility(View.VISIBLE);
        layoutNoData.setVisibility(View.GONE);

        setDataToRecyclerView(listProkers);
    }

    @Override
    public void updateModel(ProkerModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }

    public void setDataToRecyclerView(ArrayList<ListProker> listProkerArrayList) {
        ListProkerAdapter adapter = new ListProkerAdapter(listProkerArrayList, getContext());
        rvListProker.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListProker.setItemAnimator(new DefaultItemAnimator());
        rvListProker.setAdapter(adapter);
    }
}