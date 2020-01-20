package com.nurul.simpakat.presenter;

import android.util.Log;

import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.ApiUtil;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.remote.EmployeeCreateResponse;
import com.nurul.simpakat.model.remote.ListDataEmployeeResponse;
import com.nurul.simpakat.model.simpakat.EmployeeModel;
import com.nurul.simpakat.view.EmployeeView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeePresenter extends AbstractPresenter<EmployeeModel, EmployeeView, ApiProvider> {

    private Call<EmployeeCreateResponse> callEmployeeResponse;
    private Call<ListDataEmployeeResponse> callListEmployees;
    private ApiUtil apiUtils;

    @Override
    public void init(EmployeeModel viewModel, EmployeeView view, ApiProvider apiProvider) {
        super.init(viewModel, view, apiProvider);
        apiUtils = new ApiUtil(super.remoteFunctions);
    }

    @Override
    public void deinit() {

    }

    @Override
    public void fetch() {

    }

    public void insertEmployee() {

    }

    public void requestDataEmployeeFromServer() {
        callListEmployees = getRemoteFunctions().callGetDataKaryawan();
        callListEmployees.enqueue(new Callback<ListDataEmployeeResponse>() {
            @Override
            public void onResponse(Call<ListDataEmployeeResponse> call, Response<ListDataEmployeeResponse> response) {
                getView().hideLoadIndicator();
                Log.d("KARYAWAN", "response " + response);

                if (TextUtils.equalIgnoreCase(response.body().getResultCode(), Constanta.OK)) {
                    if(response.body().getCount() > 0) {
                        Log.d("KARYAWAN", "success");
                        getView().hideLoadIndicator();
                        getView().onEmployeeRetrieves(response.body().getListEmployees());
                    } else {
                        Log.d("KARYAWAN", "success");
                        getView().hideLoadIndicator();
                        getView().onEmployeeNull();
                    }
                } else {
                    Log.d("KARYAWAN", "failure");
                    getView().hideLoadIndicator();
                    getView().onEmployeeFailed(response.body().getReasonText());
                }
            }

            @Override
            public void onFailure(Call<ListDataEmployeeResponse> call, Throwable t) {
                Log.e("ERROR", "error get karyawan : " + t.getMessage());
                getView().hideLoadIndicator();
            }
        });
    }
}
