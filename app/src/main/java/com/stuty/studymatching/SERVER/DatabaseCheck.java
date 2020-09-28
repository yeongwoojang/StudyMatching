package com.stuty.studymatching.SERVER;

import android.util.Log;

import com.stuty.studymatching.RTROFIT.CheckData;
import com.stuty.studymatching.RTROFIT.CheckResponse;
import com.stuty.studymatching.RTROFIT.JoinData;
import com.stuty.studymatching.RTROFIT.JoinResponse;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatabaseCheck {

    private ServiceApi service;

    public DatabaseCheck(ServiceApi service) {
        this.service = service;
    }

    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                if (result.getCode() == 200) {
                    Log.d("resultCode",result.getCode()+"");
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Log.e("회원가입 에러 발생", t.getMessage());
            }
        });
    }


     public void startCheck(CheckData data, final String userEmail, final String userName) {
        service.userCheck(data).enqueue(new Callback<CheckResponse>() {
            @Override
            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                CheckResponse result = response.body();
                Log.d("result",result.getMessage()+"");
                if(result.getMessage() ==true){
                    startJoin(new JoinData(userEmail,userName));
                }
            }

            @Override
            public void onFailure(Call<CheckResponse> call, Throwable t) {
                Log.e("체크 에러 발생", t.getMessage());
            }
        });
    }
}
