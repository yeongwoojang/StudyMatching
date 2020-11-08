package com.stuty.studymatching.RTROFIT;

import com.google.gson.annotations.SerializedName;

public class RecievedNumberData {

    @SerializedName("recievedNumber")
    private int recievedNumber;

    public RecievedNumberData(int recievedNumber) {
        this.recievedNumber = recievedNumber;
    }
}
