package com.stuty.studymatching.RTROFIT;


import com.google.gson.annotations.SerializedName;

public class CheckData {
    @SerializedName("signInMethod")
    String signInMethod;

    @SerializedName("userEmail")
    String userEmail;


    public CheckData(String signInMethod, String userEmail) {
        this.userEmail = userEmail;
        this.signInMethod = signInMethod;
    }
}