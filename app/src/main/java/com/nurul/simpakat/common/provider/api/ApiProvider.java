package com.nurul.simpakat.common.provider.api;

import com.nurul.simpakat.BuildConfig;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.util.UnsafeHttpClient;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiProvider {
    public static String getServerAddress() {
        return Constanta.APPLICATION_URL;
    }

    public static String getApplicationPath() {
        return Constanta.APPLICATION_PATH;
    }

    public ApiProvider() {}

    private OkHttpClient getClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1,TimeUnit.MINUTES)
                .writeTimeout(1,TimeUnit.MINUTES)
                .addInterceptor(getLoggingInterceptor())
                .build();

        if (true) {
            okHttpClient = UnsafeHttpClient.getUnsafeHttpClient();
        }

        return  okHttpClient;
    }

    private HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(getInterceptorLevel());
        return httpLoggingInterceptor;
    }

    private HttpLoggingInterceptor.Level getInterceptorLevel() {
        if (BuildConfig.DEBUG) {
            return HttpLoggingInterceptor.Level.BODY;
        } else {
            return HttpLoggingInterceptor.Level.NONE;
        }
    }

    public RemoteFunctions getRemoteFunctions() {
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(String.format("%s%s", getServerAddress(), getApplicationPath()))
                        .client(getClient())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        return retrofit.create(RemoteFunctions.class);
    }
}
