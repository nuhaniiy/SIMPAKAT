package com.nurul.simpakat.home.ui.proker;


import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.JsonArray;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.util.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nurul.simpakat.common.Constanta.APPLICATION_PATH;
import static com.nurul.simpakat.common.Constanta.APPLICATION_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProkerFragment extends AbstractFragmentView<ProkerModel> {

    @BindView(R.id.spinner_unit_kerja)
    protected MaterialSpinner materialSpinner;

    @BindView(R.id.spinner_jenis_pembiayaan)
    protected MaterialSpinner spinnerPembiayaan;

    @BindView(R.id.spinner_bulan_kegiatan)
    protected MaterialSpinner bulanKegiatanSpinner;

    @BindView(R.id.layout_success)
    protected RelativeLayout layoutSuccessAdd;

    @BindView(R.id.layout_button_add_proker)
    protected RelativeLayout layoutBtnAddProker;

//    @BindView(R.id.app_bar)
//    protected AppBarLayout appBarLayout;

    @BindView(R.id.layout_add_data_proker)
    protected NestedScrollView layoutAddDataProker;

    private UnitKerjaModel unitKerjaModel;

    private String[] dataUnitKerja;

    public AddProkerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_add_proker, container, false);

        ButterKnife.bind(this, root);

        init();

        retrieveDataUnitKerja();

        setMaterialData();

        return root;
    }

    private void init() {
        setOnClick();
    }

    private void setMaterialData() {
        spinnerPembiayaan.setItems("Operasional","Investasi");
        bulanKegiatanSpinner.setItems("1","");
    }

    private void setOnClick() {
        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
            }
        });
    }

    private void showLayoutSuccess() {
//        appBarLayout.setVisibility(View.GONE);
        AddProgramKerjaActivity.instance().hideAppBar();
        layoutBtnAddProker.setVisibility(View.GONE);
        layoutAddDataProker.setVisibility(View.GONE);
        layoutSuccessAdd.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_add_proker)
    void addProkerClick() {
        showLayoutSuccess();
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

    @Override
    public void updateModel(ProkerModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }
}
