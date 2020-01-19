package com.nurul.simpakat.presenter;

import android.util.Log;

import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.ApiUtil;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.remote.DataListPengajuanResponse;
import com.nurul.simpakat.model.remote.ListPengajuanCreateRequest;
import com.nurul.simpakat.model.remote.PengajuanCreateResponse;
import com.nurul.simpakat.model.simpakat.ListPengajuanModel;
import com.nurul.simpakat.view.ListPengajuanView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPengajuanPresenter extends AbstractPresenter<ListPengajuanModel, ListPengajuanView, ApiProvider> {

    private Call<PengajuanCreateResponse> callPengajuanResponse;
    private Call<DataListPengajuanResponse> callListPengajuans;
    private ApiUtil apiUtils;

    @Override
    public void init(ListPengajuanModel viewModel, ListPengajuanView view, ApiProvider apiProvider) {
        super.init(viewModel, view, apiProvider);
        apiUtils = new ApiUtil(super.remoteFunctions);
    }

    @Override
    public void deinit() {

    }

    @Override
    public void fetch() {

    }

    public void requestDataPengajuanFromServer(String id, String kodeUnit, String jabatan) {
        ListPengajuanCreateRequest listPengajuanCreateRequest = new ListPengajuanCreateRequest();

        String nip = id;
        String kodeUnitKerja = kodeUnit;
        String role = jabatan;

        Log.e("JABATAN", "isi jabatan " + role + kodeUnitKerja + nip);

        if (android.text.TextUtils.isEmpty(id)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.nip_required));
        }

        listPengajuanCreateRequest.setNip(nip);
        listPengajuanCreateRequest.setJabatan(role);
        listPengajuanCreateRequest.setKodeUnitKerja(kodeUnitKerja);

        callListPengajuans = getRemoteFunctions().callGetListDataPengajuan(listPengajuanCreateRequest);
        callListPengajuans.enqueue(new Callback<DataListPengajuanResponse>() {
            @Override
            public void onResponse(Call<DataListPengajuanResponse> call, Response<DataListPengajuanResponse> response) {
                getView().hideLoadIndicator();
                Log.d("PENGAJUAN", "response " + response);

                if (TextUtils.equalIgnoreCase(response.body().getResultCode(), Constanta.OK)) {
                    if(response.body().getCount() > 0) {
                        Log.d("LISTPENGAJUAN", "success");
                        getView().hideLoadIndicator();
                        getView().onListPengajuanRetrieves(response.body().getListPengajuans());
                    } else {
                        Log.d("LISTPENGAJUAN", "success");
                        getView().hideLoadIndicator();
                        getView().onListPengajuanNull();
                    }
                } else {
                    Log.d("LISTPENGAJUAN", "failure");
                    getView().hideLoadIndicator();
                    getView().onListPengajuanFailed(response.body().getReasonText());
                }
            }

            @Override
            public void onFailure(Call<DataListPengajuanResponse> call, Throwable t) {
                Log.e("ERROR", "error get list pengajuan : " + t.getMessage());
                getView().hideLoadIndicator();
            }
        });
    }
}
