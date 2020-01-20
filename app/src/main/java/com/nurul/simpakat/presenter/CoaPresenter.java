package com.nurul.simpakat.presenter;

import android.util.Log;

import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.ApiUtil;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.remote.CoaCreateRequest;
import com.nurul.simpakat.model.remote.CoaCreateResponse;
import com.nurul.simpakat.model.remote.ListDataCoaResponse;
import com.nurul.simpakat.model.simpakat.CoaModel;
import com.nurul.simpakat.view.CoaView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoaPresenter extends AbstractPresenter<CoaModel, CoaView, ApiProvider> {

    private Call<CoaCreateResponse> callCoaResponse;
    private Call<ListDataCoaResponse> callListCoa;
    private ApiUtil apiUtils;

    @Override
    public void init(CoaModel viewModel, CoaView view, ApiProvider apiProvider) {
        super.init(viewModel, view, apiProvider);
        apiUtils = new ApiUtil(super.remoteFunctions);
    }

    @Override
    public void deinit() {

    }

    @Override
    public void fetch() {

    }

    public void insertCoa() {
        CoaCreateRequest coaCreateRequest = new CoaCreateRequest();

        String noAccount = super.viewModel.getKodeCoa();
        String kodeParent = super.viewModel.getKodeParent();
        String namaAccount = super.viewModel.getNamaCoa();

        if (android.text.TextUtils.isEmpty(noAccount)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.kode_account_required));
        } else if (android.text.TextUtils.isEmpty(namaAccount)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.name_account_required));
        } else {
            super.view.displayLoadIndicator();
            coaCreateRequest.setNoAccount(noAccount);
            coaCreateRequest.setKodeParent(kodeParent);
            coaCreateRequest.setNamaAccount(namaAccount);

            callCoaResponse = getRemoteFunctions().callAddCoa(coaCreateRequest);

            callCoaResponse.enqueue(new Callback<CoaCreateResponse>() {

                @Override
                public void onResponse(Call<CoaCreateResponse> call, Response<CoaCreateResponse> response) {
                    CoaCreateResponse prokerCreateResponse;
                    String message;

                    getView().hideLoadIndicator();
                    Log.d("COA", "response " + response);
//                    prokerCreateResponse = response.body();
//                    Log.d("PROKER", "isi response add proker " + prokerCreateResponse);

                    if (TextUtils.equalIgnoreCase(
                            response.body().getData(),
                            Constanta.OK)) {
                        Log.d("COA", "success");
                        getView().hideLoadIndicator();
                        getView().onCoaAdded();
                    }
//                    else {
//                        getView().hideLoadIndicator();
//                        getView().onCoaAdded();
//                    }
                }

                @Override
                public void onFailure(Call<CoaCreateResponse> call, Throwable t) {
                    Log.e("ERROR", "error add coa : " + t.getMessage());
                    getView().hideLoadIndicator();
                }
            });
        }
    }

    public void requestDataCoaFromServer() {
        callListCoa = getRemoteFunctions().callGetDataCoa();
        callListCoa.enqueue(new Callback<ListDataCoaResponse>() {
            @Override
            public void onResponse(Call<ListDataCoaResponse> call, Response<ListDataCoaResponse> response) {
                getView().hideLoadIndicator();
                Log.d("COA", "response " + response);

                if (TextUtils.equalIgnoreCase(response.body().getResultCode(), Constanta.OK)) {
                    if(response.body().getCount() > 0) {
                        Log.d("COA", "success");
                        getView().hideLoadIndicator();
                        getView().onCoaRetrieves(response.body().getListDataCoas());
                    } else {
                        Log.d("COA", "success");
                        getView().hideLoadIndicator();
                        getView().onCoaNull();
                    }
                } else {
                    Log.d("COA", "failure");
                    getView().hideLoadIndicator();
                    getView().onCoaFailed(response.body().getReasonText());
                }
            }

            @Override
            public void onFailure(Call<ListDataCoaResponse> call, Throwable t) {
                Log.e("ERROR", "error get coa : " + t.getMessage());
                getView().hideLoadIndicator();
            }
        });
    }
}
