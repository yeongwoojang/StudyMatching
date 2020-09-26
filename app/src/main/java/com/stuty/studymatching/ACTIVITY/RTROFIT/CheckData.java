package com.stuty.studymatching.ACTIVITY.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class CheckData {
    @SerializedName("userToken")
    String userToken;


    public CheckData(String userToken) {
        this.userToken = userToken;
    }
}