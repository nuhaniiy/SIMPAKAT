package com.nurul.simpakat.view.login.changePassword;

import android.util.Log;

import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.ApiUtil;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.remote.ForgetPasswordCreateRequest;
import com.nurul.simpakat.model.remote.ForgetPasswordResponse;
import com.nurul.simpakat.presenter.AbstractPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordPresenter extends AbstractPresenter<ForgetPasswordModel, ForgetPasswordView, ApiProvider> {
    private ApiUtil apiUtils;

    private Call<ForgetPasswordResponse> callForgetPassword;

    public ForgetPasswordPresenter() {
    }

    @Override
    public void init(ForgetPasswordModel viewModel, ForgetPasswordView view,
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

    public void doForgetPassword() {
        if (!android.text.TextUtils.isEmpty(getViewModel().getEmail())) {
            super.view.displayLoadIndicator();

            ForgetPasswordCreateRequest forgetPasswordCreateRequest = new ForgetPasswordCreateRequest();
            forgetPasswordCreateRequest.setEmail(getViewModel().getEmail());
            callForgetPassword = getRemoteFunctions().callForgetPassword(forgetPasswordCreateRequest);
            callForgetPassword.enqueue(new Callback<ForgetPasswordResponse>() {
                @Override
                public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {
                    ForgetPasswordResponse responseData;

                    responseData = response.body();
                    Log.e("RESPONSE", "response " + responseData);
                    if(TextUtils.equalIgnoreCase(responseData.getResultCode(), Constanta.OK)) {
                        getView().hideLoadIndicator();
                        getView().onChangeSuccess();
                    } else {
                        getView().hideLoadIndicator();
                        getView().displayMessage("Login", responseData.getReasonText(), false);
                    }
                }

                @Override
                public void onFailure(Call<ForgetPasswordResponse> call, Throwable t) {

                }
            });
        }
    }
}
