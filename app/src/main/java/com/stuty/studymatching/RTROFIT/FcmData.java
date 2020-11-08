package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class FcmData {

    @SerializedName("userNumber")
    private int userNumber;

    @SerializedName("writingNumber")
    private int writingNumber;

    public FcmData(int userNumber, int writingNumber) {
        this.userNumber = userNumber;
        this.writingNumber = writingNumber;
    }
}
