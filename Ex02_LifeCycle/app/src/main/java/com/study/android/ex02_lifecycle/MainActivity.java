package com.study.android.ex02_lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

// 이 코드는 액티비티의 생명주기(Lifecycle)와 다른 화면으로 이동하는 방법을 보여줍니다.
public class MainActivity extends AppCompatActivity {
    // 액티비티가 처음 생성될 때 호출
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 부모 클래스의 onCreate를 호출해 기본적인 초기화를 수행합니다.
        super.onCreate(savedInstanceState);
        // activity_main.xml이라는 레이아웃 파일을 화면에 표시합니다.
        setContentView(R.layout.activity_main);
    }

    private static final String TAG = "lecture";

    // 버튼을 클릭했을 때 호출
    public void onButton1Clicked(View v)
    {
        Log.d(TAG, "버튼1 클릭");
        // NewActivity.class를 목적지로 하는 Intent(인텐트)를 생성합니다.
        // Intent는 안드로이드 컴포넌트 간의 통신을 담당하는 객체로,
        // 여기서는 NewActivity라는 새 화면으로 이동하겠다는 의도를 나타냅니다.
        Intent intent = new Intent(getApplicationContext(), NewActivity.class);
        // NewActivity를 실행합니다.
        // 이전에 쓰이던 startActivityForResult()와 동일한 역할을 하지만, 더 현대적이고 권장되는 방식입니다.
        newActivityResult.launch(intent);
    }

    // 새로운 액티비티(NewActivity)가 종료된 후 결과를 받기 위해 등록하는 코드입니다.
    ActivityResultLauncher<Intent> newActivityResult = registerForActivityResult(
        // new ActivityResultContracts.StartActivityForResult(): 결과를 받을 것임을 계약(contract)으로 명시합니다.
        new ActivityResultContracts.StartActivityForResult(),
        // result -> { ... }: NewActivity가 종료되고 결과가 돌아왔을 때 실행될 콜백 함수입니다.
result -> {
            //  화면 하단에 "ActivityResult() 호출됨"이라는 짧은 팝업 메시지(토스트)를 표시합니다.
            //  이는 NewActivity에서 돌아온 직후 이 코드가 실행되었음을 확인시켜 줍니다.
            Toast.makeText(getApplicationContext(),"ActivityResult() 호출됨", Toast.LENGTH_SHORT).show();
        });
}