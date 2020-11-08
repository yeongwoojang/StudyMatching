package com.stuty.studymatching.FRAGMENT;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.stuty.studymatching.ACTIVITY.MainTabActivity;
import com.stuty.studymatching.ADAPTER.BoardAdapter;
import com.stuty.studymatching.OBJECT.User;
import com.stuty.studymatching.OBJECT.Writing;
import com.stuty.studymatching.R;
import com.stuty.studymatching.RTROFIT.AddressData;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.SERVER.RequestForBoard;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class BoardPage_Main extends Fragment implements RequestForBoard.RequestForBoardListener {

    private Fragment mFragment;
    private User user;
    private TabLayout tabLayout;
    private ImageButton writeBt, backBt;
    private Button myAreaBt;
    private TextView areaText;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private LinearLayoutManager linearLayoutManager;
    private BoardAdapter adapter;
    private BoardPageListener listener;
    private final String[] tabNames = {"전체", "토익", "취업", "IT", "교양2", "기타"};
    List<String> areaList = new ArrayList<>();
    private List<Writing> boardList = new ArrayList<>();

    RequestForBoard requestForBoard;
    private ServiceApi service;
    private String address;
    private Context mContext;

    public static BoardPage_Main newInstance() {
        return new BoardPage_Main();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        listener = (BoardPage_Main.BoardPageListener) context;
        if(getArguments()!=null){
            user = (User)getArguments().getSerializable("currentUserInfo");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_boardpage_main, container, false);
        tabLayout = rootView.findViewById(R.id.tabs);
        writeBt = rootView.findViewById(R.id.write_bt);
        backBt = rootView.findViewById(R.id.back_bt);
        spinner = rootView.findViewById(R.id.spinner);
//        areaText = rootView.findViewById(R.id.area_text);
        myAreaBt = rootView.findViewById(R.id.myArea);
        recyclerView = rootView.findViewById(R.id.board_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mFragment = this;
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = RetrofitClient.getClient().create(ServiceApi.class);
        requestForBoard = new RequestForBoard(service);
        requestForBoard.setListener((RequestForBoard.RequestForBoardListener) this);
        requestForBoard.getArea();

        areaList.add("전체");

        for (int i = 0; i < tabNames.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
            TextView view1 = new TextView(getActivity());
            view1.setText(tabNames[i]);
            view1.setGravity(tabLayout.GRAVITY_CENTER);
            view1.setTextColor(getActivity().getResources().getColor(R.color.black, null));
            tabLayout.getTabAt(i).setCustomView(view1);
        }
        recyclerView.setLayoutManager(linearLayoutManager);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        writeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.writeBtClick();
            }
        });

        myAreaBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestForBoard.getBoardInfo(new AddressData(user.getAddress()));
                spinner.setSelection(getIndex(spinner, user.getAddress()));
            }
        });

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.backBtClick(mFragment);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                address = spinner.getSelectedItem().toString();
                if (address.equals("전체")) {
                    requestForBoard.getEntireWritingData();
                } else {
                    requestForBoard.getBoardInfo(new AddressData(address));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void getArea(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            areaList.add(jsonArray.getJSONObject(i).get("address").toString());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, areaList
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(getIndex(spinner, "전체"));

    }
    @Override
    public void getInfo(JSONArray jsonArray) throws JSONException {
        boardList.clear();
        Gson gson = new Gson();
        if(jsonArray.length()>0){
            for (int i = 0; i < jsonArray.length(); i++) {
                boardList.add(gson.fromJson(jsonArray.get(i).toString(), Writing.class));
                Log.d("boardList",boardList.get(i).getTitle());
                adapter = new BoardAdapter(mContext, boardList, user.getUserNumber(),user.getUserName());
                recyclerView.setAdapter(adapter);
            }
        }else{
            adapter = new BoardAdapter(mContext, boardList, user.getUserNumber(),user.getUserName());
            recyclerView.setAdapter(adapter);
        }


    }
    private int getIndex(Spinner spinner, String item) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(item)) {
                return i;
            }
        }
        return 0;
    }


    public interface BoardPageListener {
        void writeBtClick();
        void backBtClick(Fragment fragment);
    }

    public void setListener(BoardPage_Main.BoardPageListener listener) {
        this.listener = listener;
    }
}