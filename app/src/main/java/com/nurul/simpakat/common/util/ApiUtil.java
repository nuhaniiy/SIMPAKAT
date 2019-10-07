package com.nurul.simpakat.common.util;

import com.nurul.simpakat.common.provider.api.RemoteFunctions;

public class ApiUtil {
    private RemoteFunctions remoteFunctions;

    public interface FailureCallback {
        void onFailure(String message);
        void onFailure(Throwable t);
    }

    public ApiUtil(RemoteFunctions remoteFunctions) {
        this.remoteFunctions = remoteFunctions;
    }
}
