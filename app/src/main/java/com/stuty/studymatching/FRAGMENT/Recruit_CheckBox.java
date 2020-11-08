package com.stuty.studymatching.FRAGMENT;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.stuty.studymatching.R;

public class Recruit_CheckBox extends DialogFragment {

    public static final String TAG_EVENT_DIALOG = "dialog_event";

    private DialogListener listener;

    private RadioGroup radioGroup1,radioGroup2;


    private TextView cancelBt, okBt;

    private String count="0";
    private String days = "0";

    public static Recruit_CheckBox newInstance(){
        return new Recruit_CheckBox();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        listener = (Recruit_CheckBox.DialogListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_recruit_checkbox, container, false);
        radioGroup1 = rootView.findViewById(R.id.radio_group1);
        radioGroup2 = rootView.findViewById(R.id.radio_group2);
//        radioButton1 = rootView.findViewById(R.id.no_relation);
//        radioButton3 = rootView.findViewById(R.id.three);
//        radioButton4 = rootView.findViewById(R.id.four);
//        radioButton5 = rootView.findViewById(R.id.five);
//        radioButton6 = rootView.findViewById(R.id.six);
//        radioButton7 = rootView.findViewById(R.id.seven);
//        radioButton8 = rootView.findViewById(R.id.eight);
//        radioButton9 = rootView.findViewById(R.id.nine);
//        radioButton10 = rootView.findViewById(R.id.ten);
        cancelBt = rootView.findViewById(R.id.cancle_bt);
        okBt = rootView.findViewById(R.id.ok_bt);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.no_relation :
                        count = "무관";
                        break;
                    case R.id.three :
                        count ="3";
                        break;
                    case R.id.four :
                        count ="4";
                        break;
                    case R.id.five :
                        count ="5";
                        break;
                    case R.id.six :
                        count ="6";
                        break;
                    case R.id.seven :
                        count ="7";
                        break;
                    case R.id.eight :
                        count ="8";
                        break;
                    case R.id.nine :
                        count ="9";
                        break;
                    case R.id.ten :
                        count ="10";
                        break;
                    default: count ="";
                    break;
                }
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case  R.id.three_days :
                        days = "3";
                        break;
                    case  R.id.seven_days :
                        days = "7";
                        break;
                    case  R.id.ten_days :
                        days = "10";
                        break;
                    case  R.id.fourteen_days :
                        days = "14";
                        break;
                    default: days = "";
                    break;
                }
            }
        });
        okBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.okButtonClick(count,days);
            }
        });

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cencelBtClick();
            }
        });
    }

    interface DialogListener{
        void cencelBtClick();
        void okButtonClick(String count,String days);
    }
    public void setListener(Recruit_CheckBox.DialogListener listener) {
        this.listener = listener;
    }
}
