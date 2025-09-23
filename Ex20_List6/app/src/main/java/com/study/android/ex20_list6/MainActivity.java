package com.study.android.ex20_list6;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    // 변수 선언
    SingleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new SingleAdapter(this);

        // SingleItem 객체 3개를 미리 생성하여 어댑터에 추가
        SingleItem item1 = new SingleItem("홍길동", "010-1234-5678", R.drawable.face1, 1);
        adapter.addItem(item1);

        SingleItem item2 = new SingleItem("이순신", "010-4321-9876", R.drawable.face2, 2);
        adapter.addItem(item2);

        SingleItem item3 = new SingleItem("김유신", "010-5678-4321", R.drawable.face3, 1);
        adapter.addItem(item3);

        // R.id.listView1을 찾아 어댑터를 설정하고, 항목 클릭 리스너를 추가
        ListView listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int posion, long arg3) {
                SingleItem item = (SingleItem)adapter.getItem(posion);
                Toast.makeText(getApplicationContext(), "selected : " + item.getName(),
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG, "이름 :" + item.getName()); // 디버그
            }
        });
    }
}