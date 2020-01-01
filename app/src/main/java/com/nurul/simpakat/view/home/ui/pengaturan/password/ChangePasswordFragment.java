package com.nurul.simpakat.view.home.ui.pengaturan.password;


import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.nurul.simpakat.AbstractFragmentView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.presenter.ChangePasswordPresenter;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends AbstractFragmentView<ChangePasswordModel> implements ChangePasswordView {

    @BindView(R.id.change_new_password)
    protected ShowHidePasswordEditText newPassword;

    @BindView(R.id.change_new_confirm_password)
    protected ShowHidePasswordEditText confirmNewPassword;

    @BindView(R.id.layout_data)
    protected RelativeLayout layoutData;

    @BindView(R.id.layout_change_success)
    protected RelativeLayout layoutSuccess;

    private ChangePasswordPresenter changePasswordPresenter;
    private PreferenceUtils preferenceUtils;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);

        preferenceUtils = new PreferenceUtils(
                getActivity(), Constanta.APPLICATION_PREFERENCE);

        ButterKnife.bind(this, root);

        setAppPreference(new PreferenceUtils(getActivity(), Constanta.APPLICATION_PREFERENCE));

        super.viewModel = new ChangePasswordModel();
        changePasswordPresenter = new ChangePasswordPresenter();
        changePasswordPresenter.init(super.viewModel, this, new ApiProvider());

        setOnFocusEditText();

        return root;
    }

    @OnTextChanged(R.id.change_new_password)
    void onNewPasswordTextChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.change_new_password)
    void onNewPasswordTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    @OnTextChanged(R.id.change_new_confirm_password)
    void onConfirmNewPasswordTextChanged(){
        settEditViewBackgroundTransparent();
    }

    @OnFocusChange(R.id.change_new_confirm_password)
    void onConfirmNewPasswordTouch(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settEditViewBackgroundTransparent();
            }
        }, 50);
    }

    public void settEditViewBackgroundTransparent(){
        newPassword.setBackground(getResources().getDrawable(R.drawable.background_edittext));
        confirmNewPassword.setBackground(getResources().getDrawable(R.drawable.background_edittext));
    }

    private void setOnFocusEditText() {
        newPassword.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) newPassword.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });

        newPassword.setOnClickListener(view -> {
            new Handler().postDelayed(() -> {
                TextInputLayout til = (TextInputLayout) newPassword.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
            }, 0);
        });

        confirmNewPassword.setOnFocusChangeListener((view, b) -> {
            if(b){
                TextInputLayout til = (TextInputLayout) confirmNewPassword.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
                settEditViewBackgroundTransparent();
            }
        });

        confirmNewPassword.setOnClickListener(view -> {
            new Handler().postDelayed(() -> {
                TextInputLayout til = (TextInputLayout) confirmNewPassword.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);
            }, 0);
        });
    }

    @OnClick(R.id.btn_change_new_pass)
    void onChangeNewPasswordClicked() {
        Boolean doNext = true;

        if(newPassword.getText().toString().equals("")){
            TextInputLayout til = (TextInputLayout) newPassword.getParent().getParent();
            til.setErrorEnabled(true);
            til.setError(getString(R.string.password_required));
            newPassword.setBackgroundColor(Color.TRANSPARENT);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    newPassword.setBackgroundResource(R.drawable.background_edittext);
                }
            }, 50);
            doNext = false;
        } else {
            if(newPassword.getText().toString().equals(confirmNewPassword.getText().toString())) {
                TextInputLayout til = (TextInputLayout) newPassword.getParent().getParent();
                til.setErrorEnabled(false);
                til.setError(null);

                TextInputLayout tilNew = (TextInputLayout) confirmNewPassword.getParent().getParent();
                tilNew.setErrorEnabled(false);
                tilNew.setError(null);
            } else {
                TextInputLayout til = (TextInputLayout) newPassword.getParent().getParent();
                til.setErrorEnabled(true);
                til.setError(getString(R.string.password_incorrect));
                newPassword.setBackgroundColor(Color.TRANSPARENT);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newPassword.setBackgroundResource(R.drawable.background_edittext);
                    }
                }, 50);

                TextInputLayout tilNew = (TextInputLayout) confirmNewPassword.getParent().getParent();
                tilNew.setErrorEnabled(true);
                tilNew.setError(getString(R.string.password_incorrect));
                confirmNewPassword.setBackgroundColor(Color.TRANSPARENT);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        confirmNewPassword.setBackgroundResource(R.drawable.background_edittext);
                    }
                }, 50);
                doNext = false;
            }
        }

        if(doNext) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Change Password Confirmation")
                    .setMessage("Do you really want to change your password?")
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> onClickChange())
                    .setNegativeButton(android.R.string.no, (dialog, whichButton) -> dialog.dismiss())
                    .show();
        }
    }

    private void onClickChange() {
        super.viewModel.setNIP(preferenceUtils.getString(Constanta.PREF_ID, ""));
        super.viewModel.setPassword(newPassword.getText().toString());

        changePasswordPresenter.changePasswordUser();
    }

    @Override
    public void onChangeSuccess() {
//        layoutSuccess.setVisibility(View.VISIBLE);
//        layoutData.setVisibility(View.GONE);
        displayMessage("Change Password", "Your Password Successfully Changed", false);
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
    public void updateModel(ChangePasswordModel model) {

    }

    @Override
    public boolean goBack() {
        return false;
    }
}
