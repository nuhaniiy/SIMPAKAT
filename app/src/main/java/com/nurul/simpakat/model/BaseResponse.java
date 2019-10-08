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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }
}
