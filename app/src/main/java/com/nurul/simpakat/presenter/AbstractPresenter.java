package com.nurul.simpakat.presenter;

import com.nurul.simpakat.BaseView;
import com.nurul.simpakat.common.provider.api.ApiProvider;
import com.nurul.simpakat.common.provider.api.RemoteFunctions;

public abstract class AbstractPresenter<VM, VW extends BaseView, RA extends ApiProvider> implements BasePresenter<VM, VW, RA> {
    protected RemoteFunctions remoteFunctions;
    protected VW view;
    protected VM viewModel;

    public RemoteFunctions getRemoteFunctions() {
        return remoteFunctions;
    }

    public void setRemoteFunctions(RemoteFunctions remoteFunctions) {
        this.remoteFunctions = remoteFunctions;
    }

    public VW getView() {
        return view;
    }

    public void setView(VW view) {
        this.view = view;
    }

    public VM getViewModel() {
        return viewModel;
    }

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void init(VM viewModel, VW view, RA apiProvider) {
        if (apiProvider == null) {
            throw new IllegalArgumentException("ApiProvider can not be null");
        }
        setView(view);
        setViewModel(viewModel);
        setRemoteFunctions(apiProvider.getRemoteFunctions());
    }
}
