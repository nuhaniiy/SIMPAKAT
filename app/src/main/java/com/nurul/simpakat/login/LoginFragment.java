package com.nurul.simpakat.login;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.login.signup.SignupUserActivity;
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

    @BindView(R.id.login_email)
    protected TextInputEditText email;

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

        super.viewModel = new LoginModel();
        loginPresenter = new LoginPresenter();
        loginPresenter.init(super.viewModel, this, new ApiProvider());

        setOnFocusEditText();

        return root;
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        /*Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();*/
        Boolean doNext = true;

        if(email.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) email.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.email_address_required));
            email.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    email.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        }else{
            if (!isValidEmailID(email.getText().toString())){
                TextInputLayout til = (TextInputLayout) email.getParent().getParent();
                til.setErrorEnabled(true);
                til.setError(getString(R.string.incorrect_email_address));
                email.setBackgroundColor(Color.TRANSPARENT);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        email.setBackgroundResource(R.drawable.background_edittext);
                    }
                }, 50);
                doNext = false;
            }else{
                TextInputLayout til = (TextInputLayout) email.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
            }
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
//            Log.e("DATA", "isi email " + email.getText().toString() + " password " + password.getText().toString());
            super.viewModel.setEmail(email.getText().toString());
            super.viewModel.setPassword(password.getText().toString());

            loginPresenter.signInWithEmail();
        }
    }

    @OnClick(R.id.signup_user)
    void onSingupUser() {
        startActivity(new Intent(getActivity(), SignupUserActivity.class));
    }

    @OnTextChanged(R.id.login_email)
    void onEditEmailChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnTextChanged(R.id.login_password)
    void onEditPasswordChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.login_email)
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
        email.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        password.setBackground(getResources().getDrawable(R.drawable.background_edittext));
    }

    private void setOnFocusEditText(){
        email.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) email.getParent().getParent();
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

        email.setOnClickListener(view -> {
            new Handler().postDelayed(() -> {
                TextInputLayout til = (TextInputLayout) email.getParent().getParent();
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

    }

    @Override
    public void updateModel(LoginModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }
}
