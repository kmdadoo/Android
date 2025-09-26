package com.study.android.ex31_httpex1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

// HTTP 통신(네트워크 통신) 예제를 위해 만들어진 것으로 보이며,
// GET 방식과 POST 방식 통신을 테스트하는 두 개의 버튼을 포함하고 있습니다.
// AppCompatActivity를 상속받아 이전 버전 안드로이드 기기에서도 UI와 기능을 호환할 수 있게 합니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    Button btnGetAct;
    Button btnPostAct;

    @Override
    // 최초로 생성될 때 호출되는 핵심 메소드
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 실제 화면의 버튼 객체를 찾아와 변수에 연결(
        btnGetAct = findViewById(R.id.btnGetAct);
        btnPostAct = findViewById(R.id.btnPostAct);
    }

    public  void onBtnGetAct(View v) {
        // 현재 액티비티(MainActivity)에서 **HttpGetActivity.class**로
        // 전환하기 위한 Intent 객체를 생성합니다.
        Intent intent = new Intent(MainActivity.this, HttpGetActivity.class);
        // 생성한 Intent를 사용하여 HttpGetActivity 화면을 시작(실행)합니다.
        startActivity(intent);
    }

    public  void onBtnPostAct(View v) {
        Intent intent = new Intent(MainActivity.this, HttpPostActivity.class);
        startActivity(intent);
    }
}