package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class EntireBoardResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("jsonArray")
    private String jsonArray;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getJsonArray() {
        return jsonArray;
    }
}
