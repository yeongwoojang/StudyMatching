package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class UpdateTokenData {
    @SerializedName("deviceToken")
    private String deviceToken;

    @SerializedName("userNumber")
    private int userNumber;

    public UpdateTokenData(String deviceToken, int userNumber) {
        this.deviceToken = deviceToken;
        this.userNumber = userNumber;
    }
}
