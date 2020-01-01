package com.nurul.simpakat.view.home.ui.pengajuan;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.simpakat.ListPengajuan;
import com.nurul.simpakat.presenter.PengajuanPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

import static com.nurul.simpakat.common.Constanta.APPLICATION_PATH;
import static com.nurul.simpakat.common.Constanta.APPLICATION_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPengajuanFragment extends AbstractFragmentView<PengajuanModel> implements PengajuanView {

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

    @BindView(R.id.layout_button_submission)
    protected RelativeLayout layoutButtonSubmission;

    @BindView(R.id.layout_add_data_pengajuan_proker)
    protected NestedScrollView layoutAddDataPengajuan;

    @BindView(R.id.layout_success)
    protected RelativeLayout layoutSuccessAdd;

    private String[] listProker;
    private JSONArray jsonArray;

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

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        super.viewModel = new PengajuanModel();
        pengajuanPresenter = new PengajuanPresenter();
        pengajuanPresenter.init(super.viewModel, this, new ApiProvider());

        retrieveDataUnitKerja();
        return root;
    }

    private void setOnClick() {
        programKerja.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                try {
                    if(position == 0) {
                        biaya.setText("");
                    } else {
                        JSONObject jsonObject = jsonArray.getJSONObject(position);
                        biaya.setText(jsonObject.getString("biaya"));
                    }
                } catch (JSONException ex) {

                }
            }
        });
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
        showLayoutSuccess();
        Intent broadcastIntent = new Intent("PengajuanProkerBroadcast");
        broadcastIntent.putExtra("from", "added");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(broadcastIntent);
        Thread myThread = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(3000);
                    getActivity().finish();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

    @Override
    public void onPengajuanFailed(String message) {
        Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPengajuanNull() {

    }

    @Override
    public void onPengajuanRetrieves(ArrayList<ListPengajuan> listPengajuans) {

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

    private void retrieveDataUnitKerja() {
        String url = APPLICATION_URL+APPLICATION_PATH+"simpakat_get_list_proker.php";
        AsyncHttpClient client = new AsyncHttpClient(true,80,443);
        client.setTimeout(60000);
        RequestParams params = new RequestParams();

        params.setUseJsonStreamer(true);

        client.post(url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String response = null;
                try {
                    response = new String(responseBody, "UTF-8");
                    Log.d("respond",response);
                    JSONObject json, jsonData;
                    try {
                        json = new JSONObject(response);
                        Log.d("respond","isi proker : " + json.getString("resultCode"));
                        if(TextUtils.equalIgnoreCase(json.getString("resultCode"), Constanta.OK)) {
                            jsonArray = new JSONArray(json.getString("data_proker"));
                            if(jsonArray.length() > 0) {
                                listProker = new String[jsonArray.length()+1];
                                listProker[0] = "-- Pilih --";
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    jsonData = jsonArray.getJSONObject(i);
                                    listProker[i+1] = jsonData.getString("id_proker")+" - "+jsonData.getString("nama_proker");
                                }
                                programKerja.setItems(listProker);
                            }
                            setOnClick();
                        }
                    } catch (JSONException ex) {

                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                String response;
                try {
                    response = new String(responseBody, "UTF-8");
                    Log.d("error",response);
                    //Toast.makeText(EditBusinessUnitWizardActivity.this,"Cannot Connect to server",Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnTextChanged(R.id.pengajuan_biaya)
    void onBiayaChange(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.pengajuan_biaya)
    void onBiayaTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnTextChanged(R.id.pengajuan_sisa_biaya)
    void onSisaBiayaChange(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.pengajuan_sisa_biaya)
    void onSisaBiayaTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnTextChanged(R.id.pengajuan_biaya_telah_dipakai)
    void onBiayaTelahDiapakiChange(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.pengajuan_biaya_telah_dipakai)
    void onBiayaTelahDipakaiTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnTextChanged(R.id.pengajuan_biaya_diajukan)
    void onBiayaDiajukanChange(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.pengajuan_biaya_diajukan)
    void onBiayaDiajukanTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnTextChanged(R.id.pengajuan_dibayar_kepada)
    void onDibayarkanKepadaChange(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.pengajuan_dibayar_kepada)
    void onDibayarkanKepadaTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnTextChanged(R.id.pengajuan_jabatan)
    void onJabatanChange(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.pengajuan_jabatan)
    void onJabatanTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnTextChanged(R.id.pengajuan_norek)
    void onNorekChange(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.pengajuan_norek)
    void onNorekTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    public void settEditViewBackgroundTransparent(){
        biaya.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        biayaTerpakai.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        sisaBiaya.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        biayaDiajukan.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        dibayarKepada.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        jabatan.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        norek.setBackground(getResources().getDrawable(R.drawable.background_edittext));
    }

    @OnClick(R.id.btn_pengajuan_proker)
    void onPengajuanClicked() {
        Boolean doNext = true;

        if(biaya.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) biaya.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.biaya_required));
            biaya.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    biaya.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) biaya.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(biayaTerpakai.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) biayaTerpakai.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.biaya_terpakai_required));
            biayaTerpakai.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    biayaTerpakai.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) biayaTerpakai.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(sisaBiaya.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) sisaBiaya.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.biaya_sisa_required));
            sisaBiaya.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    sisaBiaya.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) sisaBiaya.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(biayaDiajukan.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) biayaDiajukan.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.biaya_diajukan_required));
            biayaDiajukan.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    biayaDiajukan.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) biayaDiajukan.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(dibayarKepada.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) dibayarKepada.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.dibayarkan_kepada_required));
            dibayarKepada.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    dibayarKepada.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) dibayarKepada.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(jabatan.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) jabatan.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.jabatan_required));
            jabatan.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    jabatan.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) jabatan.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(norek.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) norek.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.nomer_rekening_required));
            norek.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    norek.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) norek.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(programKerja.getSelectedIndex() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.proker_name_required),
                    Toast.LENGTH_LONG).show();
            doNext = false;
        } else {
            if(tanggalPengajuan.getText().toString().equals("")) {
                Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.tanggal_pengajuan_required),
                        Toast.LENGTH_LONG).show();
                doNext = false;
            }
        }

        if(doNext) {
            String[] idProgramKerja = programKerja.getText().toString().replace(" ","").split("-");
            super.viewModel.setIdProker(idProgramKerja[0]);
            super.viewModel.setBiaya(biaya.getText().toString());
            super.viewModel.setBiayaTerpakai(biayaTerpakai.getText().toString());
            super.viewModel.setSisaBiaya(sisaBiaya.getText().toString());
            super.viewModel.setBiayaDiajukan(biayaDiajukan.getText().toString());
            super.viewModel.setDibayarKepada(dibayarKepada.getText().toString());
            super.viewModel.setJabatan(jabatan.getText().toString());
            super.viewModel.setNoRekening(norek.getText().toString());
            super.viewModel.setTanggalPengajuan(tanggalPengajuan.getText().toString());
            super.viewModel.setPersetujuanDekan("");
            super.viewModel.setPersetujuanWarek1("");
            super.viewModel.setPersetujuanWarek2("");
            super.viewModel.setPersetujuanRektor("");
            super.viewModel.setStatusPengajuan("Proses Diajukan");

            pengajuanPresenter.insertPengajuan();
        }
    }

    private void showLayoutSuccess() {
        AddPengajuanProkerActivity.instance().hideAppBar();
        layoutButtonSubmission.setVisibility(View.GONE);
        layoutAddDataPengajuan.setVisibility(View.GONE);
        layoutSuccessAdd.setVisibility(View.VISIBLE);
    }
}
