package com.stuty.studymatching.FRAGMENT;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.stuty.studymatching.R;


public class MyPageActivity extends Fragment {


    private ImageButton myFace,notiBtn;
    private CardView cardView;
    private ListView myPage_menu;
    private GridView menu_grid;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public MyPageActivity newInstance() {
        return new MyPageActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page_activity, container,false);//메모리를 객체 위에 올리는 작업
        myFace = view.findViewById(R.id.myface);
        notiBtn = view.findViewById(R.id.noti_btn);
        cardView = view.findViewById(R.id.cardview);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}