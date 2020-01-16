package com.nurul.simpakat.view.login.changePassword;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.model.simpakat.ForgetPasswordModel;
import com.nurul.simpakat.presenter.ForgetPasswordPresenter;
import com.nurul.simpakat.view.ForgetPasswordView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordFragment extends AbstractFragmentView<ForgetPasswordModel> implements ForgetPasswordView {

    @BindView(R.id.forget_email)
    protected TextInputEditText email;

    @BindView(R.id.layout_forget_data)
    protected RelativeLayout layoutData;

    @BindView(R.id.layout_success)
    protected RelativeLayout layoutSuccess;

    private ForgetPasswordPresenter forgetPasswordPresenter;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgetPasswordFragment newInstance() {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_forget_password, container, false);

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        super.viewModel = new ForgetPasswordModel();
        forgetPasswordPresenter = new ForgetPasswordPresenter();
        forgetPasswordPresenter.init(super.viewModel, this, new ApiProvider());

        setOnFocusEditText();

        return root;
    }

    @OnTextChanged(R.id.forget_email)
    void onEditEmailChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.forget_email)
    void onEditEmailTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    public void settEditViewBackgroundTransparent(){
        email.setBackground(getResources().getDrawable(R.drawable.background_edittext));
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

        email.setOnClickListener(view -> {
            new Handler().postDelayed(() -> {
                TextInputLayout til = (TextInputLayout) email.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
            }, 0);
        });
    }

    @OnClick(R.id.btn_change)
    void forgetPasswordClicked() {
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
            if (!Constanta.isValidEmailID(email.getText().toString())){
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
            TextInputLayout til = (TextInputLayout) email.getParent().getParent();
            til.setErrorEnabled(false);
            til.setError(null);
        }

        if(doNext) {
            super.viewModel.setEmail(email.getText().toString());

            forgetPasswordPresenter.doForgetPassword();
        }
    }

    @Override
    public void onChangeSuccess() {
        layoutSuccess.setVisibility(View.VISIBLE);
        layoutData.setVisibility(View.GONE);

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
    public void updateModel(ForgetPasswordModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }
}
