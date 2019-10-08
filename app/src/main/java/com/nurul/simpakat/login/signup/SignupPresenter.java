package com.nurul.simpakat.login.signup;

import android.util.Log;

import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.ApiUtil;
import com.nurul.simpakat.common.util.EncryptionUtils;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.remote.SignUpUserCreateRequest;
import com.nurul.simpakat.model.remote.SignUpUserCreateResponse;
import com.nurul.simpakat.presenter.AbstractPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupPresenter extends AbstractPresenter<SignupModel, SignupView, ApiProvider> {

    private Call<SignUpUserCreateResponse> callSignUpUserCreateByEmail;
    private ApiUtil apiUtils;

    @Override
    public void init(SignupModel viewModel, SignupView view, ApiProvider apiProvider) {
        super.init(viewModel, view, apiProvider);
        apiUtils = new ApiUtil(super.remoteFunctions);
    }

    @Override
    public void deinit() {

    }

    @Override
    public void fetch() {

    }

    public void signUpWithEmail() {
        SignUpUserCreateRequest signUpUserCreateRequest = new SignUpUserCreateRequest();

        String name = super.viewModel.getName();
        String email = super.viewModel.getEmail();
        String jabatan = super.viewModel.getJabatan();
        String password = EncryptionUtils.getSHA256(super.viewModel.getPassword());
        String confirmPassword = EncryptionUtils.getSHA256(super.viewModel.getConfirmPassword());

        if (android.text.TextUtils.isEmpty(email)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.email_address_required));
        } else if (android.text.TextUtils.isEmpty(getViewModel().getPassword())) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.password_required));
        } else if (!TextUtils.equalIgnoreCase(password, confirmPassword)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.password_and_confirm_password_not_same));
        } else {
            super.view.displayLoadIndicator();
            signUpUserCreateRequest.setEmail(email);
            signUpUserCreateRequest.setPassword(password);
            signUpUserCreateRequest.setNamaUser(name);
            signUpUserCreateRequest.setJabatan(jabatan);
            signUpUserCreateRequest.setStatus("Inactive");

            callSignUpUserCreateByEmail = getRemoteFunctions().callSignUpUserCreateByEmail(signUpUserCreateRequest);

            callSignUpUserCreateByEmail.enqueue(new Callback<SignUpUserCreateResponse>() {

                @Override
                public void onResponse(Call<SignUpUserCreateResponse> call, Response<SignUpUserCreateResponse> response) {
                    SignUpUserCreateResponse signUpUserCreateResponse;
                    String message;

                    getView().hideLoadIndicator();
                    signUpUserCreateResponse = response.body();
                    Log.d("REGIS", "isi response regis " + signUpUserCreateResponse);

                    if (TextUtils.equalIgnoreCase(
                            signUpUserCreateResponse.getData(),
                            Constanta.OK)) {
                        Log.d("REGIS", "success");
                        getView().hideLoadIndicator();
                        getView().onEmailSuccessReg();
                    }
                    /*else {
                        message = apiUtils.getFailMessage(signUpUserCreateResponse);
                        message = ApiMessageUtils.translate(getView(), message);
                        getView().displayMessage(
                                getView().getResourceString(R.string.info), message);
                    }*/
                }

                @Override
                public void onFailure(Call<SignUpUserCreateResponse> call, Throwable t) {
                    Log.e("ERROR", "error regis : " + String.valueOf(t.getMessage()));
                    getView().hideLoadIndicator();
                }
            });
        }
    }
}
