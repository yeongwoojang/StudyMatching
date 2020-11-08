package com.stuty.studymatching.ACTIVITY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.stuty.studymatching.ADAPTER.RequestAdapter;
import com.stuty.studymatching.OBJECT.RecievedData;
import com.stuty.studymatching.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RequestListActivity extends AppCompatActivity {

    private ImageButton backBt;
    private RecyclerView recyclerView;
    private RequestAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private List<RecievedData> requestList = new ArrayList<RecievedData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        Intent intent = getIntent();
        backBt = (ImageButton)findViewById(R.id.back_bt);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        //mainPage에서 받은 요청목록을 받는 부분
        if(intent.getStringExtra("jsonArray")!=null){
            Gson gson = new Gson();
            try {
                JSONArray jsonArray = new JSONArray(intent.getStringExtra("jsonArray"));
                for(int i=0;i<jsonArray.length();i++){
                    requestList.add(gson.fromJson(jsonArray.get(i).toString(), RecievedData.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RequestAdapter(requestList,this);
        recyclerView.setAdapter(adapter);

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
    }
}