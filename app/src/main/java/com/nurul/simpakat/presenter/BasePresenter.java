package com.nurul.simpakat.presenter;

import com.nurul.simpakat.BaseView;
import com.nurul.simpakat.common.provider.api.ApiProvider;

public interface BasePresenter<VM, VW extends BaseView, RA extends ApiProvider> {
    void init(VM viewModel, VW view, RA apiProvider);
    void deinit();
    void fetch();
}
