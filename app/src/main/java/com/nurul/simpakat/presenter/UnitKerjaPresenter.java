package com.nurul.simpakat.presenter;

import android.util.Log;

import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.ApiUtil;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.remote.ListDataUnitKerjaResponse;
import com.nurul.simpakat.model.remote.UnitKerjaCreateResponse;
import com.nurul.simpakat.model.simpakat.DataUnitKerjaModel;
import com.nurul.simpakat.view.UnitKerjaView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnitKerjaPresenter extends AbstractPresenter<DataUnitKerjaModel, UnitKerjaView, ApiProvider> {

    private Call<UnitKerjaCreateResponse> callUnitKerjaResponse;
    private Call<ListDataUnitKerjaResponse> callListUnitKerja;
    private ApiUtil apiUtils;

    @Override
    public void init(DataUnitKerjaModel viewModel, UnitKerjaView view, ApiProvider apiProvider) {
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

    public void requestDataUnitKerjaFromServer() {
        callListUnitKerja = getRemoteFunctions().callGetDataUnitKerja();
        callListUnitKerja.enqueue(new Callback<ListDataUnitKerjaResponse>() {
            @Override
            public void onResponse(Call<ListDataUnitKerjaResponse> call, Response<ListDataUnitKerjaResponse> response) {
                getView().hideLoadIndicator();
                Log.d("UNITKERJA", "response " + response);

                if (TextUtils.equalIgnoreCase(response.body().getResultCode(), Constanta.OK)) {
                    if(response.body().getCount() > 0) {
                        Log.d("UNITKERJA", "success");
                        getView().hideLoadIndicator();
                        getView().onUnitKerjaRetrieves(response.body().getListUnitKerja());
                    } else {
                        Log.d("UNITKERJA", "success");
                        getView().hideLoadIndicator();
                        getView().onUnitKerjaNull();
                    }
                } else {
                    Log.d("UNITKERJA", "failure");
                    getView().hideLoadIndicator();
                    getView().onUnitKerjaFailed(response.body().getReasonText());
                }
            }

            @Override
            public void onFailure(Call<ListDataUnitKerjaResponse> call, Throwable t) {
                Log.e("ERROR", "error get unitkerja : " + t.getMessage());
                getView().hideLoadIndicator();
            }
        });
    }
}
