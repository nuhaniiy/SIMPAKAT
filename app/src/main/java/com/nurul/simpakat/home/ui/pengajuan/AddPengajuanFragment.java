package com.nurul.simpakat.home.ui.pengajuan;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.PreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPengajuanFragment extends AbstractFragmentView<PengajuanModel> implements PengajuanView{

    @BindView(R.id.spinner_unit_program_kerja)
    protected MaterialSpinner programKerja;

    @BindView(R.id.pengajuan_biaya)
    protected TextInputEditText biaya;

    @BindView(R.id.pengajuan_biaya_telah_dipakai)
    protected TextInputEditText biayaTerpakai;

    @BindView(R.id.pengajuan_sisa_biaya)
    protected TextInputEditText sisaBiaya;

    @BindView(R.id.pengajuan_biaya_diajukan)
    protected TextInputEditText biayaDiajukan;

    @BindView(R.id.pengajuan_dibayar_kepada)
    protected TextInputEditText dibayarKepada;

    @BindView(R.id.pengajuan_jabatan)
    protected TextInputEditText jabatan;

    @BindView(R.id.pengajuan_norek)
    protected TextInputEditText norek;

    @BindView(R.id.layout_tanggal_pengajuan)
    protected EditText tanggalPengajuan;

    public AddPengajuanFragment() {
        // Required empty public constructor
    }

    private PengajuanPresenter pengajuanPresenter;
    final Calendar myCalendar = Calendar.getInstance();

    public static AddPengajuanFragment newInstance() {
        AddPengajuanFragment fragment = new AddPengajuanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_pengajuan, container, false);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        super.viewModel = new PengajuanModel();
        pengajuanPresenter = new PengajuanPresenter();
        pengajuanPresenter.init(super.viewModel, this, new ApiProvider());

        return root;
    }

    @Override
    public void updateModel(PengajuanModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }

    @Override
    public void onPengajuanAdded() {

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tanggalPengajuan.setText(sdf.format(myCalendar.getTime()));
    }

    @OnClick(R.id.layout_tanggal_pengajuan)
    void onChangeTanggalPengajuan() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.btn_pengajuan_proker)
    void onPengajuanClicked() {

    }
}
