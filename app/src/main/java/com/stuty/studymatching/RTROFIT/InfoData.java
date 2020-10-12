package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class InfoData {
    @SerializedName("address")
    String address;

    public InfoData(String address) {
        this.address = address;
    }
}
