package com.nurul.simpakat.presenter;

import android.util.Log;

import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.ApiUtil;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.remote.ListDataPengajuanResponse;
import com.nurul.simpakat.model.remote.PengajuanCreateRequest;
import com.nurul.simpakat.model.remote.PengajuanCreateResponse;
import com.nurul.simpakat.model.remote.RetrievePengajuanRequest;
import com.nurul.simpakat.model.simpakat.PengajuanModel;
import com.nurul.simpakat.view.PengajuanView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengajuanPresenter extends AbstractPresenter<PengajuanModel, PengajuanView, ApiProvider> {

    private Call<PengajuanCreateResponse> callPengajuanResponse;
    private Call<ListDataPengajuanResponse> callListPengajuans;
    private ApiUtil apiUtils;

    @Override
    public void init(PengajuanModel viewModel, PengajuanView view, ApiProvider apiProvider) {
        super.init(viewModel, view, apiProvider);
        apiUtils = new ApiUtil(super.remoteFunctions);
    }

    @Override
    public void deinit() {

    }

    @Override
    public void fetch() {

    }

    public void insertPengajuan() {
        PengajuanCreateRequest pengajuanCreateRequest = new PengajuanCreateRequest();

        String idProker = super.viewModel.getIdProker();
        String biaya = super.viewModel.getBiaya();
        String biayaTerpakai = super.viewModel.getBiayaTerpakai();
        String sisaBiaya = super.viewModel.getSisaBiaya();
        String biayaDiajukan = super.viewModel.getBiayaDiajukan();
        String dibayarKepada = super.viewModel.getDibayarKepada();
        String jabatan = super.viewModel.getJabatan();
        String noRekening = super.viewModel.getNoRekening();
        String tglPengajuan = super.viewModel.getTanggalPengajuan();
        String persetujuanDekan = super.viewModel.getPersetujuanDekan();
        String persetujuanWarek1 = super.viewModel.getPersetujuanWarek1();
        String persetujuanWarek2 = super.viewModel.getPersetujuanWarek2();
        String persetujuanRektor = super.viewModel.getPersetujuanRektor();
        String statusPengajuan = super.viewModel.getStatusPengajuan();

        if (android.text.TextUtils.isEmpty(biaya)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.biaya_required));
        } else if (android.text.TextUtils.isEmpty(biayaTerpakai)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.biaya_terpakai_required));
        } else if (android.text.TextUtils.isEmpty(biayaDiajukan)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.biaya_diajukan_required));
        } else {
            super.view.displayLoadIndicator();
            pengajuanCreateRequest.setIdProker(idProker);
            pengajuanCreateRequest.setBiaya(biaya);
            pengajuanCreateRequest.setBiayaTerpakai(biayaTerpakai);
            pengajuanCreateRequest.setSisaBiaya(sisaBiaya);
            pengajuanCreateRequest.setBiayaDiajukan(biayaDiajukan);
            pengajuanCreateRequest.setDibayarKepada(dibayarKepada);
            pengajuanCreateRequest.setJabatan(jabatan);
            pengajuanCreateRequest.setBiaya(biaya);
            pengajuanCreateRequest.setNoRekening(noRekening);
            pengajuanCreateRequest.setTanggalPengajuan(tglPengajuan);
            pengajuanCreateRequest.setPersetujuanDekan(persetujuanDekan);
            pengajuanCreateRequest.setPersetujuanWarek1(persetujuanWarek1);
            pengajuanCreateRequest.setPersetujuanWarek2(persetujuanWarek2);
            pengajuanCreateRequest.setPersetujuanRektor(persetujuanRektor);
            pengajuanCreateRequest.setStatusPengajuan(statusPengajuan);

            callPengajuanResponse = getRemoteFunctions().callPengajuanProkerCreate(pengajuanCreateRequest);

            callPengajuanResponse.enqueue(new Callback<PengajuanCreateResponse>() {

                @Override
                public void onResponse(Call<PengajuanCreateResponse> call, Response<PengajuanCreateResponse> response) {
                    PengajuanCreateResponse pengajuanCreateResponse;
                    String message;

                    getView().hideLoadIndicator();
                    Log.d("PENGAJUAN", "response " + response);

                    if (TextUtils.equalIgnoreCase(
                            response.body().getData(),
                            Constanta.OK)) {
                        Log.d("PENGAJUAN", "success");
                        getView().hideLoadIndicator();
                        getView().onPengajuanAdded();
                    }
                }

                @Override
                public void onFailure(Call<PengajuanCreateResponse> call, Throwable t) {
                    Log.e("ERROR", "error add pengajuan proker : " + t.getMessage());
                    getView().hideLoadIndicator();
                }
            });
        }
    }

    public void requestDataPengajuanFromServer(String id) {
        RetrievePengajuanRequest retrievePengajuanRequest = new RetrievePengajuanRequest();

        String nip = id;
        if(android.text.TextUtils.isEmpty(nip)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.nip_required));
        }

        retrievePengajuanRequest.setNip(nip);
        callListPengajuans = getRemoteFunctions().callGetDataPengajuan(retrievePengajuanRequest);

        callListPengajuans.enqueue(new Callback<ListDataPengajuanResponse>() {

            @Override
            public void onResponse(Call<ListDataPengajuanResponse> call, Response<ListDataPengajuanResponse> response) {

                getView().hideLoadIndicator();
                Log.d("PENGAJUAN", "response " + response);
//                    prokerCreateResponse = response.body();
//                    Log.d("PROKER", "isi response add proker " + prokerCreateResponse);

                if (TextUtils.equalIgnoreCase(response.body().getResultCode(), Constanta.OK)) {
                    if(response.body().getCount() > 0) {
                        Log.d("PENGAJUAN", "success");
                        getView().hideLoadIndicator();
                        getView().onPengajuanRetrieves(response.body().getListPengajuans());
                    } else {
                        Log.d("PENGAJUAN", "success");
                        getView().hideLoadIndicator();
                        getView().onPengajuanNull();
                    }
                } else {
                    Log.d("PENGAJUAN", "failure");
                    getView().hideLoadIndicator();
                    getView().onPengajuanFailed(response.body().getReasonText());
                }
            }

            @Override
            public void onFailure(Call<ListDataPengajuanResponse> call, Throwable t) {
                Log.e("ERROR", "error add proker : " + t.getMessage());
                getView().hideLoadIndicator();
            }
        });
    }
}
