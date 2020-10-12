package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class InfoResponse {
    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
