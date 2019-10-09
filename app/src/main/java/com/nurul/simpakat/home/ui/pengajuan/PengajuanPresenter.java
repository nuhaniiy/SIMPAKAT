package com.nurul.simpakat.home.ui.pengajuan;

import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.util.ApiUtil;
import com.nurul.simpakat.model.remote.PengajuanCreateResponse;
import com.nurul.simpakat.presenter.AbstractPresenter;

import retrofit2.Call;

public class PengajuanPresenter extends AbstractPresenter<PengajuanModel, PengajuanView, ApiProvider> {

    private Call<PengajuanCreateResponse> callPengajuanResponse;
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

    }
}
