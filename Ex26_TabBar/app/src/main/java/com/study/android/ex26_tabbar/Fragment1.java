package com.study.android.ex26_tabbar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment1 extends Fragment {
    private static final String TAG = "lecture";

    @Override
    // 이 메서드는 프래그먼트의 **UI(사용자 인터페이스)**를 생성하고 반환하는 역할을 합니다.
    // 프래그먼트가 화면에 나타날 때 호출됩니다.
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // LayoutInflater 객체를 이용해 fragment_1.xml 레이아웃 파일을 메모리에 로드하여 뷰(View) 객체로 만듭니다.
        // container는 이 프래그먼트가 삽입될 부모 뷰 그룹을 의미합니다.
        // false는 생성된 뷰를 container에 즉시 추가하지 않겠다는 뜻입니다.
        // 이 작업은 프래그먼트 매니저가 자동으로 처리합니다.)
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);

        // fragment_1.xml 레이아웃 안에 있는 button1 ID를 가진 버튼을 찾아 객체에 연결합니다.
        Button button = rootView.findViewById(R.id.button1);
        button.setOnClickListener(v -> {
            Log.d(TAG, "Fragment1"); // 디버깅 확인
        });

        // 최종적으로 생성된 뷰 그룹(rootView)을 반환합니다. 이 뷰가 화면에 그려지게 됩니다.
        return rootView;
    }
}