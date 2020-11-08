package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class AddressData {
    @SerializedName("address")
    String address;

    public AddressData(String address) {
        this.address = address;
    }
}

