package com.nurul.simpakat.view.login;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.view.home.HomeActivity;
import com.nurul.simpakat.view.login.signup.SignupUserActivity;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends AbstractFragmentView<LoginModel> implements LoginView {

    @BindView(R.id.login_nip)
    protected TextInputEditText nip;

    @BindView(R.id.login_password)
    protected ShowHidePasswordEditText password;

    private LoginPresenter loginPresenter;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        super.viewModel = new LoginModel();
        loginPresenter = new LoginPresenter();
        loginPresenter.init(super.viewModel, this, new ApiProvider());

        setOnFocusEditText();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String id = getAppPreference().getString(Constanta.PREF_ID, "");
        String email = getAppPreference().getString(Constanta.PREF_EMAIL, "'");

        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(email)) {
            showMasterPage();
        }
    }


    @OnClick(R.id.btn_login)
    void onLoginClick() {
        Boolean doNext = true;

        if(nip.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) nip.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.email_address_required));
            nip.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    nip.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        }else{
            /*if (!isValidEmailID(nip.getText().toString())){
                TextInputLayout til = (TextInputLayout) nip.getParent().getParent();
                til.setErrorEnabled(true);
                til.setError(getString(R.string.incorrect_email_address));
                nip.setBackgroundColor(Color.TRANSPARENT);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        nip.setBackgroundResource(R.drawable.background_edittext);
                    }
                }, 50);
                doNext = false;
            }else{
                TextInputLayout til = (TextInputLayout) nip.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
            }*/
            TextInputLayout til = (TextInputLayout) nip.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(password.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) password.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.password_required));
            password.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    password.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        }else{
            TextInputLayout til = (TextInputLayout) password.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }


        if(doNext) {
            super.viewModel.setNip(nip.getText().toString());
            super.viewModel.setPassword(password.getText().toString());

            loginPresenter.signInWithEmail();
        }
    }

    @OnClick(R.id.signup_user)
    void onSingupUser() {
        startActivity(new Intent(getActivity(), SignupUserActivity.class));
    }

    @OnTextChanged(R.id.login_nip)
    void onEditEmailChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnTextChanged(R.id.login_password)
    void onEditPasswordChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.login_nip)
    void onEditEmailTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnFocusChange(R.id.login_password)
    void onEditPasswordTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    public void settEditViewBackgroundTransparent(){
        nip.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        password.setBackground(getResources().getDrawable(R.drawable.background_edittext));
    }

    private void setOnFocusEditText(){
        nip.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) nip.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });

        password.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) password.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });

        nip.setOnClickListener(view -> {
            new Handler().postDelayed(() -> {
                TextInputLayout til = (TextInputLayout) nip.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
            }, 0);
        });

        password.setOnClickListener(view -> {
            new Handler().postDelayed(() -> {
                TextInputLayout til = (TextInputLayout) password.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
            }, 0);
        });
    }

    private boolean isValidEmailID(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onLoginSuccess() {
        String id = String.valueOf(viewModel.getNip());
        String email = viewModel.getEmail();
        String nama = viewModel.getName();


        Log.e("user_id",id);

        getAppPreference().putString(Constanta.PREF_ID, id);
        getAppPreference().putString(Constanta.PREF_EMAIL, email);
        getAppPreference().putString(Constanta.PREF_NAME, nama);

        showMasterPage();
    }

    @Override
    public void updateModel(LoginModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }

    private void showMasterPage() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
