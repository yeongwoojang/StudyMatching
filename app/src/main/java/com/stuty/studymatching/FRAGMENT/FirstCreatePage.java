package com.stuty.studymatching.FRAGMENT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stuty.studymatching.ACTIVITY.SearchAddress;
import com.stuty.studymatching.ADAPTER.TypeAdapter;
import com.stuty.studymatching.R;

import static android.app.Activity.RESULT_OK;

public class FirstCreatePage extends Fragment implements CustomDatePicker.DialogListener {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    public static final String datePicker = "DATAPICKER";
    private FirstPageListener firstPageListener;
    private Context mContext;

    private TextView typeText;
    private Button nextBt,text;
    private ImageButton closeBt;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TypeAdapter typeAdapter;
    private final String[] types = {"토익","교양","프로그래밍","취업","기타"};

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("execute","onAttach");
        mContext = context;
        firstPageListener = (FirstPageListener) context;

    }

    public FirstCreatePage newInstance(){
        return new FirstCreatePage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("execute","onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_first_createpage, container, false);
        nextBt = rootView.findViewById(R.id.next_bt);
        text = rootView.findViewById(R.id.test);
        closeBt = rootView.findViewById(R.id.close_bt);
        typeText = rootView.findViewById(R.id.type_text);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        Log.d("dghdfhdfh",data);
                    }
                }
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("execute","onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        typeAdapter = new TypeAdapter(types,getActivity());
        recyclerView.setAdapter(typeAdapter);
        typeAdapter.setListener(new TypeAdapter.TypeAdapterListener() {
            @Override
            public void updateType(String typeValue) {
                typeText.setText("분야 : "+typeValue);
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchAddress.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
            }
        });

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
    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);

        Toast.makeText(getActivity(),"Date: "+dateMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void result(int year, int month, int day) {
        processDatePickerResult(year,month,day);
    }



    public interface FirstPageListener {
        void nextBtClick();
        void closeBtClick();
    }
}
