package com.stuty.studymatching.FRAGMENT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.stuty.studymatching.ACTIVITY.SearchAddress;
import com.stuty.studymatching.ADAPTER.TypeAdapter;
import com.stuty.studymatching.R;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.RTROFIT.UidData;
import com.stuty.studymatching.SERVER.WriteToBoard;

import static android.app.Activity.RESULT_OK;

public class FirstCreatePage extends Fragment{

    private FirebaseAuth mAuth;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private FirstPageListener firstPageListener;
    private ServiceApi service;
    private Context mContext;

    private TextView partText;
    private Button postingBt,recruitBt,text;
    private ImageButton closeBt;
    private RecyclerView recyclerView;
    private EditText titleEdt,contentEdt;
    private TextView edtLength,recruitText,recruitPeroidText;


    String pCount,pDays;
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
        postingBt = rootView.findViewById(R.id.posting_bt);
        text = rootView.findViewById(R.id.test);
        closeBt = rootView.findViewById(R.id.close_bt);
        recruitBt = rootView.findViewById(R.id.recruit_Bt);
        titleEdt = rootView.findViewById(R.id.title_edt);
        contentEdt = rootView.findViewById(R.id.content_edt);
        edtLength = rootView.findViewById(R.id.edt_length);
        partText = rootView.findViewById(R.id.part_text);
        recruitPeroidText = rootView.findViewById(R.id.recruit_peroid_text);
        recruitText = rootView.findViewById(R.id.recruit_text);
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

        service = RetrofitClient.getClient().create(ServiceApi.class);
        WriteToBoard writeToBoard = new WriteToBoard(service);



        //키보드 보이기
        InputMethodManager keyBoardManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyBoardManager.showSoftInput(titleEdt, InputMethodManager.SHOW_IMPLICIT);

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        typeAdapter = new TypeAdapter(types,getActivity());
        recyclerView.setAdapter(typeAdapter);

        typeAdapter.setListener(new TypeAdapter.TypeAdapterListener() {
            @Override
            public void updateType(String typeValue) {
                partText.setText(typeValue);
            }
        });




        recruitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recruit_CheckBox dialog = Recruit_CheckBox.newInstance();
                dialog.setCancelable(false);
                dialog.setListener(new Recruit_CheckBox.DialogListener() {
                    @Override
                    public void cencelBtClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void okButtonClick(String count,String days) {
                        pCount = count;
                        pDays = days;
                        if(pCount.equals("무관")){
                            recruitText.setText("모집인원 : "+pCount);
                        }else{
                            recruitText.setText("모집인원 : "+pCount+"명");
                        }
                        recruitPeroidText.setText("모집기간 : "+pDays+"일");
                        dialog.dismiss();
                    }
                });
                dialog.show(getActivity().getSupportFragmentManager(),dialog.TAG_EVENT_DIALOG);
            }
        });


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

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchAddress.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
            }
        });

        postingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Database에 게시글을 등록
                if(partText.getText().toString().equals("PART")||pCount.equals("0")||pDays.equals("")){
                    Toast.makeText(getActivity(),"조건을 정확히 선택하세요",Toast.LENGTH_SHORT).show();
                }else{
                    mAuth = FirebaseAuth.getInstance();
                    writeToBoard.getUserNumber(new UidData(mAuth.getCurrentUser().getUid()),
                            pCount,pDays,partText.getText().toString(),titleEdt.getText().toString(),contentEdt.getText().toString());

                    //----------------------------------------------------//

                    //MainTabActivity에 게시 버튼이 눌렸음을 알리며 Drawer를 닫아주기를 요청한다.
                    firstPageListener.postingBtClick();
                    //키보드 숨기기
                    InputMethodManager keyBoardManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    keyBoardManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    titleEdt.setText("");
                    contentEdt.setText("");
                }
            }
        });

        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstPageListener.closeBtClick();

                //키보드 숨기기
                InputMethodManager keyBoardManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                keyBoardManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }


    public interface FirstPageListener {
        void postingBtClick();
        void closeBtClick();
    }
}
