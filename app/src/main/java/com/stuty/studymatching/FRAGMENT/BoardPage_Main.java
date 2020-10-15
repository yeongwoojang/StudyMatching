package com.stuty.studymatching.FRAGMENT;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.stuty.studymatching.ADAPTER.BoardAdapter;
import com.stuty.studymatching.ADAPTER.TypeAdapter;
import com.stuty.studymatching.OBJECT.Writing;
import com.stuty.studymatching.R;
import com.stuty.studymatching.RTROFIT.InfoData;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.SERVER.Area;
import com.stuty.studymatching.SERVER.VisibleBoard;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BoardPage_Main extends Fragment implements VisibleBoard.GetBoardInfoListener, Area.AreaListener {
    private TabLayout tabLayout;
    private ImageButton writeBt;
    private Button myAreaBt;
    private TextView areaText;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private LinearLayoutManager linearLayoutManager;
    private BoardAdapter adapter;
    private BoardPageListener listener;
    private final String[] tabNames = {"전체", "토익", "취업", "IT", "교양2", "기타"};

    List<String> areaList = new ArrayList<>();
    private ServiceApi service;
    private String address;
    private List<Writing> boardList = new ArrayList<>();
    private Context mContext;

    public static BoardPage_Main newInstance() {
        return new BoardPage_Main();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        listener = (BoardPage_Main.BoardPageListener) context;
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
        spinner = rootView.findViewById(R.id.spinner);
//        areaText = rootView.findViewById(R.id.area_text);
        myAreaBt = rootView.findViewById(R.id.myArea);
        recyclerView = rootView.findViewById(R.id.board_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = RetrofitClient.getClient().create(ServiceApi.class);
        VisibleBoard visibleBoard = new VisibleBoard(service);
        visibleBoard.setListener((VisibleBoard.GetBoardInfoListener) this);

        Area area = new Area(service);
        area.setListener((Area.AreaListener) this);
        area.getArea();
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
        ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);


        writeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.writeBtClick();
            }
        });

        myAreaBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                address = bundle.getString("address");
                Log.d("address",address);
                visibleBoard.getBoardInfo(new InfoData(address));
                spinner.setSelection(getIndex(spinner,address));
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), areaList.get(position),Toast.LENGTH_SHORT).show();
                address = spinner.getSelectedItem().toString();
                Log.d("address",address);
                if(address.equals("전체")){
                    area.getEntireWritingData();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void getInfo(JSONArray jsonArray) throws JSONException {
        boardList.clear();
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            boardList.add(gson.fromJson(jsonArray.get(i).toString(), Writing.class));
            adapter = new BoardAdapter(boardList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getArea(JSONArray jsonArray) throws JSONException {
        for(int i=0;i<jsonArray.length();i++){
            areaList.add(jsonArray.getJSONObject(i).get("address").toString());
            Log.d("array",areaList.get(i));
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_item,areaList
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setSelection(getIndex(spinner,"전체"));

//        Log.d("array",jsonArray.get(0)+"");
    }

    @Override
    public void getEntireBoard(JSONArray jsonArray) throws JSONException {
        boardList.clear();
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            boardList.add(gson.fromJson(jsonArray.get(i).toString(), Writing.class));
            adapter = new BoardAdapter(boardList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private int getIndex(Spinner spinner, String item){
        for(int i=0;i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(item)){
                return i;
            }
        }
        return 0;
    }
    public interface BoardPageListener {
        void writeBtClick();
    }

    public void setListener(BoardPage_Main.BoardPageListener listener) {
        this.listener = listener;
    }
}