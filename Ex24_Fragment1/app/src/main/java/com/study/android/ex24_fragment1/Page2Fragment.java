package com.study.android.ex24_fragment1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Page2Fragment extends Fragment {
    private static final String TAG = "lecture";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.page2_fragment, container, false);

        Button button = rootView.findViewById(R.id.button2);
        button.setOnClickListener(v -> {
            MainActivity activity = (MainActivity) getActivity();
            activity.onFragmentChange(1);
        });

        return rootView;
    }
}