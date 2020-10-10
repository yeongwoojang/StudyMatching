package com.stuty.studymatching.FRAGMENT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.stuty.studymatching.R;

public class BoardPage_Main extends Fragment {
    private TabLayout tabLayout;


    private final String[] tabNames = {"전체", "토익", "취업", "IT", "교양", "기타"};

    public BoardPage_Main newInstance() {
        return new BoardPage_Main();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_boardpage_main, container, false);
        tabLayout = rootView.findViewById(R.id.tabs);
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
    }
}