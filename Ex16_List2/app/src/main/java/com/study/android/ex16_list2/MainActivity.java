package com.study.android.ex16_list2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//  **ListView**에 사용자 지정(커스텀) 어댑터를 적용하여 항목을 표시하는 예제입니다.
//  이 코드는 ArrayAdapter보다 더 복잡한 디자인의 목록 항목을 만들 때 사용됩니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    String[] names = { "홍길동", "강감찬", "을지문덕", "양만춘", "유관순"}; // 리스트
    String[] ages = { "20", "25", "30", "35", "40"}; // 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView1 = findViewById(R.id.listView1);

//        ArrayAdapter<String> adapter1;
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
//        listView1.setAdapter(adapter1);

        // 2단계
        // 이전에 사용했던 ArrayAdapter 대신, 직접 만든 MyAdapter 클래스의 객체를 생성합니다.
        final MyAdapter adapter = new MyAdapter();
        // 생성한 커스텀 어댑터를 리스트뷰에 연결
        listView1.setAdapter(adapter);

        // 4 단계
        // setOnItemClickListener를 설정하여 리스트뷰의 항목이 클릭되었을 때의 동작을 정의합니다.
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // onItemClick 메서드 내에서 클릭된 항목의 position을 이용해 names 배열에서 이름을 가져옵니다.
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int posion, long arg3) {
                // Toast 메시지와 Log를 통해 선택된 이름을 화면에 표시하고 로그에 기록
                Toast.makeText(getApplicationContext(), "selected : " + names[posion],
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG, "이름 :" + names[posion]); // 디버그
            }
        });
    }

    // 1단계
    // BaseAdapter를 상속받아 ListView가 데이터를 표시하는 방식을 완전히 제어합니
    class MyAdapter extends BaseAdapter {
        // : 리스트에 표시할 항목의 총 개수를 반환합니다.
        // 여기서는 names 배열의 길이를 반환하므로, 총 5개의 항목이 표시됩니다.
        @Override
        public int getCount() {
            return names.length;
        }

        // 해당 위치(position)의 데이터 항목을 반환합니다.
        // 여기서는 names[position]을 반환합니다.
        @Override
        public Object getItem(int position) {
            return names[position];
        }

        // 해당 위치의 항목 ID를 반환합니다. 보통 position 값을 그대로 사용합니다.
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 가장 중요한 메서드로, 리스트뷰의 각 항목에 대한 **뷰(View)**를 만듭니다.
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 1-1  단계
            // 이름(names[position])을 표시할 첫 번째 TextView를 생성하고, 글꼴 크기와 색상을 설정합니다.
            TextView view1 = new TextView( getApplicationContext());
            view1.setText(names[position]);
            view1.setTextSize(40.0f);
            view1.setTextColor(Color.BLUE);

//                return view1;

            // 3단계
            // 두 개의 TextView를 수직으로 배치하기 위해 LinearLayout을 생성합니다.
            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            layout.addView(view1);  // 이름 TextView를 LinearLayout에 추가

            //  나이(ages[position])를 표시할 두 번째 TextView를 생성하고, 글꼴 크기와 색상을 설정합니다.
            TextView view2 = new TextView( getApplicationContext() );
            view2.setText( ages[position]);
            view2.setTextSize(40.0f);
            view2.setTextColor(Color.RED);

            layout.addView(view2);  // 나이 TextView를 LinearLayout에 추가

            // 이름과 나이가 모두 포함된 LinearLayout 전체를 리스트뷰의 한 항목으로 반환
            return layout;
        }
    }
}