package com.nurul.simpakat.presenter;

import android.util.Log;

import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.ApiUtil;
import com.nurul.simpakat.common.util.EncryptionUtils;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.remote.SignInUserCreateRequest;
import com.nurul.simpakat.model.remote.SignInUserCreateResponse;
import com.nurul.simpakat.model.simpakat.LoginModel;
import com.nurul.simpakat.view.LoginView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends AbstractPresenter<LoginModel, LoginView, ApiProvider> {
    private ApiUtil apiUtils;

    private Call<SignInUserCreateResponse> callSignInUserCreateByEmail;

    public LoginPresenter() {

    }

    @Override
    public void init(LoginModel viewModel, LoginView view,
                     ApiProvider apiProvider) {
        super.init(viewModel, view, apiProvider);

        apiUtils = new ApiUtil(super.remoteFunctions);
    }

    @Override
    public void deinit() {

    }

    @Override
    public void fetch() {

    }

    public void signInWithEmail() {
        if (!android.text.TextUtils.isEmpty(getViewModel().getNip())) {
            super.view.displayLoadIndicator();

            SignInUserCreateRequest signInUserCreateRequest = new SignInUserCreateRequest();
            signInUserCreateRequest.setNip(getViewModel().getNip());
            signInUserCreateRequest.setPassword(EncryptionUtils.getSHA256(getViewModel().getPassword()));
            signInUserCreateRequest.setdToken(getViewModel().getdToken());
            callSignInUserCreateByEmail =
                    getRemoteFunctions().callSignInUserCreateByEmail(signInUserCreateRequest);

            callSignInUserCreateByEmail.enqueue(new Callback<SignInUserCreateResponse>() {

                @Override
                public void onResponse(Call<SignInUserCreateResponse> call, Response<SignInUserCreateResponse> response) {
                    SignInUserCreateResponse responseData;
                    String message;

                    responseData = response.body();
                    Log.d("RESPONSE", "response " + responseData);
                    if(TextUtils.equalIgnoreCase(responseData.getResultCode(), Constanta.OK)) {
                        getViewModel().setNip(responseData.getData().getNip());
                        getViewModel().setEmail(responseData.getData().getEmail());
                        getViewModel().setName(responseData.getData().getNamaUser());
                        getViewModel().setJabatan(responseData.getData().getJabatan());
                        getViewModel().setKodeUnitKerja(responseData.getData().getKodeUnitKerja());
                        getViewModel().setUser(responseData.getData());

                        getView().hideLoadIndicator();
                        getView().onLoginSuccess();
                    } else {
                        getView().hideLoadIndicator();
                        getView().displayMessage("Login", responseData.getReasonText(), false);
                    }
                }

                @Override
                public void onFailure(Call<SignInUserCreateResponse> call, Throwable t) {
                    Log.e("ERROR", String.valueOf(t.getMessage()));
                    getView().hideLoadIndicator();
                }
            });
        } else {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.nip_required));
        }
    }
}
