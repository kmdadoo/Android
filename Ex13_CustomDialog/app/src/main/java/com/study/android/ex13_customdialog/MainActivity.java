package com.study.android.ex13_customdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//  **두 가지 종류의 커스텀 다이얼로그(로그인 화면과 로딩 화면)**를 보여주는 기능을 구현하고 있습니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    // 가장 먼저 실행되는 메서드
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  레이아웃 파일을 화면에 표시하도록 설정
        setContentView(R.layout.activity_main);
    }

    // 사용자 지정 로그인 다이얼로그를 띄우고, 입력된 정보를 처리하는 로직을 담고 있습니다.
    public void onBtn1Clicked(View v)
    {
        // 새로운 다이얼로그 객체를 생성
        final Dialog loginDialog = new Dialog(this);
        // 다이얼로그의 화면을 custom_dialog.xml 레이아웃 파일로 설정합니다.
        // 이 파일은 로그인 화면의 UI를 정의하고 있습니다.
        loginDialog.setContentView(R.layout.custom_dialog);

        final EditText username = loginDialog.findViewById(R.id.editText1);
        final EditText password = loginDialog.findViewById(R.id.editText2);

        Button login = loginDialog.findViewById(R.id.button11);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 아이디와 비밀번호 입력창이 비어있지 않은지 확인합니다.
                if (username.getText().toString().trim().length() > 0
                    && password.getText().toString().trim().length() > 0){
                    // 입력값이 있을 경우: 로그인 성공"이라는 메시지를 짧게 보여주고, loginDialog.dismiss()를 통해 다이얼로그를 닫습니다.
                    Toast.makeText(getApplicationContext(), "로그인 성공 ", Toast.LENGTH_LONG).show();

                    loginDialog.dismiss();
                }else {
                    // 입력값이 없을 경우: "다시입력하세요"라는 메시지를 보여줍니다.
                    Toast.makeText(getApplicationContext(), "다시입력하세요", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button cancel = loginDialog.findViewById(R.id.button12);
        // 취소 버튼을 클릭하면 loginDialog.dismiss()를 호출하여 다이얼로그를 닫습니다
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
            }
        });

        // 위에서 설정한 모든 내용을 바탕으로 로그인 다이얼로그를 화면에 표시합니다.
        loginDialog.show();
    }

    // 원형 로딩 프로그레스 다이얼로그를 띄우는 역할을 합니다.
    public void onBtn2Clicked(View v)
    {
        // CustomCircleProgressDialog.java 클래스의 객체를 생성합니다.
        // 이 클래스에는 custom_circle_progress.xml 레이아웃 파일이 연결되어 있어 회전하는 로딩 이미지가 표시됩니다.
        CustomCircleProgressDialog customCircleProgressDialog =
                new CustomCircleProgressDialog(MainActivity.this);
        //주변 클릭 터치 시 프로그래서 사라지지 않게 하기 : false
        // 다이얼로그 외부를 터치했을 때 다이얼로그가 사라지도록 허용합니다.
        // (주석에는 false로 설명되어 있지만, 실제 코드 값은 true입니다.)
        customCircleProgressDialog.setCancelable(true);
        // 로딩 다이얼로그를 화면에 표시
        customCircleProgressDialog.show();
    }
}