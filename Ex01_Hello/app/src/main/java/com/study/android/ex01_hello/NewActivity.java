package com.study.android.ex01_hello;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    String sName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        // 인텐트에 전달된 데이터 구하기
        Intent intent = getIntent(); // 시작하는 데 사용된 객체를 가져옵니다
        // "CustomerName": 인텐트에서 키와 연관된 문자열 데이터를 추출하여 sName변수에 저장합니다.
        // 이를 통해 NewActivity에서 전송된 고객 이름(예: "홍길동" 또는 "전우치")을 수신합니다 MainActivity.
        sName = intent.getStringExtra("CustomerName");
    }

    public void onButton7Clicked(View v)
    {
        // 화면에 팝업 "토스트" 메시지가 표시됩니다.
        // 이 메시지는 메서드 CustomerName에서 수신된 데이터를 표시하여 onCreate이 액티비티에 데이터가 성공적으로 전달되었음을 확인합니다.
        Toast.makeText(getApplicationContext(), "CustomerName : " + sName, Toast.LENGTH_SHORT).show();
    }

    public void onButton8Clicked(View v)
    {
        // 현재 인텐트 종료사 인텐트에 정달할 데이터 세팅
        Intent intent = new Intent(); // 새롭고 빈 Intent객체를 생성
        // 인텐트에 명명된 문자열을 넣고 "강감찬"키와 연결합니다 .
        // 이것이 다시 전송할 "BackData"데이터입니다 .NewActivity
        intent.putExtra("BackData", "강감찬");
        // 이는 결과를 반환하는 데 중요한 단계입니다.
        // 결과 코드를 설정하고 10반환 데이터가 포함된 인텐트를 연결합니다.
        // 결과 코드는 10이 활동의 성공적인 반환을 나타내기 위해 선택한 임의의 정수입니다.
        setResult(10, intent);
        // 이 메서드는 NewActivity를 닫고, 제어권을 이전 액티비티( MainActivity)로 반환합니다.
        // 이 호출은 ActivityResultLauncher를 닫기 전에 setResult결과 데이터가 MainActivity의 콜백( )에 전달되도록 합니다.
        finish();
    }
}

