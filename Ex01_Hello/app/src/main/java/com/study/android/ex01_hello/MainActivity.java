package com.study.android.ex01_hello;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

// 제공된 안드로이드 Java 코드는 여러 버튼에 대한 클릭 리스너를 정의하고, 다양한 기능을 수행하는 앱을 보여줍니다.
// 이 코드는 특히 액티비티 결과 처리를 위한 최신 방법인 **ActivityResultLauncher**를 사용하는 방식에 초점을 맞추고 있습니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button);
        // 익명 클래스를 사용하여 클릭 리스너를 설정.  기본적인 이벤트 처리
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "로그 출력");
                Toast.makeText(getApplicationContext(), "긴 토스트", Toast.LENGTH_SHORT).show();
            }
        });

        // 버튼2 :
        // 인텐트 만들어 웹브라우저 띄우기. 람다(lambda) 표현식을 사용해 클릭 리스너를 설정
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            // 이 버튼을 누르면 ACTION_VIEW 인텐트를 통해 지정된 URL(https://m.naver.com)을 기본 웹 브라우저 앱에서 엽니다.
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.naver.com"));
            startActivity(intent);
        });
    }

    // 버튼3 :
    // 인텐트 만들어 전화 걸기
    // 이 버튼은 XML 레이아웃 파일의 android:onClick 속성과 연결되어 있습니다.
    // 클릭 시, tel: URI를 포함하는 ACTION_VIEW 인텐트를 실행하여 전화 앱을 띄우고 지정된 번호로 전화를 걸 준비를 합니다.
    public void onButton3Clicked(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-9273-9992"));
        startActivity(intent);
    }

    // 버튼4 :
    // EditText에 입력한 값을 TextView에 보여주기
    // android:onClick 속성으로 연결됩니다.
    // EditText에 입력된 텍스트를 가져와 TextView에 표시하고, 그 내용을 Toast 메시지로도 띄워 사용자에게 피드백을 제공합니다.
    public void onButton4Clicked(View v)
    {
        EditText editText = findViewById(R.id.editText);
        TextView textView = findViewById(R.id.textView);
        textView.setText(editText.getText());

        String str = editText.getText().toString();
        Toast.makeText(getApplicationContext(), "EditText : " + str, Toast.LENGTH_SHORT).show();
    }

    // 버튼5 :
    // 내가 생성한 액티비티 실행하고 결과 받아오기
    // 이 두 버튼은 **startActivityForResult**의 최신 대체 기술인 **ActivityResultLauncher**를 사용합니다.
    public void onButton5Clicked(View v)
    {
        Intent intent = new Intent(getApplicationContext(), NewActivity.class);
        intent.putExtra("CustomerName", "홍길동");
//        startActivity(intent);
        // 초기화된 런처를 사용해 NewActivity를 시작합니다.
        startNewActivityLauncher.launch(intent);
    }

    // 런처를 초기화하고 결과를 처리할 콜백을 정의합니다.
    // ActivityResultLauncher 선언 및 초기화: startNewActivityLauncher와 newActivityResult라는 두 개의 런처가 클래스 멤버 변수로 선언되어 있습니다.
    // 이들은 registerForActivityResult() 메서드를 통해 초기화되며, 액티비티가 종료된 후 결과를 받아 처리할 콜백 함수(result -> { ... })를 등록합니다.
    protected ActivityResultLauncher<Intent> startNewActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
        Log.d(TAG, "콜백 함수 호출됨");
        // 이 블록은 NewActivity가 종료되고 결과를 돌려줄 때 실행됩니다.
        // 콜백 함수 내에서는 result.getResultCode()를 통해 결과 코드를 확인하고, result.getData()를 통해
        // **NewActivity**에서 돌아온 Intent 객체를 가져와 BackData라는 이름으로 전달된 데이터를 추출합니다.
        if (result.getResultCode() == 10) {
            Intent data = result.getData();
            if (data != null) {
                String resultData = data.getStringExtra("BackData");
                Toast.makeText(this, "돌아온 결과: " + resultData, Toast.LENGTH_SHORT).show();
            }
        }
    });

    // 버튼6 :
    // 내가 생성한 액티비티 실행하고 결과 받아오기
    // 각 인텐트에는 CustomerName이라는 키에 다른 값이 담겨 있습니다.
    // 이 방식은 요청 코드를 수동으로 관리할 필요가 없으며, 더 안정적으로 결과를 처리할 수 있습니다.
    public void onButton6Clicked(View v)
    {
        Intent intent = new Intent(getApplicationContext(), NewActivity.class);
        intent.putExtra("CustomerName", "전우치");
        // 초기화된 런처를 사용해 NewActivity를 시작합니다.
        newActivityResult.launch(intent);
    }

    ActivityResultLauncher<Intent> newActivityResult = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), result -> {
            Log.d(TAG, "콜백 함수 호출됨");
            // 이 블록은 NewActivity가 종료되고 결과를 돌려줄 때 실행됩니다.
            if (result.getResultCode() == 10) {
                // 인텐트에 전달된 데이터 구하기
                Intent intent = result.getData();
                String sData = intent.getStringExtra("BackData");
                Toast.makeText(getApplicationContext(),sData, Toast.LENGTH_SHORT).show();
            }
        });
}