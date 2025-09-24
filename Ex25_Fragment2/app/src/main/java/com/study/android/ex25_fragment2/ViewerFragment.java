package com.study.android.ex25_fragment2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewerFragment extends Fragment {
    private static final String TAG = "lecture";

    // 객체를 선언
    ImageView imageView;

    @Nullable
    @Override
    // 프래그먼트의 UI를 생성할 때 호출
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView =
                // fragment_viewer.xml 레이아웃 파일을 읽어들여 뷰 객체로 만듭니다.
                (ViewGroup) inflater.inflate(R.layout.fragment_viewer, container, false);

        // fragment_viewer.xml 레이아웃 내에 있는 imageView라는 ID를 가진 이미지 뷰를 찾아 객체를 연결합니다
        imageView = rootView.findViewById(R.id.imageView);

        // 최종적으로 생성된 뷰 그룹(rootView)을 반환합니다. 이 뷰가 화면에 그려집니다
        return rootView;
    }

    // 외부에서 호출될 수 있는 공개 메서드
    public void setImage(int resId){    // resId 매개변수는 변경할 이미지의 리소스 ID를 받습니다.
        // 전달받은 리소스 ID에 해당하는 이미지를 imageView에 설정하여 화면에 표시된 이미지를 변경합니다.
        imageView.setImageResource(resId);
    }
}