package com.stuty.studymatching.FRAGMENT;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stuty.studymatching.ADAPTER.TypeAdapter;
import com.stuty.studymatching.R;

public class FirstCreatePage extends Fragment {

    private FirstPageListener firstPageListener;

    private Button nextBt;
    private ImageButton closeBt;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TypeAdapter typeAdapter;
    private final String[] types = {"토익","교양","프로그래밍","취업","기타"};

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        firstPageListener = (FirstPageListener) context;
    }

    public FirstCreatePage newInstance(){
        return new FirstCreatePage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first_createpage, container, false);
        nextBt = rootView.findViewById(R.id.next_bt);
        closeBt = rootView.findViewById(R.id.close_bt);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        typeAdapter = new TypeAdapter(types,getActivity());
        recyclerView.setAdapter(typeAdapter);

        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstPageListener.nextBtClick();
            }
        });

        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstPageListener.closeBtClick();
            }
        });
    }

    public interface FirstPageListener {
        void nextBtClick();
        void closeBtClick();
    }
}
