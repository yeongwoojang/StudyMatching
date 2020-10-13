package com.stuty.studymatching.SERVER;

import android.util.Log;

import com.stuty.studymatching.RTROFIT.InfoData;
import com.stuty.studymatching.RTROFIT.InfoResponse;
import com.stuty.studymatching.RTROFIT.ServiceApi;

import org.json.JSONArray;
import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisibleBoard {
    private ServiceApi service;
    private GetBoardInfoListener listener;
    public VisibleBoard(ServiceApi service) {
        this.service = service;
    }

    public void getBoardInfo(InfoData data){
        service.getWritingInfo(data).enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                InfoResponse result = response.body();
                if(result.getCode()==200){
                    Log.d("response","글목록 불러오기 성공");

                    try {
                        JSONArray jsonArray = new JSONArray(result.getJsonArray());
                        listener.getInfo(jsonArray);
                        Log.d("boardRead",jsonArray+"");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Log.d("response","글목록 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {

            }
        });

    }

    public interface GetBoardInfoListener{
        void getInfo(JSONArray jsonArray) throws JSONException;
    }
    public void setListener(VisibleBoard.GetBoardInfoListener listener){
        this.listener = listener;
    }
}
