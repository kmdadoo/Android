package com.study.android.ex14_spinner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// **스피너(Spinner)**를 사용하는 방법을 보여주는 예제입니다.
// 스피너는 드롭다운 목록과 유사한 형태로, 사용자가 여러 항목 중에서 하나를 선택할 수 있게 해주는 UI 위젯입니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    //  화면에 선택된 항목의 이름을 표시할 TextView 객체를 선언
    TextView textView;

    // 스피너에 표시할 항목들을 문자열 배열로 미리 정의
    String[] items = { "mike", "angel", "crow", "john", "ginnie", "sally", "cohen", "rice" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML 레이아웃 파일(activity_main.xml)에 있는 textView1 위젯과 코드를 연결합니다.
        textView = findViewById(R.id.textView1);

        // 스피너에 데이터를 연결해주는 **어댑터(Adapter)**를 생성합니다.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                // this: 현재 액티비티의 컨텍스트(맥락)를 의미합니다.
                // android.R.layout.simple_spinner_item:
                //      안드로이드에서 기본으로 제공하는 스피너 항목의 디자인 레이아웃입니다.
                // items: 위에서 정의한 문자열 배열을 어댑터에 전달하여 스피너에 항목들이 표시되도록 합니다.
                this, android.R.layout.simple_spinner_item, items);

        // XML 레이아웃에 있는 spinner 위젯과 코드를 연결합니다.
        Spinner spinner = findViewById(R.id.spinner);
        //  생성한 어댑터를 스피너에 최종적으로 연결
        spinner.setAdapter(adapter);

        // 스피너 이벤트 처리
        // 사용자가 스피너에서 어떤 항목을 선택했을 때 발생하는 이벤트를 처리하기 위한 리스너(Listener)를 설정합니다.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //  아이템이 선택되었을 때 호출 됨
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       // position: 선택된 항목의 인덱스 번호(위치)를 의미합니다. 0부터 시작
                                       View view, int position, long id) {
                // 선택된 항목의 인덱스를 이용해 items 배열에서 해당 이름을 가져와 textView에 표시합니다.
                textView.setText(items[position]);
                Log.d(TAG, "선택된 이름 : " + items[position]);  //  디버그
            }

            // 아무것도 선택되지 않았을 때 호출 됨
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView.setText("");

            }
        });
    }
}