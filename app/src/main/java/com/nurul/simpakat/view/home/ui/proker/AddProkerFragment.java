package com.nurul.simpakat.view.home.ui.proker;


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
import com.nurul.simpakat.model.simpakat.ListProker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

import static com.nurul.simpakat.common.Constanta.APPLICATION_PATH;
import static com.nurul.simpakat.common.Constanta.APPLICATION_URL;
import static com.nurul.simpakat.common.Constanta.PREF_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProkerFragment extends AbstractFragmentView<ProkerModel> implements ProkerView {

    @BindView(R.id.spinner_unit_kerja)
    protected MaterialSpinner materialSpinner;

    @BindView(R.id.spinner_jenis_pembiayaan)
    protected MaterialSpinner spinnerPembiayaan;

    @BindView(R.id.spinner_bulan_kegiatan)
    protected MaterialSpinner bulanKegiatanSpinner;

    @BindView(R.id.spinner_coa)
    protected MaterialSpinner coaSpinner;

    @BindView(R.id.layout_success)
    protected RelativeLayout layoutSuccessAdd;

    @BindView(R.id.layout_button_add_proker)
    protected RelativeLayout layoutBtnAddProker;

    @BindView(R.id.layout_add_data_proker)
    protected NestedScrollView layoutAddDataProker;

    @BindView(R.id.add_prgram_kerja)
    protected TextInputEditText programKerja;

    @BindView(R.id.add_keterangan)
    protected TextInputEditText keterangan;

    @BindView(R.id.add_biaya)
    protected TextInputEditText biaya;

    private String[] dataUnitKerja, dataCOA;

    public AddProkerFragment() {
        // Required empty public constructor
    }

    private ProkerPresenter prokerPresenter;

    public static AddProkerFragment newInstance() {
        AddProkerFragment fragment = new AddProkerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_add_proker, container, false);

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        super.viewModel = new ProkerModel();
        prokerPresenter = new ProkerPresenter();
        prokerPresenter.init(super.viewModel, this, new ApiProvider());

        init();

        setMaterialData();

        retrieveDataUnitKerja();
        retrieveDataCoa();

        setOnFocusEditText();

        return root;
    }

    private void init() {
        setOnClick();
    }

    private void setMaterialData() {
        spinnerPembiayaan.setItems("-- Pilih --","Operasional","Investasi");
        bulanKegiatanSpinner.setItems("-- Pilih --","1","2","3","4","5","6","7","8","9","10","11","12");
    }

    private void setOnClick() {
        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
            }
        });
    }

    private void showLayoutSuccess() {
        AddProgramKerjaActivity.instance().hideAppBar();
        layoutBtnAddProker.setVisibility(View.GONE);
        layoutAddDataProker.setVisibility(View.GONE);
        layoutSuccessAdd.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_add_proker)
    void addProkerClick() {
        Boolean doNext = true;

        if(programKerja.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) programKerja.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.proker_name_required));
            programKerja.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    programKerja.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) programKerja.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(keterangan.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) keterangan.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.password_required));
            keterangan.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    keterangan.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) keterangan.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

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

        if(materialSpinner.getSelectedIndex() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.unitkerja_required),
                    Toast.LENGTH_LONG).show();
            doNext = false;
        } else {
            if(coaSpinner.getSelectedIndex() == 0) {
                Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.coa_required),
                        Toast.LENGTH_LONG).show();
                doNext = false;
            } else {
                if(spinnerPembiayaan.getSelectedIndex() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.jenis_pembiayaan_required),
                            Toast.LENGTH_LONG).show();
                    doNext = false;
                } else {
                    if(bulanKegiatanSpinner.getSelectedIndex() == 0) {
                        Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.bulan_kegiatan_required),
                                Toast.LENGTH_LONG).show();
                        doNext = false;
                    }
                }
            }
        }

        if(doNext) {
            String[] idUnitKerja = materialSpinner.getText().toString().replace(" ","").split("-");
            super.viewModel.setUnitKerja(idUnitKerja[0]);
            super.viewModel.setProgramKerja(programKerja.getText().toString());
            super.viewModel.setKeterangan(keterangan.getText().toString());
            String[] noAccountCOA = coaSpinner.getText().toString().replace(" ","").split("-");
            super.viewModel.setAccountCOA(noAccountCOA[0]);
            super.viewModel.setJenisPembiayaan(spinnerPembiayaan.getText().toString());
            super.viewModel.setBiaya(biaya.getText().toString());
            super.viewModel.setBulanKegiatan(bulanKegiatanSpinner.getText().toString());

            prokerPresenter.insertProker(getAppPreference().getString(PREF_ID, ""));
        }
    }

    private void retrieveDataUnitKerja() {
        String url = APPLICATION_URL+APPLICATION_PATH+"simpakat_get_data_unitkerja.php";
        AsyncHttpClient client = new AsyncHttpClient(true,80,443);
        client.setTimeout(60000);
        RequestParams params = new RequestParams();

//        params.put("id_user", "");

        params.setUseJsonStreamer(true);

        client.post(url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String response = null;
                try {
                    response = new String(responseBody, "UTF-8");
                    Log.d("respond",response);
                    JSONObject json, jsonDataUnit;
                    try {
                        json = new JSONObject(response);
                        Log.d("respond","isi : " + json.getString("resultCode"));
                        if(TextUtils.equalIgnoreCase(json.getString("resultCode"), Constanta.OK)) {
                            JSONArray jsonArray = new JSONArray(json.getString("data"));
                            if(jsonArray.length() > 0) {
                                dataUnitKerja = new String[jsonArray.length()+1];
                                dataUnitKerja[0] = "-- Pilih --";
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    jsonDataUnit = jsonArray.getJSONObject(i);
                                    dataUnitKerja[i+1] = jsonDataUnit.getString("kode_unit_kerja")+" - "+jsonDataUnit.getString("nama_unit_kerja");
                                }

                                Log.e("ARRAY","isi array : " + dataUnitKerja);
                                materialSpinner.setItems(dataUnitKerja);
                            }
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

    private void retrieveDataCoa() {
        String url = APPLICATION_URL+APPLICATION_PATH+"simpakat_get_data_coa.php";
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
                    JSONObject json, jsonDataCOA;
                    try {
                        json = new JSONObject(response);
                        Log.d("respond","isi : " + json.getString("resultCode"));
                        if(TextUtils.equalIgnoreCase(json.getString("resultCode"), Constanta.OK)) {
                            JSONArray jsonArray = new JSONArray(json.getString("data"));
                            if(jsonArray.length() > 0) {
                                dataCOA = new String[jsonArray.length()+1];
                                dataCOA[0] = "-- Pilih --";
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    jsonDataCOA = jsonArray.getJSONObject(i);
                                    dataCOA[i+1] = jsonDataCOA.getString("no_account")+" - "+jsonDataCOA.getString("nama_account");
                                }

                                Log.e("ARRAY","isi array : " + dataCOA);
                                coaSpinner.setItems(dataCOA);
                            }
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

    @Override
    public void updateModel(ProkerModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }

    @OnTextChanged(R.id.add_prgram_kerja)
    void onProgramKerjaChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.add_prgram_kerja)
    void onProgramKerjaTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnTextChanged(R.id.add_keterangan)
    void onKeteranganChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.add_keterangan)
    void onKeteranganTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnTextChanged(R.id.add_biaya)
    void onBiayaChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.add_biaya)
    void onBiayaTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    private void setOnFocusEditText(){
        programKerja.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) programKerja.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });

        keterangan.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) keterangan.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });

        biaya.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) biaya.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });
    }

    public void settEditViewBackgroundTransparent(){
        programKerja.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        keterangan.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        biaya.setBackground(getResources().getDrawable(R.drawable.background_edittext));
    }

    @Override
    public void onProkerAdded() {
        showLayoutSuccess();
        Intent broadcastIntent = new Intent("ProkerBroadcast");
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
    public void onProkerNull() {

    }

    @Override
    public void onProkerRetrieve(ArrayList<ListProker> listProkers) {

    }
}
