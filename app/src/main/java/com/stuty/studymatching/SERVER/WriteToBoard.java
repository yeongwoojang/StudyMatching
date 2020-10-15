package com.stuty.studymatching.SERVER;

import android.util.Log;

import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.RTROFIT.UidData;
import com.stuty.studymatching.RTROFIT.UserNumberResponse;
import com.stuty.studymatching.RTROFIT.WriteData;
import com.stuty.studymatching.RTROFIT.WriteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteToBoard {
    private ServiceApi service;
    private String userNumber;


    public WriteToBoard(ServiceApi service) {
        this.service = service;
    }

    public void write(WriteData data){
        service.writeToDB(data).enqueue(new Callback<WriteResponse>() {
            @Override
            public void onResponse(Call<WriteResponse> call, Response<WriteResponse> response) {
                WriteResponse result = response.body();
                if(result.getCode()==200){
                    Log.d("result","글을 등록했습니다");
                }else{
                    Log.d("result","글등록을 실패했습니다.");
                }
            }

            @Override
            public void onFailure(Call<WriteResponse> call, Throwable t) {

            }
        });

    }

    public void getUserNumber(UidData data,String currentTime, String recruitMember, String recruitPeriod ,String part, String title, String content){
        service.checkUid(data).enqueue(new Callback<UserNumberResponse>() {
            @Override
            public void onResponse(Call<UserNumberResponse> call, Response<UserNumberResponse> response) {
                UserNumberResponse result = response.body();
                Log.d("result",result.getCode()+"");
                if(result.getCode()==200){
                    Log.d("result","userNumber얻어오기 성공");
                    userNumber = result.getUserNumber();
                    write(new WriteData(userNumber,currentTime,recruitMember,recruitPeriod,part,title,content));
                }else{
                    Log.d("result","userNumber얻어오기 실패");
                }
            }

            @Override
            public void onFailure(Call<UserNumberResponse> call, Throwable t) {
                Log.d("result","에러");
            }
        });
    }


}
