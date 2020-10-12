package com.stuty.studymatching.SERVER;

import android.util.Log;

import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.RTROFIT.UserData;
import com.stuty.studymatching.RTROFIT.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInDatabase {
    private ServiceApi service;


    public UserInDatabase(ServiceApi service) {
        this.service = service;
    }

    public void getUser(UserData data){
        service.getUserInfo(data).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse result = response.body();
                if(result.getCode()==200){
                    Log.d("디비연동 테스트","success");
                    Log.d("디비연동 테스트",result.getObject());
                }else{
                    Log.d("디비연동 테스트","failed");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
}
