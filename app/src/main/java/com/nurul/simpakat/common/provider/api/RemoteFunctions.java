package com.nurul.simpakat.common.provider.api;

import com.nurul.simpakat.model.remote.ChangePasswordCreateRequest;
import com.nurul.simpakat.model.remote.ChangePasswordResponse;
import com.nurul.simpakat.model.remote.DataListPengajuanResponse;
import com.nurul.simpakat.model.remote.ForgetPasswordCreateRequest;
import com.nurul.simpakat.model.remote.ForgetPasswordResponse;
import com.nurul.simpakat.model.remote.ListDatProkerResponse;
import com.nurul.simpakat.model.remote.ListDataPengajuanResponse;
import com.nurul.simpakat.model.remote.ListPengajuanCreateRequest;
import com.nurul.simpakat.model.remote.PengajuanCreateRequest;
import com.nurul.simpakat.model.remote.PengajuanCreateResponse;
import com.nurul.simpakat.model.remote.ProkerCreateRequest;
import com.nurul.simpakat.model.remote.ProkerCreateResponse;
import com.nurul.simpakat.model.remote.RetrievePengajuanRequest;
import com.nurul.simpakat.model.remote.RetrieveProkerRequest;
import com.nurul.simpakat.model.remote.SignInUserCreateRequest;
import com.nurul.simpakat.model.remote.SignInUserCreateResponse;
import com.nurul.simpakat.model.remote.SignUpUserCreateRequest;
import com.nurul.simpakat.model.remote.SignUpUserCreateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RemoteFunctions {
    @POST("user_login.php")
    Call<SignInUserCreateResponse> callSignInUserCreateByEmail(
            @Body SignInUserCreateRequest request);

    @POST("simpakat_register_user.php")
    Call<SignUpUserCreateResponse> callSignUpUserCreateByEmail(
            @Body SignUpUserCreateRequest request);

    @POST("simpakat_add_proker.php")
    Call<ProkerCreateResponse> callProkerCreate(
            @Body ProkerCreateRequest request);

    @POST("simpakat_get_data_proker.php")
    Call<ListDatProkerResponse> callGetDataProker(
            @Body RetrieveProkerRequest request);

    @POST("simpakat_add_pengajuan_proker.php")
    Call<PengajuanCreateResponse> callPengajuanProkerCreate(
            @Body PengajuanCreateRequest request);

    @POST("simpakat_get_data_pengajuan.php")
    Call<ListDataPengajuanResponse> callGetDataPengajuan(
            @Body RetrievePengajuanRequest request);

    @POST("simpakat_get_list_data_pengajuan.php")
    Call<DataListPengajuanResponse> callGetListDataPengajuan(
            @Body ListPengajuanCreateRequest request);

    @POST("simpakat_check_email_user.php")
    Call<ForgetPasswordResponse> callForgetPassword(
            @Body ForgetPasswordCreateRequest request);

    @POST("simpakat_user_change_password.php")
    Call<ChangePasswordResponse> callChangePassword(
            @Body ChangePasswordCreateRequest request);
}
