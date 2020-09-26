package com.stuty.studymatching.ACTIVITY.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class CheckResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private Boolean message;


    public int getCode() {
        return code;
    }

    public Boolean getMessage() {
        return message;
    }

}
