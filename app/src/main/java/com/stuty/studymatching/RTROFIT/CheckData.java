package com.stuty.studymatching.RTROFIT;


import com.google.gson.annotations.SerializedName;

public class CheckData {
    @SerializedName("userEmail")
    String userEmail;


    public CheckData(String userEmail) {
        this.userEmail = userEmail;
    }
}