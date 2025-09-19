package com.study.android.ex10_introex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//  IntroActivity 클래스예요.
//  이 액티비티는 보통 앱이 시작될 때 잠깐 보여주는 인트로(Splash) 화면을 구현하는 데 사용돼요.
//  간단히 말해, 이 코드는 2초 동안 인트로 화면을 보여준 뒤 자동으로 MainActivity로 전환하는 기능을 수행합니다.
public class IntroActivity extends AppCompatActivity {
    private static final String TAG = "lecture";
    // Handler에서 메시지를 구분하기 위한 정수 값이에요. 메시지 코드로 사용돼요.
    private static final int STOPSPLASH = 0;
    // 인트로 화면이 표시될 시간(밀리초)을 정의해요. 여기서는 2초로 설정되어 있어요.
    private static final int SPLASHTIME = 2000;  // 2초

    // Handler는 특정 시간 뒤에 작업을 수행하거나, 다른 스레드로부터 메시지를 받아 작업을 처리할 때 사용돼요.
    // 여기서는 인트로 화면이 끝날 시간을 처리하는 데 사용됩니다.
    private final Handler splashHandler = new Handler(Looper.myLooper()){
        // splashHandler가 메시지를 받을 때 호출되는 메소드예요.
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Intent intent;

            // 메시지의 종류(msg.what)에 따라 다른 작업을 수행
            switch (msg.what){
                // 메시지 코드가 STOPSPLASH일 때 실행되는 부분이에요.
                // 이 코드는 2초 후에 인트로 화면을 종료하고 메인 화면으로 전환하는 역할을 해요.
                case STOPSPLASH:
                    // IntroActivity에서 MainActivity로 이동하기 위한 Intent를 생성해요.
                    intent = new Intent(IntroActivity.this, MainActivity.class);
                    // 생성한 Intent를 실행해 MainActivity를 시작해요
                    startActivity(intent);
                    // 화면 전환 시 애니메이션 효과를 줍니다.
                    // fade_in은 새 화면이 나타날 때 서서히 나타나게 하고, hold는 현재 화면을 잠시 멈춰두는 효과를 줘요.
                    overridePendingTransition(R.anim.fade_in, R.anim.hold);
                    // 현재 액티비티(IntroActivity)를 종료해요.
                    // 이렇게 하면 사용자가 뒤로 가기 버튼을 눌렀을 때 인트로 화면으로 돌아가지 않고 앱이 종료됩니다.
                    finish();
                    break;
            }
        }
    };

    // 액티비티가 처음 생성될 때 호출
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 주의 : activity_intro.xml 레이아웃 파일을 화면에 표시
        setContentView(R.layout.activity_intro);
    }

    // 액티비티가 다시 화면에 나타날 때마다 호출
    @Override
    protected void onResume() {
        super.onResume();

        Message msg = new Message();     // 새로운 메시지 객체를 생성
        msg.what = STOPSPLASH;  // 메시지의 종류를 STOPSPLASH로 설정
        // splashHandler에게 SPLASHTIME (2초) 후에 메시지(msg)를 보내도록 요청해요.
        // 이 시점부터 2초가 지나면 위에서 설명한 handleMessage() 메소드가 실행되어 화면이 전환됩니다.
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }
}