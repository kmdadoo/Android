package com.study.android.ex24_fragment1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

//  **프래그먼트(Fragment)**를 정의하는 부분입니다.
//  여기서 정의된 Page1Fragment는 앱의 화면 일부를 구성하며,
//  특정 버튼을 눌렀을 때 메인 액티비티에 있는 다른 프래그먼트로 화면을 전환하는 역할을 합니다.
public class Page1Fragment extends Fragment {
    private static final String TAG = "lecture";

    @Nullable
    @Override
    // 이 메서드는 프래그먼트의 UI를 그릴 때 호출되는 가장 중요한 부분입니다.
    // 여기서 프래그먼트가 보여줄 레이아웃을 정의합니다.
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // inflater.inflate(R.layout.page1_fragment, container, false):
        //      inflater 객체를 사용하여 page1_fragment.xml 파일을 읽어들여 **뷰(View)**로 만듭니다.
        // container는 이 프래그먼트가 삽입될 부모 뷰 그룹을 의미합니다.
        // false는 inflater가 생성한 뷰를 container에 즉시 추가하지 않겠다는 뜻입니다.
        // (이 작업은 프래그먼트 매니저가 자동으로 처리합니다.)
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.page1_fragment, container, false);

        // page1_fragment.xml 레이아웃 안에 있는 button2라는 ID를 가진 버튼을 찾습니다.
        Button button = rootView.findViewById(R.id.button2);
        // 클릭되었을 때 실행될 동작을 정의
        button.setOnClickListener(v -> {
            // 프래그먼트가 속해 있는 메인 액티비티(MainActivity)의 인스턴스를 가져옵니다.
            // 프래그먼트가 액티비티와 통신하기 위해 반드시 필요한 과정입니다.
            MainActivity activity = (MainActivity) getActivity();
            // 메인 액티비티에 정의된 onFragmentChange() 메서드를 호출하여,
            // 화면을 0번 인덱스에 해당하는 다른 프래그먼트로 전환하도록 요청합니다.
            activity.onFragmentChange(0);
        });

        // 최종적으로 생성된 뷰 그룹(rootView)을 반환합니다. 이 뷰가 화면에 그려집니다
        return rootView;
    }
}
// 이 프래그먼트는 page1_fragment 레이아웃을 화면에 보여주고,
// 사용자가 버튼을 클릭하면 메인 액티비티에 있는 메서드를 호출해 다른 화면으로 전환하는 역할을 수행합니다.