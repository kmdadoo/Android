package com.study.android.ex09_alert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// 이 클래스는 화면에 있는 여러 버튼에 대한 클릭 이벤트를 처리하며, 각 버튼을 누르면
// 다른 종류의 **알림 대화상자(AlertDialog)**를 띄우는 기능을 구현합니다.
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 액티비티가 시작될 때 가장 먼저 호출되는 필수 메소드
        super.onCreate(savedInstanceState);
        // activity_main.xml 레이아웃 파일을 화면에 표시하여 앱의 UI를 설정합니다.
        setContentView(R.layout.activity_main);
    }

    // 버튼 1 : 기본형 알림창
    public void onButton1Clicked(View v)
    {
        // 현재 액티비티를 기준으로 알림창을 만들기 위한 빌더 객체를 생성합니다.
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("알림") // 알림창의 제목
                .setMessage("알럿 창입니다.") //  알림창에 표시될 메시지
                // 사용자가 알림창 바깥을 터치하거나 뒤로가기 버튼을 눌러도 알림창이 닫히지 않도록 합니다.
                .setCancelable(false)
                // 알림창 제목 옆에 전화 다이얼러 모양의 아이콘을 표시합니다.
                .setIcon(android.R.drawable.ic_dialog_dialer)
                // '닫기'라는 긍정 버튼을 추가하고, 버튼 클릭 시 아무런 동작도 하지 않도록 null로 설정합니다.
                .setPositiveButton("닫기", null)
                .show(); // 설정한 알림창을 화면에 띄웁니다.
    }

    // 버튼 2 : 버튼 처리
    public void onButton2Clicked(View v)
    {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("알림")
                .setMessage("앱을 종료하시겠습니까?")
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_dialer)
                // 'Yes' 버튼을 눌렀을 때 실행될 동작을 정의하는 리스너입니다.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    // 버튼 클릭 시 이 메소드가 호출
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // 'ID value: ...' 라는 메시지를 담은 **토스트(잠깐 나타났다 사라지는 메시지)**를
                        // 화면에 띄웁니다. id는 클릭된 버튼의 고유 ID를 나타냅니다.
                        Toast.makeText(getApplicationContext(),"ID value : " +
                                Integer.toString(id), Toast.LENGTH_SHORT).show();
                        dialog.cancel();    // 토스트 메시지 표시 후 알림 대화상자를 닫습니다.
                    }
                })
                .show();
    }

    // 버튼 3 :  MyUtil을 이용한 알림창 (제목+메시지)
    public void onButton3Clicked(View v)
    {
        // 첫 번째 인자: 현재 컨텍스트(MainActivity.this)를 전달합니다.
        //두 번째 인자: 메시지 내용인 "아이디를 입력해 주세요"를 전달합니다.
        //세 번째 인자: 제목인 "알림"을 전달합니다.
        MyUtil.AlertShow(MainActivity.this, "아이디를 입력해 주세요", "알림");
    }

    // 버튼 4 : 상단 없애고 내용만. MyUtil을 이용한 알림창 (메시지만)
    public void onButton4Clicked(View v)
    {
        // 첫 번째 인자: 현재 컨텍스트(MainActivity.this)를 전달합니다.
        //두 번째 인자: 메시지 내용인 "아이디를 입력해 주세요"를 전달합니다.
        MyUtil.AlertShow(MainActivity.this, "아이디를 입력해 주세요");
    }
}