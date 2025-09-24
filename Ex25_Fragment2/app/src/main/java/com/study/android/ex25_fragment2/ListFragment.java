package com.study.android.ex25_fragment2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// ListFragment에 대한 정의입니다. 이 프래그먼트는 화면에 **리스트 뷰(ListView)**를 표시하고,
// 사용자가 리스트 항목을 선택하면 **메인 액티비티(MainActivity)**와 소통하여 이미지를 바꾸는 기능을 구현합니다.
public class ListFragment extends Fragment {
    private static final String TAG = "lecture";

    // 리스트 뷰에 표시될 텍스트들, 즉 "첫번째 이미지", "두번째 이미지", "세번째 이미지"를 저장하는 배열입니다.
    String[] values = {"첫번째 이미지", "두번째 이미지", "세번째 이미지"};

    // 콜백(Callback) 패턴을 사용하기 위한 인터페이스 변수입니다.
    // 이 콜백을 통해 프래그먼트는 자신이 속한 액티비티에 특정 이벤트(리스트 항목 선택)를 알릴 수 있습니다.
    public ImageSelectionCallback callback;

    @Override
    // 액티비티에 붙을 때 호출되는 생명주기 메서드
    public void onAttach(Context context) {
        super.onAttach(context);

        // 액티비티가 ImageSelectionCallback 인터페이스를 구현했는지 확인합니다.
        if (context instanceof ImageSelectionCallback){
            // 만약 구현했다면, callback 변수에 해당 액티비티를 할당하여
            // 프래그먼트와 액티비티 간의 통신 채널을 연결합니다.
            callback = (ImageSelectionCallback) context;
        }
    }

    @Nullable
    @Override
    // 프래그먼트의 UI를 만드는 곳
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView =
                // inflater.inflate(R.layout.fragment_list, ...):
                //      fragment_list.xml 레이아웃 파일을 인플레이트(inflate)하여 뷰 객체로 만듭니다.
                (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);

        // 레이아웃에서 리스트 뷰를 찾아 객체를 얻습니다.
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        // values 배열의 데이터를 리스트 뷰에 보여줄 **어댑터(Adapter)**를 생성합니다.
        // simple_list_item_1는 안드로이드가 기본으로 제공하는 간단한 리스트 아이템 레이아웃입니다.
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getContext(), android.R.layout.simple_list_item_1, values);
        // 리스트 뷰에 어댑터를 연결하여 데이터를 화면에 표시하게 합니다.
        listView.setAdapter(adapter);

        // 리스트 아이템이 클릭되었을 때의 동작을 정의
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // 아이템이 클릭되면 이 메서드가 호출
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 연결된 콜백 객체가 있는지 확인
                if (callback != null){
                    // 콜백 객체의 onImageSelected 메서드를 호출하여,
                    // 선택된 아이템의 위치(position)를 액티비티에 전달합니다.
                    callback.onImageSelected(position);
                }
            }
        });

        // 최종적으로 생성된 뷰 그룹(rootView)을 반환합니다. 이 뷰가 화면에 그려집니다
        return rootView;
    }
}