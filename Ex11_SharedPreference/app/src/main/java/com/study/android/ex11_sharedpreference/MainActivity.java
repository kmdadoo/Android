package com.study.android.ex11_sharedpreference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// SharedPreferences라는 안드로이드의 데이터 저장 방식을 이용해 사용자의
// 로그인 정보를 저장하고 불러오는 기능을 구현하려 하고 있어요.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    SharedPreferences.Editor editor;
    EditText tvID;
    EditText tvPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // login이라는 이름의 SharedPreferences 객체를 가져옵니다.
        // MODE_PRIVATE는 이 데이터가 현재 앱 내에서만 접근 가능하도록 설정하는 옵션
        SharedPreferences pref = getSharedPreferences("login", Activity.MODE_PRIVATE);
        // 이 객체에 데이터를 쓰거나 수정하기 위해 에디터(Editor) 객체를 준비합니다.
        editor = pref.edit();

        // 레이아웃 파일(activity_main.xml)에 정의된 아이디 입력란(EditText)을 변수 tvID에 연결해요.
        tvID = findViewById(R.id.etID);
        // 비밀번호 입력란(EditText)을 변수 tvPwd에 연결해요.
        tvPwd = findViewById(R.id.etPwd);

        //-------------------------------------------------------------------

        // pref 객체에서 **"id"**라는 키로 저장된 문자열 값을 불러옵니다.
        // 만약 값이 없으면 기본값인 "" (빈 문자열)을 반환해요.
        String id = pref.getString("id", "");
        // "pwd"라는 키로 저장된 비밀번호 값을 불러와요.
        String pwd = pref.getString("pwd", "");

        Log.d(TAG, "id:"+id);

        // 불러온 아이디와 비밀번호 값을 각각의 입력란에 자동으로 채워 넣습니다.
        // 이렇게 하면 앱을 다시 실행했을 때 이전에 입력했던 정보가 남아 있게 돼요.
        tvID.setText(id);
        tvPwd.setText(pwd);
    }

    // 로그인 버튼 클릭 시 호출
    public void onLoginClicked(View v)
    {
        // 1. EditText에 입력된 값을 가져온다.
        String id = tvID.getText().toString();
        String pwd = tvPwd.getText().toString();

        // 2. 입력된 값을 SharedPreferences 에디터에 저장한다.
        editor.putString("id", id);
        editor.putString("pwd", pwd);

        // 3. 변경사항을 최종적으로 적용한다.
        editor.apply();

        // 4. 저장 완료 메시지를 사용자에게 보여준다.
        Toast.makeText(getApplicationContext(), "로그인 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }
}