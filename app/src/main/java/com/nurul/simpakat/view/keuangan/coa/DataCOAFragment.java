package com.nurul.simpakat.view.keuangan.coa;


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
import com.nurul.simpakat.model.simpakat.CoaModel;
import com.nurul.simpakat.model.simpakat.ListDataCoa;
import com.nurul.simpakat.presenter.CoaPresenter;
import com.nurul.simpakat.view.CoaView;
import com.nurul.simpakat.view.keuangan.adapter.ListDataCoaAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataCOAFragment extends AbstractFragmentView<CoaModel> implements CoaView, ListDataCoaAdapter.ListCoaClicked {

    @BindView(R.id.fab_add_coa)
    FloatingActionButton fabAddCoa;

    @BindView(R.id.coa_list_loading)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @BindView(R.id.data_coa_layout)
    RelativeLayout layoutDataCoa;

    @BindView(R.id.no_data_coa)
    RelativeLayout layoutNoDataCoa;

    @BindView(R.id.recycler_coa)
    RecyclerView rvDataCoa;

    private CoaPresenter coaPresenter;

    public DataCOAFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("CoaBroadcast"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_data_coa, container, false);

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        fabAddCoa.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AddCoaActivity.class));
        });

        super.viewModel = new CoaModel();
        coaPresenter = new CoaPresenter();
        coaPresenter.init(super.viewModel, this, new ApiProvider());

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        coaPresenter.requestDataCoaFromServer();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, Intent intent) {
//            String topic = intent.getAction();
            String from = intent.getStringExtra("from");
            if(from.equals("added")) {
                coaPresenter.requestDataCoaFromServer();
            }
        }
    };

    @Override
    public void updateModel(CoaModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }

    @Override
    public void onCoaAdded() {

    }

    @Override
    public void onCoaFailed(String message) {

    }

    @Override
    public void onCoaNull() {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataCoa.setVisibility(View.GONE);
        layoutNoDataCoa.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCoaRetrieves(ArrayList<ListDataCoa> listDataCoas) {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataCoa.setVisibility(View.VISIBLE);
        layoutNoDataCoa.setVisibility(View.GONE);

        setDataToRecyclerView(listDataCoas);
    }

    public void setDataToRecyclerView(ArrayList<ListDataCoa> listDataCoaArrayList) {
        ListDataCoaAdapter adapter = new ListDataCoaAdapter(listDataCoaArrayList, this);
        rvDataCoa.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDataCoa.setItemAnimator(new DefaultItemAnimator());
        rvDataCoa.setAdapter(adapter);
    }

    @Override
    public void ListCoaClicked(ListDataCoa list) {

    }
}
