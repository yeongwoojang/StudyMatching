package com.stuty.studymatching.RTROFIT;


import com.google.gson.annotations.SerializedName;

public class CheckData {
    @SerializedName("signInMethod")
    String signInMethod;

    @SerializedName("uid")
    String uid;


    public CheckData(String signInMethod, String uid) {
        this.signInMethod = signInMethod;
        this.uid = uid;
    }
}