package com.study.android.ex15_list1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

//  **ListView**를 사용하여 목록을 화면에 표시하는 방법을 보여주는 예제입니다.
//  ListView는 스크롤 가능한 목록 형태의 UI를 만들 때 사용되는 위젯입니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    String[] names = { "홍길동", "강간찬", "을지문덕", "양만춘", "유관순"}; // 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // String 타입의 데이터를 다루는 ArrayAdapter 객체를 선언합니다.
        ArrayAdapter<String> adapter;
        // ArrayAdapter를 생성
        // this: 현재 액티비티의 컨텍스트(맥락)를 의미합니다.
        // ndroid.R.layout.simple_list_item_1: 안드로이드에서 기본으로 제공하는 간단한
        // 리스트 항목의 디자인 레이아웃입니다. 항목 하나에 텍스트만 표시합니다.
        // names: 위에서 정의한 문자열 배열을 어댑터에 전달하여 리스트뷰에 항목들이 표시되도록 합니다.
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        //  XML 레이아웃 파일(activity_main.xml)에 있는 listView1 위젯과 코드를 연결합니다.
        ListView listView = findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        // 사용자가 리스트뷰의 특정 항목을 클릭했을 때 발생하는 이벤트를 처리하기 위한 리스너(Listener)를 설정합니다.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 항목이 클릭되었을 때 호출되는 메서드입니다.
            @Override
            // position: 클릭된 항목의 인덱스 번호(위치)를 의미합니다. 0부터 시작합니다.
            public void onItemClick(AdapterView<?> arg0, View arg1, int posion, long arg3) {
                // 화면 하단에 잠시 나타났다 사라지는 메시지(토스트)를 띄웁니다.
                // 클릭된 항목의 이름을 가져와 "selected : [이름]" 형태로 표시합니다.
                Toast.makeText(getApplicationContext(), "selected : " + names[posion],
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG, "이름 :" + names[posion]); // 디버그
            }
        });

        // RecyclerView 는 본인한테 맞는 특별한 어탭터를 구성해야 한다.
        // RecyclerView는 ListView보다 더 유연하고 성능이 좋지만, 별도의 복잡한 어댑터 설정이 필요하다는 점을 주석으로 설명하고 있습니다.
        RecyclerView recyclerView = findViewById(R.id.recycleView1);
    }
}