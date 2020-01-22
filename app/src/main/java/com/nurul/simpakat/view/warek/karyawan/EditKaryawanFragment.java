package com.nurul.simpakat.view.warek.karyawan;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
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
import com.nurul.simpakat.model.simpakat.SignupModel;
import com.nurul.simpakat.presenter.SignupPresenter;
import com.nurul.simpakat.view.SignupView;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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
public class EditKaryawanFragment extends AbstractFragmentView<SignupModel> implements SignupView {

    @BindView(R.id.edit_name)
    TextInputEditText editName;

    @BindView(R.id.edit_email)
    TextInputEditText editEmail;

//    @BindView(R.id.edit_password)
//    ShowHidePasswordEditText editPassword;
//
//    @BindView(R.id.edit_confirm_password)
//    ShowHidePasswordEditText editConfirmPassword;

    @BindView(R.id.spinner_jabatan)
    MaterialSpinner spinnerJabatan;

    @BindView(R.id.spinner_unit_kerja)
    MaterialSpinner spinnerUnitKerja;

    @BindView(R.id.edit_nip)
    TextInputEditText editNIP;

    @BindView(R.id.cb_reset)
    CheckBox cbResetPass;

    private SignupPresenter signupPresenter;
    private String[] dataUnitKerja;

    public EditKaryawanFragment() {
        // Required empty public constructor
    }

    public static EditKaryawanFragment newInstance() {
        EditKaryawanFragment fragment = new EditKaryawanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_karyawan, container, false);

        super.viewModel = new SignupModel();
        signupPresenter = new SignupPresenter();
        signupPresenter.init(super.viewModel, this,
                new ApiProvider());

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        setOnFocusEditText();

        setMaterialData();

        retrieveDataUnitKerja();

        setDataEdit();

        return root;
    }

    private void setDataEdit() {
        Log.e("action", "data " + getActivity().getIntent().getStringExtra("action"));
        editNIP.setText(getActivity().getIntent().getStringExtra("nip"));
        editName.setText(getActivity().getIntent().getStringExtra("nama"));
        editEmail.setText(getActivity().getIntent().getStringExtra("email"));
        spinnerJabatan.setText(getActivity().getIntent().getStringExtra("jabatan"));
        spinnerUnitKerja.setText(getActivity().getIntent().getStringExtra("unitKerja"));
    }

    private void setOnFocusEditText(){
        editNIP.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) editNIP.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });

        editEmail.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) editEmail.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });

        editName.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) editName.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });

        editEmail.setOnClickListener(view -> new Handler().postDelayed(() -> {
            TextInputLayout til = (TextInputLayout) editEmail.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }, 0));


        editName.setOnClickListener(view -> new Handler().postDelayed(() -> {
            TextInputLayout til = (TextInputLayout) editName.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }, 0));


    }

    @OnTextChanged(R.id.edit_nip)
    void onEditNIPChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnTextChanged(R.id.edit_email)
    void onEditEmailChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnTextChanged(R.id.edit_name)
    void onEditNameChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.edit_nip)
    void onEditNIPTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnFocusChange(R.id.edit_email)
    void onEditEmailTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnFocusChange(R.id.edit_name)
    void onEditNameTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    public void settEditViewBackgroundTransparent(){
        editNIP.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        editEmail.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        editName.setBackground(getResources().getDrawable(R.drawable.background_edittext));
    }

    @OnClick(R.id.button_register)
    void onClickButtonRegister() {

        Boolean doNext = true;

        if(editNIP.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) editNIP.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.nip_required));
            editNIP.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    editNIP.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else {
            TextInputLayout til = (TextInputLayout) editNIP.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }
        if(editEmail.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) editEmail.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.email_address_required));
            editEmail.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    editEmail.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        }else{
            if (!isValidEmailID(editEmail.getText().toString())){
                TextInputLayout til = (TextInputLayout) editEmail.getParent().getParent();
                til.setErrorEnabled(true);
                til.setError(getString(R.string.incorrect_email_address));
                editEmail.setBackgroundColor(Color.TRANSPARENT);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        editEmail.setBackgroundResource(R.drawable.background_edittext);
                    }
                }, 50);
                doNext = false;
            }else{
                TextInputLayout til = (TextInputLayout) editEmail.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
            }
        }

        if(editName.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) editName.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.name_required));
            editName.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    editName.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        }else{

            TextInputLayout til = (TextInputLayout) editName.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(doNext) {
            super.viewModel.setNIP(editNIP.getText().toString());
            super.viewModel.setName(editName.getText().toString());
            super.viewModel.setEmail(editEmail.getText().toString());
            super.viewModel.setJabatan(spinnerJabatan.getText().toString());
//            super.viewModel.setdToken(dToken);
            if(spinnerJabatan.getText().toString().equalsIgnoreCase("Unit Kerja")) {
                String[] idUnitKerja = spinnerUnitKerja.getText().toString().replace(" ","").split("-");
                super.viewModel.setKodeUnitKerja(idUnitKerja[0]);
            } else {
                super.viewModel.setKodeUnitKerja("0");
            }
            super.viewModel.setResetPass(cbResetPass.isChecked());

            signupPresenter.editKaryawan();
        }
    }

    private void setMaterialData() {
        spinnerJabatan.setItems("Unit Kerja", "Dekan", "Keuangan", "Wakil Rektor 1", "Wakil Rektor 2", "Rektor");

        spinnerJabatan.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(item.equals("Unit Kerja")) {
                    spinnerUnitKerja.setEnabled(true);
                } else {
                    spinnerUnitKerja.setEnabled(false);
                }
            }
        });

//        spinnerJabatan.setOnItemSelectedListener((view, position, id, item) -> {
//            if(position == 1 || position == 2) {
//                spinnerUnitKerja.setEnabled(true);
//            } else {
//                spinnerUnitKerja.setEnabled(false);
//            }
//        });
    }

    private void retrieveDataUnitKerja() {
        String url = APPLICATION_URL+APPLICATION_PATH+"simpakat_get_list_data_unit_kerja.php";
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
                                spinnerUnitKerja.setItems(dataUnitKerja);
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
    public void onEmailSuccessReg() {
        Intent broadcastIntent = new Intent("EmployeeBroadcast");
        broadcastIntent.putExtra("from", "update");
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
    public void updateModel(SignupModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }

    private boolean isValidEmailID(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
