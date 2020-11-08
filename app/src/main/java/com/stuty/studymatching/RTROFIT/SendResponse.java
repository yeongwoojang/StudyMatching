package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class SendResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
