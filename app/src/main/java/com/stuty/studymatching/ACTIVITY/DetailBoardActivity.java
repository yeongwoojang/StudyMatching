package com.stuty.studymatching.ACTIVITY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.stuty.studymatching.ADAPTER.CommentAdapter;
import com.stuty.studymatching.OBJECT.Comment;
import com.stuty.studymatching.OBJECT.Writing;
import com.stuty.studymatching.R;
import com.stuty.studymatching.RTROFIT.CommentData;
import com.stuty.studymatching.RTROFIT.FcmData;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.SendData;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.RTROFIT.WritingNumberData;
import com.stuty.studymatching.SERVER.RequestForFcm;
import com.stuty.studymatching.SERVER.RequestForBoard;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailBoardActivity extends AppCompatActivity implements RequestForBoard.RequestForBoardListener {

    private Context mContext;

    private TextView toolbarTitle, writerView, titleView, contentView;
    private ImageButton applyBt, commentsBt, backBt;
    private EditText commentEdt;
    private RecyclerView recyclerView;

    private CommentAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ServiceApi service;


    private List<Comment> commentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_board);

        mContext = this;

        Intent intent = getIntent();
        String writer = intent.getStringExtra("writer");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        int writingNumber = intent.getIntExtra("writingNumber", 0);

        toolbarTitle = (TextView) findViewById(R.id.toolbar_writing_title);
        writerView = (TextView) findViewById(R.id.writer);
        titleView = (TextView) findViewById(R.id.title);
        contentView = (TextView) findViewById(R.id.content);
        applyBt = (ImageButton) findViewById(R.id.apply_bt);
        backBt = (ImageButton) findViewById(R.id.back_bt);
        commentsBt = (ImageButton) findViewById(R.id.comments_bt);
        commentEdt = (EditText) findViewById(R.id.comments_edit);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        toolbarTitle.setText(title);
        writerView.setText(writer);
        titleView.setText(title);
        contentView.setText(content);

        service = RetrofitClient.getClient().create(ServiceApi.class);
        RequestForBoard requestForBoard = new RequestForBoard(service);
        requestForBoard.setListener(this);
        requestForBoard.getComments(new WritingNumberData(writingNumber));

        applyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                int FcmRecipientNumber = intent.getIntExtra("FcmRecipientNumber",0);
                int senderNumber = intent.getIntExtra("senderNumber",0);
                String sender = intent.getStringExtra("sender");
                int writingNumber = intent.getIntExtra("writingNumber",0);
                Writing receivedUserInfo = (Writing)intent.getSerializableExtra("recipient");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                Calendar calendar = Calendar.getInstance();
                String requestTime = format.format(calendar.getTime());
                RequestForFcm requestForFcm = new RequestForFcm(service);
                requestForFcm.sendData(new SendData(receivedUserInfo,senderNumber,sender,requestTime));
                requestForFcm.sendFCM(new FcmData(FcmRecipientNumber,writingNumber));
            }
        });

        commentsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                int userNumber = intent.getIntExtra("senderNumber",0);
                Log.d("userNumber3",userNumber+"");
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                String commentWriter = mAuth.getCurrentUser().getDisplayName();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String currentTime = simpleDateFormat.format(calendar.getTime());
                requestForBoard.writeComment(new CommentData(writingNumber,userNumber, commentWriter, commentEdt.getText().toString(), currentTime));
                commentList.add(new Comment(userNumber,commentWriter, commentEdt.getText().toString(), currentTime));
                commentEdt.setText("");
                adapter = new CommentAdapter(mContext, commentList);
                recyclerView.setAdapter(adapter);
            }
        });

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getInfo(JSONArray jsonArray) throws JSONException {
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            commentList.add(gson.fromJson(jsonArray.get(i).toString(), Comment.class));
            adapter = new CommentAdapter(mContext, commentList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void getArea(JSONArray jsonArray) throws JSONException {

    }
}