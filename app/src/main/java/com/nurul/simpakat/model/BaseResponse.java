package com.nurul.simpakat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public abstract class BaseResponse {
    @Expose
    @SerializedName("error")
    private String error;

    @Expose
    @SerializedName("resultCode")
    private String resultCode;

    @Expose
    @SerializedName("messageText")
    private String reasonText;
}
