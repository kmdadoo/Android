package com.study.android.ex21_grid;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//  **그리드뷰(GridView)**를 사용해 데이터를 격자 형태로 보여주는 안드로이드 앱의 예제입니다.
//  BaseAdapter를 상속받는 커스텀 어댑터를 사용하여 이름과 나이 데이터를 그리드에 표시하고,
//  각 항목을 클릭했을 때 로그를 출력하는 기능이 구현되어 있습니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    // 변수 선언
    String[] names = {"홍길동", "강간찬", "을지문덕", "양만춘", "유관순"};
    String[] ages = {"20", "25", "30", "35", "40"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyAdapter adapter = new MyAdapter();  // 객체를 생성

        // R.id.gridView1을 찾아 어댑터를 설정
        GridView gridView1 = findViewById(R.id.gridView1);
        gridView1.setAdapter(adapter);
        // 그리드 항목을 클릭했을 때의 동작을 정의
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // position을 2로 나누어 행(가로줄) 인덱스를 계산합니다.
                // (그리드뷰가 한 줄에 2개의 항목을 표시하기 때문)
                int row = position / 2;
                // int column = position % 2;: position을 2로 나눈 나머지를
                //      이용해 열(세로줄) 인덱스를 계산합니다.
                int column = position % 2;

                Log.d(TAG, "Row index : " + row + " Column index :" + column);
                Log.d(TAG, names[row * 2 + column]);
            }
        });
    }

    //  그리드뷰에 데이터를 제공
    class MyAdapter extends BaseAdapter {
        // 그리드뷰에 표시할 항목의 총 개수를 반환
        @Override
        public int getCount() {
            return names.length;
        }

        // 지정된 위치의 데이터를 반환
        @Override
        public Object getItem(int position) {
            return names[position];
        }

        // 지정된 위치의 ID를 반환
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 그리드 항목의 뷰를 생성
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 뷰를 재활용하는 패턴을 사용하여 성능을 최적화합니다.
            LinearLayout layout;
            if (convertView == null) {
                // LinearLayout 객체를 생성합니다. 이 레이아웃이 하나의 그리드 항목이 됩니다.
                layout = new LinearLayout(getApplicationContext());
                // 내부의 뷰들이 세로로 배치되도록 설정합니다.
                layout.setOrientation(LinearLayout.VERTICAL);
                // 각 항목의 배경색을 흰색으로 설정하여 그리드뷰의 회색 배경과 대비되게 합니다.
                layout.setBackgroundColor(Color.WHITE);
                // 뷰의 패딩을 설정하여 안쪽 여백을 줍니다.
                layout.setPadding(10, 20, 10, 20);

                // names 배열의 position에 해당하는 이름으로 TextView를 생성하고, 크기와 색상을 설정합니다.
                TextView view1 = new TextView(getApplicationContext());
                view1.setTextSize(40.0f);
                view1.setTextColor(Color.BLUE);
                // 이 뷰를 LinearLayout에 추가
                layout.addView(view1);

                TextView view2 = new TextView(getApplicationContext());
                view2.setTextSize(40.0f);
                view2.setTextColor(Color.RED);
                layout.addView(view2);
            } else {
                layout = (LinearLayout) convertView;
            }

            // 데이터를 설정합니다.
            TextView view1 = (TextView) layout.getChildAt(0);
            view1.setText(names[position]);
            TextView view2 = (TextView) layout.getChildAt(1);
            view2.setText(ages[position]);

            // 이름과 나이 뷰가 모두 포함된 LinearLayout을 반환하면,
            // 그리드뷰는 이 LinearLayout을 해당 위치에 표시합니다.
            return layout;
        }
    }
}