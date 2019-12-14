package com.nurul.simpakat.view.home.ui.proker;

import android.util.Log;

import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.ApiUtil;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.remote.ListDatProkerResponse;
import com.nurul.simpakat.model.remote.ProkerCreateRequest;
import com.nurul.simpakat.model.remote.ProkerCreateResponse;
import com.nurul.simpakat.presenter.AbstractPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProkerPresenter extends AbstractPresenter<ProkerModel, ProkerView, ApiProvider> {

    private Call<ProkerCreateResponse> callProkerCreate;
    private Call<ListDatProkerResponse> callListProkers;
    private ApiUtil apiUtils;

    @Override
    public void init(ProkerModel viewModel, ProkerView view, ApiProvider apiProvider) {
        super.init(viewModel, view, apiProvider);
        apiUtils = new ApiUtil(super.remoteFunctions);
    }

    @Override
    public void deinit() {

    }

    @Override
    public void fetch() {

    }

    public void insertProker(String userid) {
        ProkerCreateRequest prokerCreateRequest = new ProkerCreateRequest();

        String iduser = userid;
        String unitKerja = super.viewModel.getUnitKerja();
        String programKerja = super.viewModel.getProgramKerja();
        String keterangan = super.viewModel.getKeterangan();
        String accountCOA = super.viewModel.getAccountCOA();
        String jenisPembiayaan = super.viewModel.getJenisPembiayaan();
        String biaya = super.viewModel.getBiaya();
        String bulanKegiatan = super.viewModel.getBulanKegiatan();

        if (android.text.TextUtils.isEmpty(programKerja)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.proker_name_required));
        } else if (android.text.TextUtils.isEmpty(biaya)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.biaya_required));
        } else {
            super.view.displayLoadIndicator();
            prokerCreateRequest.setIdUser(iduser);
            prokerCreateRequest.setKodeUnitKerja(unitKerja);
            prokerCreateRequest.setNamaProker(programKerja);
            prokerCreateRequest.setKeterangan(keterangan);
            prokerCreateRequest.setIdCOA(accountCOA);
            prokerCreateRequest.setJenisPembiayaan(jenisPembiayaan);
            prokerCreateRequest.setBulanKegiatan(bulanKegiatan);
            prokerCreateRequest.setBiaya(biaya);
            prokerCreateRequest.setStatusProker("Belum Diajukan");

            callProkerCreate = getRemoteFunctions().callProkerCreate(prokerCreateRequest);

            callProkerCreate.enqueue(new Callback<ProkerCreateResponse>() {

                @Override
                public void onResponse(Call<ProkerCreateResponse> call, Response<ProkerCreateResponse> response) {
                    ProkerCreateResponse prokerCreateResponse;
                    String message;

                    getView().hideLoadIndicator();
                    Log.d("PROKER", "response " + response);
//                    prokerCreateResponse = response.body();
//                    Log.d("PROKER", "isi response add proker " + prokerCreateResponse);

                    if (TextUtils.equalIgnoreCase(
                            response.body().getData(),
                            Constanta.OK)) {
                        Log.d("PROKER", "success");
                        getView().hideLoadIndicator();
                        getView().onProkerAdded();
                    }
                }

                @Override
                public void onFailure(Call<ProkerCreateResponse> call, Throwable t) {
                    Log.e("ERROR", "error add proker : " + t.getMessage());
                    getView().hideLoadIndicator();
                }
            });
        }
    }

    public void requestDataProkerFromServer() {
        callListProkers = getRemoteFunctions().callGetDataProker();

        callListProkers.enqueue(new Callback<ListDatProkerResponse>() {

            @Override
            public void onResponse(Call<ListDatProkerResponse> call, Response<ListDatProkerResponse> response) {

                getView().hideLoadIndicator();
                Log.d("PROKER", "response " + response);
//                    prokerCreateResponse = response.body();
//                    Log.d("PROKER", "isi response add proker " + prokerCreateResponse);

                if (TextUtils.equalIgnoreCase(response.body().getResultCode(), Constanta.OK)) {
                    if(response.body().getCount() > 0) {
                        Log.d("PROKER", "success");
                        getView().hideLoadIndicator();
                        getView().onProkerRetrieve(response.body().getListProkers());
                    } else {
                        Log.d("PROKER", "success");
                        getView().hideLoadIndicator();
                        getView().onProkerNull();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListDatProkerResponse> call, Throwable t) {
                Log.e("ERROR", "error add proker : " + t.getMessage());
                getView().hideLoadIndicator();
            }
        });
    }
}
