package com.nurul.simpakat.login.signup;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupUserFragment extends AbstractFragmentView<SignupModel> implements SignupView {

    @BindView(R.id.edit_name)
    TextInputEditText editName;

    @BindView(R.id.edit_email)
    TextInputEditText editEmail;

    @BindView(R.id.edit_password)
    ShowHidePasswordEditText editPassword;

    @BindView(R.id.edit_confirm_password)
    ShowHidePasswordEditText editConfirmPassword;

    @BindView(R.id.spinner_jabatan)
    MaterialSpinner spinnerJabatan;

    private SignupPresenter signupPresenter;

    public static SignupUserFragment newInstance() {
        SignupUserFragment fragment = new SignupUserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SignupUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_signup_user, container, false);

        super.viewModel = new SignupModel();
        signupPresenter = new SignupPresenter();
        signupPresenter.init(super.viewModel, this,
                new ApiProvider());

        ButterKnife.bind(this, root);

        setOnFocusEditText();

        setMaterialData();

        return root;
    }

    private void setMaterialData() {
        spinnerJabatan.setItems("-- Pilih --", "Unit Kerja", "Dekan", "Keuangan", "Wakil Rektor 1", "Wakil Rektor 2", "Rektor");
    }

    private void setOnFocusEditText(){
        editEmail.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) editEmail.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });

        editPassword.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) editPassword.getParent().getParent();
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

        editConfirmPassword.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) editConfirmPassword.getParent().getParent();
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

        editPassword.setOnClickListener(view -> new Handler().postDelayed(() -> {
            TextInputLayout til = (TextInputLayout) editPassword.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }, 0));


        editName.setOnClickListener(view -> new Handler().postDelayed(() -> {
            TextInputLayout til = (TextInputLayout) editName.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }, 0));

        editConfirmPassword.setOnClickListener(view -> new Handler().postDelayed(() -> {
            TextInputLayout til = (TextInputLayout) editConfirmPassword.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }, 0));


    }

    @OnTextChanged(R.id.edit_email)
    void onEditEmailChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnTextChanged(R.id.edit_name)
    void onEditNameChanged(){
        settEditViewBackgroundTransparent();
    }


    @OnTextChanged(R.id.edit_confirm_password)
    void onEditConfirmPasswordChanged(){
        settEditViewBackgroundTransparent();
    }


    @OnTextChanged(R.id.edit_password)
    void onEditPasswordChanged(){
        settEditViewBackgroundTransparent();
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

    @OnFocusChange(R.id.edit_password)
    void onEditPasswordTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnFocusChange(R.id.edit_confirm_password)
    void onEditConfirmPasswordTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    public void settEditViewBackgroundTransparent(){
        editEmail.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        editPassword.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        editName.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        editConfirmPassword.setBackground(getResources().getDrawable(R.drawable.background_edittext));
    }

    @OnClick(R.id.button_register)
    void onClickButtonRegister() {

        Boolean doNext = true;


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

        if(editPassword.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) editPassword.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.password_required));
            editPassword.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    editPassword.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        }else{

            TextInputLayout til = (TextInputLayout) editPassword.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);

            if(!editPassword.getText().toString().equals(editConfirmPassword.getText().toString())){
                TextInputLayout til2 = (TextInputLayout) editConfirmPassword.getParent().getParent();
                til2.setErrorEnabled(true);
                til2.setError(getString(R.string.password_and_confirm_password_not_same));
                editConfirmPassword.setBackgroundColor(Color.TRANSPARENT);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        editConfirmPassword.setBackgroundResource(R.drawable.background_edittext);
                    }
                }, 50);
                doNext = false;
            }else{
                TextInputLayout til2 = (TextInputLayout) editConfirmPassword.getParent().getParent();
                til2.setErrorEnabled(false);
                til2.setError(null);
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

        if(spinnerJabatan.getSelectedIndex() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.jabatan_required),
                    Toast.LENGTH_LONG).show();
            doNext = false;
        }

        if(doNext) {
            super.viewModel.setName(editName.getText().toString());
            super.viewModel.setEmail(editEmail.getText().toString());
            super.viewModel.setPassword(editPassword.getText().toString());
            super.viewModel.setConfirmPassword(editConfirmPassword.getText().toString());
            super.viewModel.setJabatan(spinnerJabatan.getText().toString());

            signupPresenter.signUpWithEmail();
        }
    }

    @OnClick(R.id.text_login)
    void onClickTextLogin() {
        getActivity().finish();
    }

    @Override
    public void onEmailSuccessReg() {
//        closeView();
        startActivity(new Intent(getActivity(), SuccessRegisterActivity.class));
        getActivity().finish();
    }

    @Override
    public void updateModel(SignupModel model) {
        // Do nothing
    }

    @Override
    public boolean goBack() {
        return false;
    }

    private boolean isValidEmailID(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
