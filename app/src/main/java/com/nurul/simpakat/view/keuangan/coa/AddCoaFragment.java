package com.nurul.simpakat.view.keuangan.coa;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.model.simpakat.CoaModel;
import com.nurul.simpakat.model.simpakat.ListDataCoa;
import com.nurul.simpakat.presenter.CoaPresenter;
import com.nurul.simpakat.view.CoaView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCoaFragment extends AbstractFragmentView<CoaModel> implements CoaView {

    @BindView(R.id.layout_success)
    protected RelativeLayout layoutSuccessAdd;

    @BindView(R.id.layout_button_add_coa)
    protected RelativeLayout layoutBtnAddCoa;

    @BindView(R.id.layout_add_data_coa)
    protected NestedScrollView layoutAddDataCoa;

    @BindView(R.id.add_kode_parent)
    protected TextInputEditText kodeParent;

    @BindView(R.id.add_kode_coa)
    protected TextInputEditText kodeAccount;

    @BindView(R.id.add_nama_coa)
    protected TextInputEditText namaAccount;


    public AddCoaFragment() {
        // Required empty public constructor
    }

    private CoaPresenter coaPresenter;

    public static AddCoaFragment newInstance() {
        AddCoaFragment fragment = new AddCoaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_coa, container, false);

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        super.viewModel = new CoaModel();
        coaPresenter = new CoaPresenter();
        coaPresenter.init(super.viewModel, this, new ApiProvider());

        setOnFocusEditText();

        return root;
    }

    private void showLayoutSuccess() {
        AddCoaActivity.instance().hideAppBar();
        layoutBtnAddCoa.setVisibility(View.GONE);
        layoutAddDataCoa.setVisibility(View.GONE);
        layoutSuccessAdd.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_add_coa)
    void addCoaClicked() {
        Boolean doNext = true;

        if(kodeParent.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) kodeParent.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.kode_parent_required));
            kodeParent.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    kodeParent.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) kodeParent.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(kodeAccount.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) kodeAccount.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.kode_account_required));
            kodeAccount.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    kodeAccount.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) kodeAccount.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(namaAccount.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) namaAccount.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.name_account_required));
            namaAccount.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    namaAccount.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else{
            TextInputLayout til = (TextInputLayout) namaAccount.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(doNext) {
            super.viewModel.setKodeParent(kodeParent.getText().toString());
            super.viewModel.setKodeCoa(kodeAccount.getText().toString());
            super.viewModel.setNamaCoa(namaAccount.getText().toString());

            coaPresenter.insertCoa();
        }
    }

    @OnTextChanged(R.id.add_nama_coa)
    void onNamaAccountChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.add_nama_coa)
    void onNamaAccountTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnTextChanged(R.id.add_kode_coa)
    void onKodeAccountChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.add_kode_coa)
    void onKodeAccountTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnTextChanged(R.id.add_kode_parent)
    void onKodeParentChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.add_kode_parent)
    void onKodeParentTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    private void setOnFocusEditText(){
        kodeParent.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) kodeParent.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });

        kodeAccount.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) kodeAccount.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });

        namaAccount.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) namaAccount.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });
    }

    public void settEditViewBackgroundTransparent(){
        kodeParent.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        kodeAccount.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        namaAccount.setBackground(getResources().getDrawable(R.drawable.background_edittext));
    }

    @Override
    public void onCoaAdded() {
        showLayoutSuccess();
        Intent broadcastIntent = new Intent("CoaBroadcast");
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
    public void onCoaFailed(String message) {

    }

    @Override
    public void onCoaNull() {

    }

    @Override
    public void onCoaRetrieves(ArrayList<ListDataCoa> listDataCoas) {

    }

    @Override
    public void updateModel(CoaModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }
}
