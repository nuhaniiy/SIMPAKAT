package com.nurul.simpakat.presenter;

import android.util.Log;

import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.ApiUtil;
import com.nurul.simpakat.common.util.TextUtils;
import com.nurul.simpakat.model.remote.KegiatanCreateRequest;
import com.nurul.simpakat.model.remote.KegiatanCreateResponse;
import com.nurul.simpakat.model.remote.ListKegiatanCreateRequest;
import com.nurul.simpakat.model.remote.ListKegiatanResponse;
import com.nurul.simpakat.model.simpakat.KegiatanModel;
import com.nurul.simpakat.view.KegiatanView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KegiatanPresenter extends AbstractPresenter<KegiatanModel, KegiatanView, ApiProvider> {

    private Call<KegiatanCreateResponse> callKegiatanResponse;
    private Call<ListKegiatanResponse> callListKegiatan;
    private ApiUtil apiUtils;

    @Override
    public void init(KegiatanModel viewModel, KegiatanView view, ApiProvider apiProvider) {
        super.init(viewModel, view, apiProvider);
        apiUtils = new ApiUtil(super.remoteFunctions);
    }

    @Override
    public void deinit() {

    }

    @Override
    public void fetch() {

    }

    public void insertKegiatan() {
        KegiatanCreateRequest kegiatanCreateRequest = new KegiatanCreateRequest();

        String idProker = super.viewModel.getIdProker();
        String namaKegiatan = super.viewModel.getNamaKegiatan();
        String nominal = super.viewModel.getNominalAnggaran();
        String tanggalKegiatan = super.viewModel.getTanggalKegiatan();
        String statusKegiatan = super.viewModel.getStatusKegiatan();
        String nip = super.viewModel.getNip();

        if (android.text.TextUtils.isEmpty(namaKegiatan)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.nama_kegiatan_required));
        } else if (android.text.TextUtils.isEmpty(nominal)) {
            getView().displayMessage(
                    getView().getResourceString(R.string.info),
                    getView().getResourceString(R.string.nominal_anggaran_kegiatan_required));
        } else {
            super.view.displayLoadIndicator();
            kegiatanCreateRequest.setIdProker(idProker);
            kegiatanCreateRequest.setNamaKegiatan(namaKegiatan);
            kegiatanCreateRequest.setNominalAnggaran(nominal);
            kegiatanCreateRequest.setTanggalKegiatan(tanggalKegiatan);
            kegiatanCreateRequest.setStatusKegiatan(statusKegiatan);
            kegiatanCreateRequest.setNip(nip);

            callKegiatanResponse = getRemoteFunctions().callKegiatanCreate(kegiatanCreateRequest);

            callKegiatanResponse.enqueue(new Callback<KegiatanCreateResponse>() {

                @Override
                public void onResponse(Call<KegiatanCreateResponse> call, Response<KegiatanCreateResponse> response) {
                    KegiatanCreateResponse prokerCreateResponse;
                    String message;

                    getView().hideLoadIndicator();
                    Log.d("KEGIATAN", "response " + response);

                    if (TextUtils.equalIgnoreCase(
                            response.body().getData(),
                            Constanta.OK)) {
                        Log.d("KEGIATAN", "success");
                        getView().hideLoadIndicator();
                        getView().onKegiatanAdded();
                    }
                }

                @Override
                public void onFailure(Call<KegiatanCreateResponse> call, Throwable t) {
                    Log.e("ERROR", "error add kegiatan : " + t.getMessage());
                    getView().hideLoadIndicator();
                }
            });
        }
    }

    public void requestDataKegiatanFromServer(String id) {
        ListKegiatanCreateRequest listKegiatanCreateRequest = new ListKegiatanCreateRequest();
        listKegiatanCreateRequest.setNip(id);
        callListKegiatan = getRemoteFunctions().callGetDataKegiatan(listKegiatanCreateRequest);
        callListKegiatan.enqueue(new Callback<ListKegiatanResponse>() {
            @Override
            public void onResponse(Call<ListKegiatanResponse> call, Response<ListKegiatanResponse> response) {
                getView().hideLoadIndicator();
                Log.d("KEGIATAN", "response " + response);

                if (TextUtils.equalIgnoreCase(response.body().getResultCode(), Constanta.OK)) {
                    if(response.body().getCount() > 0) {
                        Log.d("KEGIATAN", "success");
                        getView().hideLoadIndicator();
                        getView().onKegiatanRetrieves(response.body().getListKegiatans());
                    } else {
                        Log.d("KEGIATAN", "success");
                        getView().hideLoadIndicator();
                        getView().onKegiatanNull();
                    }
                } else {
                    Log.d("KEGIATAN", "failure");
                    getView().hideLoadIndicator();
                    getView().onKegiatanFailed(response.body().getReasonText());
                }
            }

            @Override
            public void onFailure(Call<ListKegiatanResponse> call, Throwable t) {
                Log.e("ERROR", "error get list kegiatan : " + t.getMessage());
                getView().hideLoadIndicator();
            }
        });
    }
}
