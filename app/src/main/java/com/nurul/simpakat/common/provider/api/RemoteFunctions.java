package com.nurul.simpakat.common.provider.api;

import com.nurul.simpakat.model.remote.SignInUserCreateRequest;
import com.nurul.simpakat.model.remote.SignInUserCreateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RemoteFunctions {
    @POST("user_login.php")
    Call<SignInUserCreateResponse> callSignInUserCreateByEmail(
            @Body SignInUserCreateRequest request);
}
