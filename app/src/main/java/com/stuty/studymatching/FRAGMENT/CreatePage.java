package com.stuty.studymatching.FRAGMENT;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stuty.studymatching.R;

public class CreatePage extends Fragment {

    EditText contentEdt;
    Button postingBt;

    public CreatePage newInstance(){return new CreatePage();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_createpage, container, false);
        contentEdt = rootView.findViewById(R.id.content_edt);
        postingBt = rootView.findViewById(R.id.posting_bt);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contentEdt.requestFocus();
        //키보드 보이게 하는 부분
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

//        키보드를 숨기게 하는 코드
//        InputMethodManager immhide = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        String content = contentEdt.getText().toString();
        if(content!=""){
            postingBt.setTextColor(getActivity().getResources().getColor(R.color.black,null));
            postingBt.setEnabled(false);
        }else{
            postingBt.setTextColor(getActivity().getResources().getColor(R.color.gray200,null));
            postingBt.setEnabled(true);

        }




    }
}
