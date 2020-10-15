package com.stuty.studymatching.SERVER;

import android.hardware.Camera;
import android.util.Log;

import com.stuty.studymatching.RTROFIT.AreaResponse;
import com.stuty.studymatching.RTROFIT.EntireWritingResponse;
import com.stuty.studymatching.RTROFIT.ServiceApi;

import org.json.JSONArray;
import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Area {

    private ServiceApi service;
    private AreaListener listener;

    public Area(ServiceApi service) {
        this.service = service;
    }

    public void getArea(){
       service.getAreaData().enqueue(new Callback<AreaResponse>() {
           @Override
           public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
               AreaResponse result = response.body();
               if(result.getCode()==200){
                   Log.d("areaResult",result.getJsonArray());
                   try {
                       JSONArray jsonArray = new JSONArray(result.getJsonArray());
                       listener.getArea(jsonArray);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           }

           @Override
           public void onFailure(Call<AreaResponse> call, Throwable t) {

           }
       });
    }

    public void getEntireWritingData(){
        service.getEntireWriting().enqueue(new Callback<EntireWritingResponse>() {
            @Override
            public void onResponse(Call<EntireWritingResponse> call, Response<EntireWritingResponse> response) {
                EntireWritingResponse result = response.body();
                if(result.getCode()==200){
                    try {
                        JSONArray jsonArray = new JSONArray(result.getJsonArray());
                        listener.getEntireBoard(jsonArray);
                        Log.d("writingArr",jsonArray+"");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<EntireWritingResponse> call, Throwable t) {

            }
        });
    }
    public interface AreaListener{
        void getArea(JSONArray jsonArray) throws JSONException;
        void getEntireBoard(JSONArray jsonArray) throws JSONException;
    }
    public void setListener(Area.AreaListener listener){
        this.listener = listener;

    }

}
