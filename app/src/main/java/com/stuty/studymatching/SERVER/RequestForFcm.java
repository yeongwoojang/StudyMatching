package com.stuty.studymatching.SERVER;

import android.util.Log;
import android.widget.Toast;

import com.stuty.studymatching.RTROFIT.FcmData;
import com.stuty.studymatching.RTROFIT.FcmResponse;
import com.stuty.studymatching.RTROFIT.ReceivedDataResponse;
import com.stuty.studymatching.RTROFIT.RecievedUserData;
import com.stuty.studymatching.RTROFIT.SendData;
import com.stuty.studymatching.RTROFIT.SendResponse;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.RTROFIT.UpdateTokenData;
import com.stuty.studymatching.RTROFIT.UpdateTokenResponse;

import org.json.JSONArray;
import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestForFcm {

    private static final String TAG = "FCM";
//    private JSONArray jsonArray;
    ServiceApi service;
    private RequestForFcmListener listener;
    public RequestForFcm(ServiceApi service) {
        this.service = service;
    }

    public void sendFCM(FcmData data){
        service.sendFcm(data).enqueue(new Callback<FcmResponse>() {
            @Override
            public void onResponse(Call<FcmResponse> call, Response<FcmResponse> response) {
                Log.d("fcm","SUCCESS!");
            }

            @Override
            public void onFailure(Call<FcmResponse> call, Throwable t) {

            }
        });
    }

    public void updateDeviceToken(UpdateTokenData data){
        service.updateDeviceToken(data).enqueue(new Callback<UpdateTokenResponse>() {
            @Override
            public void onResponse(Call<UpdateTokenResponse> call, Response<UpdateTokenResponse> response) {
                UpdateTokenResponse result = response.body();
                if(result.getCode()==200){
                    Log.d("UpdateToken","디바이스 토큰을 업데이트 했습니다.");
                }
            }

            @Override
            public void onFailure(Call<UpdateTokenResponse> call, Throwable t) {

            }
        });
    }

    public void getRecievedData(RecievedUserData data){
        service.getRecievedData(data).enqueue(new Callback<ReceivedDataResponse>() {
            @Override
            public void onResponse(Call<ReceivedDataResponse> call, Response<ReceivedDataResponse> response) {
                ReceivedDataResponse result = response.body();
                if(result.getCode()==200){
                    try {
                       JSONArray jsonArray = new JSONArray(result.getJsonArray());
                        Log.d("gfhdfghfgdh",jsonArray+"");
                        listener.getRecievedData(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReceivedDataResponse> call, Throwable t) {

            }
        });
    }

    public void sendData(SendData data){
        service.sendData(data).enqueue(new Callback<SendResponse>() {
            @Override
            public void onResponse(Call<SendResponse> call, Response<SendResponse> response) {
                SendResponse result = response.body();
                if(result.getCode()==200){
                    Log.d(TAG,"Fcm데이타 등록 성공");
                }else{
                    Log.d(TAG,"Fcm데이타 등록 실패");
                }
            }

            @Override
            public void onFailure(Call<SendResponse> call, Throwable t) {

            }
        });
    }
    public interface RequestForFcmListener{
        void getRecievedData(JSONArray jsonArray) throws JSONException;
    }
    public void setListener(RequestForFcmListener listener){
        this.listener = listener;
    }
}
