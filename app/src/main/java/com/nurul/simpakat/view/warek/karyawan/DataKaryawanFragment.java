package com.nurul.simpakat.view.warek.karyawan;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.model.simpakat.EmployeeModel;
import com.nurul.simpakat.model.simpakat.ListEmployee;
import com.nurul.simpakat.presenter.EmployeePresenter;
import com.nurul.simpakat.view.CrudKaryawan;
import com.nurul.simpakat.view.EmployeeView;
import com.nurul.simpakat.view.warek.adapter.ListEmployeeAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataKaryawanFragment extends AbstractFragmentView<EmployeeModel> implements EmployeeView, ListEmployeeAdapter.ListEmployeeClicked {

    @BindView(R.id.fab_add_karyawan)
    FloatingActionButton fabAddKaryawan;

    @BindView(R.id.karyawan_list_loading)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @BindView(R.id.data_karyawan_layout)
    RelativeLayout layoutDataKaryawan;

    @BindView(R.id.no_data_karyawan)
    RelativeLayout layoutNoDataKaryawan;

    @BindView(R.id.recycler_karyawan)
    RecyclerView rvDataKaryawan;

    @BindView(R.id.layout_bottom_sheet)
    RelativeLayout bottomSheetLayout;

    @BindView(R.id.vw_nama_karyawan)
    TextView namaKaryawan;

    @BindView(R.id.vw_nip)
    TextView nipKaryawan;

    @BindView(R.id.vw_unitkerja)
    TextView namaUnitKerja;

    @BindView(R.id.vw_email)
    TextView emailKaryawan;

    @BindView(R.id.vw_jabatan)
    TextView jabatanKaryawan;

    private Boolean flagView = false;

    private EmployeePresenter employeePresenter;

    ListEmployeeAdapter adapter;
    private ListEmployee employeeList;

    public DataKaryawanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("EmployeeBroadcast"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_data_karyawan, container, false);

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        fabAddKaryawan.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CrudDataKaryawanActivity.class);
            intent.putExtra("action", "0");
            getActivity().startActivity(intent);
        });

        super.viewModel = new EmployeeModel();
        employeePresenter = new EmployeePresenter();
        employeePresenter.init(super.viewModel, this, new ApiProvider());

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        employeePresenter.requestDataEmployeeFromServer();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, Intent intent) {
//            String topic = intent.getAction();
            String from = intent.getStringExtra("from");
            if(from.equals("added")) {
                employeePresenter.requestDataEmployeeFromServer();
            } else if(from.equals("update")) {
                employeePresenter.requestDataEmployeeFromServer();
            }
        }
    };

    @Override
    public void updateModel(EmployeeModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }

    @Override
    public void onEmployeeAdded() {

    }

    @Override
    public void onEmployeeFailed(String message) {

    }

    @Override
    public void onEmployeeNull() {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataKaryawan.setVisibility(View.GONE);
        layoutNoDataKaryawan.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmployeeRetrieves(ArrayList<ListEmployee> listEmployees) {
        avLoadingIndicatorView.setVisibility(View.GONE);
        layoutDataKaryawan.setVisibility(View.VISIBLE);
        layoutNoDataKaryawan.setVisibility(View.GONE);

        setDataToRecyclerView(listEmployees);
    }

    public void setDataToRecyclerView(ArrayList<ListEmployee> listEmployeeArrayList) {
        adapter = new ListEmployeeAdapter(listEmployeeArrayList, this, getContext());
        rvDataKaryawan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDataKaryawan.setItemAnimator(new DefaultItemAnimator());
        rvDataKaryawan.setAdapter(adapter);
    }

    @Override
    public void ListEmployeeClicked(ListEmployee list) {
        employeeList = list;
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

            namaKaryawan.setText(list.getNamaKaryawan());
            nipKaryawan.setText(": "+list.getNip());
            if(TextUtils.isEmpty(list.getNamaUnit())) {
                namaUnitKerja.setText(": -");
            } else {
                namaUnitKerja.setText(": "+list.getNamaUnit());
            }
            emailKaryawan.setText(": "+list.getEmail());
            jabatanKaryawan.setText(": "+list.getJabatan());
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
        namaKaryawan.setText("");
        nipKaryawan.setText("");
        namaUnitKerja.setText("");
        emailKaryawan.setText("");
        jabatanKaryawan.setText("");
    }

    @OnClick(R.id.btn_edit)
    void onEditDataKaryawan() {
        flagView = false;
        TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 2400);
        slide.setDuration(1000);
//        slide.setFillAfter(true);
        bottomSheetLayout.startAnimation(slide);
        bottomSheetLayout.setVisibility(View.GONE);

        Intent intent = new Intent(getActivity(), CrudDataKaryawanActivity.class);
        intent.putExtra("action", "1");
        intent.putExtra("nip", employeeList.getNip());
        intent.putExtra("nama", employeeList.getNamaKaryawan());
        intent.putExtra("email", employeeList.getEmail());
        intent.putExtra("jabatan", employeeList.getJabatan());
        intent.putExtra("unitKerja", employeeList.getKodeUnitKerja());
        getActivity().startActivity(intent);
    }

//    @OnClick(R.id.btn_delete)
//    void onDeleteDataKaryawan() {
//        new AlertDialog.Builder(getActivity())
//                .setTitle("Konfirmasi")
//                .setMessage("Apakah anda yakin ingin menghapus?")
//                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> onClickTextLogout())
//                .setNegativeButton(android.R.string.no, (dialog, whichButton) -> dialog.dismiss())
//                .show();
//    }

    private void onClickTextLogout() {

    }

    @Override
    public void onResume() {
        employeePresenter.requestDataEmployeeFromServer();
        super.onResume();
    }
}
