package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class RecievedUserData {

    @SerializedName("userNumber")
    private int userNumber;

    public RecievedUserData(int userNumber) {
        this.userNumber = userNumber;
    }
}
