package com.stuty.studymatching.FRAGMENT;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stuty.studymatching.R;

public class SecondCreatePage extends Fragment {

    private SecondPageListener secondPageListener;

    private Button postingBt;
    private ImageButton prevBt;
    private EditText contentEdt;
    private TextView edtLength;

    public SecondCreatePage newInstance() {
        return new SecondCreatePage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second_createpage, container, false);

        postingBt = rootView.findViewById(R.id.posting_bt);
        prevBt = rootView.findViewById(R.id.prev_bt);
        contentEdt = rootView.findViewById(R.id.content_edt);
        edtLength = rootView.findViewById(R.id.edt_length);
        return rootView;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        secondPageListener = (SecondCreatePage.SecondPageListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        키보드 보이기
        InputMethodManager keyBoardManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyBoardManager.showSoftInput(contentEdt, InputMethodManager.SHOW_IMPLICIT);

        contentEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String inputText = contentEdt.getText().toString();
                edtLength.setText(inputText.length()+" / 150");
                int currentLength = inputText.length();
                if(currentLength>0){
                    postingBt.setTextColor(getActivity().getResources().getColor(R.color.black,null));
                    postingBt.setEnabled(true);

                }else{
                    postingBt.setTextColor(getActivity().getResources().getColor(R.color.gray500,null));
                    postingBt.setEnabled(false);
                }
                if(currentLength==150){
                    Toast.makeText(getActivity(), "150자 이내로 작성하세요", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        postingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Database에 게시글을 등록

                //----------------------------------------------------//

                //MainTabActivity에 게시 버튼이 눌렸음을 알리며 Drawer를 닫아주기를 요청한다.
                secondPageListener.postingBtClick();
                //키보드 숨기기
                InputMethodManager keyBoardManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                keyBoardManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });

        prevBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //키보드 숨기기
                InputMethodManager keyBoardManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                keyBoardManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                secondPageListener.prevBtClick();
                contentEdt.setText("");
            }
        });
    }

    public interface SecondPageListener{
        void postingBtClick();
        void prevBtClick();
    }
}
