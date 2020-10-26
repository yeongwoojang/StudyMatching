package com.stuty.studymatching.SERVER;

import android.util.Log;

import com.stuty.studymatching.RTROFIT.AddressData;
import com.stuty.studymatching.RTROFIT.AddressResponse;
import com.stuty.studymatching.RTROFIT.AreaResponse;
import com.stuty.studymatching.RTROFIT.CommentData;
import com.stuty.studymatching.RTROFIT.EntireBoardResponse;
import com.stuty.studymatching.RTROFIT.ReadCommentResponse;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.RTROFIT.WriteCommentResponse;
import com.stuty.studymatching.RTROFIT.WriteData;
import com.stuty.studymatching.RTROFIT.WriteResponse;
import com.stuty.studymatching.RTROFIT.WritingNumberData;

import org.json.JSONArray;
import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestForBoard {

    private static final String TAG = "WriteToBoard";

    private RequestForBoardListener listener;
    private ServiceApi service;

    public RequestForBoard(ServiceApi service) {
        this.service = service;
    }

    public void writeToBoard(WriteData data){
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

    public void writeComment(CommentData data){
        service.writeComment(data).enqueue(new Callback<WriteCommentResponse>() {
            @Override
            public void onResponse(Call<WriteCommentResponse> call, Response<WriteCommentResponse> response) {
                WriteCommentResponse result = response.body();
                if(result.getCode()==200){
                    Log.d(TAG,"댓글을 등록했습니다");
                }else{
                    Log.d(TAG,"댓글을 등록하지 못했습니다.");
                }
            }

            @Override
            public void onFailure(Call<WriteCommentResponse> call, Throwable t) {

            }
        });
    }


    public void getBoardInfo(AddressData data){
        service.getAddressBoard(data).enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                AddressResponse result = response.body();
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
            public void onFailure(Call<AddressResponse> call, Throwable t) {

            }
        });

    }

    public void getComments(WritingNumberData data){
        service.getComments(data).enqueue(new Callback<ReadCommentResponse>() {
            @Override
            public void onResponse(Call<ReadCommentResponse> call, Response<ReadCommentResponse> response) {
                ReadCommentResponse result = response.body();
                if(result.getCode()==200){
                    try {
                        JSONArray jsonArray = new JSONArray(result.getJsonArray());
                        listener.getInfo(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<ReadCommentResponse> call, Throwable t) {
                Log.d("errer","실패");
            }
        });
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
        service.getEntireWriting().enqueue(new Callback<EntireBoardResponse>() {
            @Override
            public void onResponse(Call<EntireBoardResponse> call, Response<EntireBoardResponse> response) {
                EntireBoardResponse result = response.body();
                if(result.getCode()==200){
                    try {
                        JSONArray jsonArray = new JSONArray(result.getJsonArray());
                        listener.getInfo(jsonArray);
                        Log.d("writingArr",jsonArray+"");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<EntireBoardResponse> call, Throwable t) {

            }
        });
    }


    public interface RequestForBoardListener{
        void getInfo(JSONArray jsonArray) throws JSONException;
        void getArea(JSONArray jsonArray) throws JSONException;
    }
    public void setListener(RequestForBoardListener listener){
        this.listener = listener;
    }
}
