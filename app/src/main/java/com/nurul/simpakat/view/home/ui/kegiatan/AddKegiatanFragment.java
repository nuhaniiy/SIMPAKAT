package com.nurul.simpakat.view.home.ui.kegiatan;


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
import android.widget.Button;
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
import com.nurul.simpakat.model.simpakat.KegiatanModel;
import com.nurul.simpakat.model.simpakat.ListKegiatan;
import com.nurul.simpakat.presenter.KegiatanPresenter;
import com.nurul.simpakat.view.KegiatanView;

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
public class AddKegiatanFragment extends AbstractFragmentView<KegiatanModel> implements KegiatanView {

    @BindView(R.id.spinner_unit_program_kerja)
    protected MaterialSpinner programKerja;

    @BindView(R.id.text_nama_kegiatan)
    protected TextInputEditText namaKegiatan;

    @BindView(R.id.text_nominal_anggaran)
    protected TextInputEditText nominalAnggaran;

    @BindView(R.id.layout_tanggal_kegiatan)
    protected EditText tanggalKegiatan;

    @BindView(R.id.layout_button_kegiatan)
    protected RelativeLayout layoutButtonKegiatan;

    @BindView(R.id.layout_add_data_kegiatan)
    protected NestedScrollView layoutAddDataKegiatan;

    @BindView(R.id.layout_success)
    protected RelativeLayout layoutSuccessAdd;

    @BindView(R.id.btn_kegiatan)
    protected Button btnKegiatan;


    private String[] listProker;
    private String[] kodeProker;
    private JSONArray jsonArray;

    private KegiatanPresenter kegiatanPresenter;
    final Calendar myCalendar = Calendar.getInstance();

    public AddKegiatanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_kegiatan, container, false);

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        super.viewModel = new KegiatanModel();
        kegiatanPresenter = new KegiatanPresenter();
        kegiatanPresenter.init(super.viewModel, this, new ApiProvider());

        retrieveDataUnitKerja();

        return root;
    }

    @Override
    public void onKegiatanAdded() {
        showLayoutSuccess();
        Intent broadcastIntent = new Intent("KegiatanBroadcast");
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
    public void onKegiatanFailed(String message) {
        Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onKegiatanNull() {

    }

    @Override
    public void onKegiatanRetrieves(ArrayList<ListKegiatan> listKegiatans) {

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tanggalKegiatan.setText(sdf.format(myCalendar.getTime()));
    }

    @OnClick(R.id.layout_tanggal_kegiatan)
    void onChangeTanggalKegiatan() {
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
        String url = APPLICATION_URL+APPLICATION_PATH+"simpakat_get_list_proker_kegiatan.php";
        AsyncHttpClient client = new AsyncHttpClient(true,80,443);
        client.setTimeout(60000);
        RequestParams params = new RequestParams();

        params.put("NIP", getAppPreference().getString(Constanta.PREF_ID, ""));
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
                                kodeProker = new String[jsonArray.length()+1];
                                listProker[0] = "-- Pilih --";
                                kodeProker[0] = "0";
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    jsonData = jsonArray.getJSONObject(i);
                                    kodeProker[i+1] = jsonData.getString("id_proker");
                                    listProker[i+1] = jsonData.getString("nama_proker");
                                    programKerja.setEnabled(true);
                                    dataEnable();
                                }
                                programKerja.setItems(listProker);
                            } else {
                                programKerja.setText("-- Pilih --");
                                programKerja.setEnabled(false);
                                dataDisable();
                                Toast.makeText(getActivity().getApplicationContext(), "Please input your program kerja first", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException ex) {
                        programKerja.setText("-- Pilih --");
                        programKerja.setEnabled(false);
                        dataDisable();
                        Toast.makeText(getActivity().getApplicationContext(), "Please input your program kerja first", Toast.LENGTH_LONG).show();
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

    public void settEditViewBackgroundTransparent(){
        namaKegiatan.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        nominalAnggaran.setBackground(getResources().getDrawable(R.drawable.background_edittext));
    }

    @OnTextChanged(R.id.text_nama_kegiatan)
    void onNamaKegiatanChange(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.text_nama_kegiatan)
    void onNamaKegiatanTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnTextChanged(R.id.text_nominal_anggaran)
    void onNominalAnggaranChange(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.text_nominal_anggaran)
    void onNominalAnggaranTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnClick(R.id.btn_kegiatan)
    void onKegiatanClicked() {
        Boolean doNext = true;

        if(namaKegiatan.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) namaKegiatan.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.nama_kegiatan_required));
            namaKegiatan.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    namaKegiatan.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) namaKegiatan.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(nominalAnggaran.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) nominalAnggaran.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.nominal_anggaran_kegiatan_required));
            nominalAnggaran.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    nominalAnggaran.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) nominalAnggaran.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(programKerja.getSelectedIndex() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.proker_name_required),
                    Toast.LENGTH_LONG).show();
            doNext = false;
        } else {
            if(tanggalKegiatan.getText().toString().equals("")) {
                Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.tanggal_kegiatan_required),
                        Toast.LENGTH_LONG).show();
                doNext = false;
            }
        }

        if(doNext) {
            //            String[] idProgramKerja = programKerja.getText().toString().replace(" ","").split("-");
            int idProgramKerja = programKerja.getSelectedIndex();
//            super.viewModel.setIdProker(idProgramKerja[0]);
            super.viewModel.setIdProker(kodeProker[idProgramKerja]);
            super.viewModel.setNamaKegiatan(namaKegiatan.getText().toString());
            super.viewModel.setNominalAnggaran(nominalAnggaran.getText().toString());
            super.viewModel.setTanggalKegiatan(tanggalKegiatan.getText().toString());
            super.viewModel.setStatusKegiatan("Belum Dilaporkan");
            super.viewModel.setNip(getAppPreference().getString(Constanta.PREF_ID, ""));

            kegiatanPresenter.insertKegiatan();
        }
    }

    private void showLayoutSuccess() {
        AddKegiatanActivity.instance().hideAppBar();
        layoutButtonKegiatan.setVisibility(View.GONE);
        layoutAddDataKegiatan.setVisibility(View.GONE);
        layoutSuccessAdd.setVisibility(View.VISIBLE);
    }

    private void dataDisable() {
        namaKegiatan.setEnabled(false);
        nominalAnggaran.setEnabled(false);
        tanggalKegiatan.setEnabled(false);
        btnKegiatan.setEnabled(false);
    }

    private void dataEnable() {
        namaKegiatan.setEnabled(true);
        nominalAnggaran.setEnabled(true);
        tanggalKegiatan.setEnabled(true);
        btnKegiatan.setEnabled(true);
    }

    @Override
    public void updateModel(KegiatanModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }
}
