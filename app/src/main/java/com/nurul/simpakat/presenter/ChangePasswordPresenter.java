package com.nurul.simpakat.presenter;

import android.util.Log;

import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.ApiUtil;
import com.nurul.simpakat.common.util.EncryptionUtils;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.remote.ChangePasswordCreateRequest;
import com.nurul.simpakat.model.remote.ChangePasswordResponse;
import com.nurul.simpakat.model.simpakat.ChangePasswordModel;
import com.nurul.simpakat.view.ChangePasswordView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordPresenter extends AbstractPresenter<ChangePasswordModel, ChangePasswordView, ApiProvider> {

    private ApiUtil apiUtils;
    private Call<ChangePasswordResponse> callChangePasswordUser;

    public ChangePasswordPresenter() {
    }

    @Override
    public void init(ChangePasswordModel viewModel, ChangePasswordView view,
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

    public void changePasswordUser() {
        if (!android.text.TextUtils.isEmpty(getViewModel().getPassword())) {
            super.view.displayLoadIndicator();

            ChangePasswordCreateRequest changePasswordCreateRequest = new ChangePasswordCreateRequest();
            changePasswordCreateRequest.setNip(getViewModel().getNIP());
            changePasswordCreateRequest.setPassword(EncryptionUtils.getSHA256(getViewModel().getPassword()));
            callChangePasswordUser = getRemoteFunctions().callChangePassword(changePasswordCreateRequest);
            callChangePasswordUser.enqueue(new Callback<ChangePasswordResponse>() {
                @Override
                public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                    ChangePasswordResponse responseData;

                    responseData = response.body();
                    Log.e("RESPONSE", "response " + responseData);
                    if(TextUtils.equalIgnoreCase(responseData.getResultCode(), Constanta.OK)) {
                        getView().hideLoadIndicator();
                        getView().onChangeSuccess();
                    } else {
                        getView().hideLoadIndicator();
                        getView().displayMessage("Change Password", responseData.getReasonText(), false);
                    }
                }

                @Override
                public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                    Log.e("ERROR", "error change password : " + t.getMessage());
                    getView().hideLoadIndicator();
                }
            });
        } else {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.password_required));
        }
    }
}
