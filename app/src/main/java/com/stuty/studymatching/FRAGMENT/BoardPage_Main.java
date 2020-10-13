package com.stuty.studymatching.FRAGMENT;

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
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.stuty.studymatching.ADAPTER.BoardAdapter;
import com.stuty.studymatching.ADAPTER.TypeAdapter;
import com.stuty.studymatching.OBJECT.Writing;
import com.stuty.studymatching.R;
import com.stuty.studymatching.RTROFIT.InfoData;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.SERVER.VisibleBoard;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class BoardPage_Main extends Fragment implements VisibleBoard.GetBoardInfoListener{
    private TabLayout tabLayout;
    private ImageButton writeBt;
    private TextView areaText;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BoardAdapter adapter;
    private BoardPageListener listener;
    private final String[] tabNames = {"전체", "토익", "취업", "IT", "교양2", "기타"};

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
        Bundle bundle = getArguments();
        address = bundle.getString("address");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_boardpage_main, container, false);
        tabLayout = rootView.findViewById(R.id.tabs);
        writeBt = rootView.findViewById(R.id.write_bt);
        areaText = rootView.findViewById(R.id.area_text);
        recyclerView = rootView.findViewById(R.id.board_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 0; i < tabNames.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
            TextView view1 = new TextView(getActivity());
            view1.setText(tabNames[i]);
            view1.setGravity(tabLayout.GRAVITY_CENTER);
            view1.setTextColor(getActivity().getResources().getColor(R.color.black, null));
            tabLayout.getTabAt(i).setCustomView(view1);
        }
        areaText.setText(address);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
        VisibleBoard visibleBoard = new VisibleBoard(service);
        visibleBoard.setListener((VisibleBoard.GetBoardInfoListener)this);
        visibleBoard.getBoardInfo(new InfoData(address));


        recyclerView.setLayoutManager(linearLayoutManager);



        writeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.writeBtClick();
            }
        });
    }

    @Override
    public void getInfo(JSONArray jsonArray) throws JSONException {
        Gson gson = new Gson();
        for(int i=0;i<jsonArray.length();i++){
            boardList.add(gson.fromJson(jsonArray.get(i).toString(),Writing.class));
            adapter = new BoardAdapter(boardList);
            recyclerView.setAdapter(adapter);
        }
    }

    public interface BoardPageListener{
        void writeBtClick();
    }

    public void setListener(BoardPage_Main.BoardPageListener listener){
        this.listener = listener;
    }
}