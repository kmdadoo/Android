package com.study.android.ex05_edittext1;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TextView textView1;

    EditText etId;
    EditText etPwd;
    EditText etYear;
    EditText etMonth;
    EditText etDay;

    String sId;
    String sPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);

        etId = findViewById(R.id.etId);
        etPwd = findViewById(R.id.etPwd);
        etYear = findViewById(R.id.etYear);
        etMonth = findViewById(R.id.etMonth);
        etDay = findViewById(R.id.etDay);

        // 월 입력시 유효성 체크
        etMonth.addTextChangedListener(watcher);
    }

    // inputType과 maxLength가 EditText에 지정되어 있으면 키보드에 [리턴]이 [다음]으로 됨
    // 맨 마지막 입력칸은 자동으로 [완료]가 됨
    // 입력칸이 키보드에 가려지면 자동으로 올라감.

    // 키보드 내리기 버튼
    public void onKeydownClicked(View v)
    {
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    // 로그인 버튼
    public void onLoginClicked(View v)
    {
        sId = etId.getText().toString();
        sPwd = etPwd.getText().toString();

        if (sId.length() < 3 ) {
            Toast.makeText(getApplicationContext(), "아이디를 입력해 주세요 ", Toast.LENGTH_SHORT).show();
            etId.requestFocus();
        } else if (sPwd.length() < 5) {
            Toast.makeText(getApplicationContext(), "비밀번호를 정확히 입력해 주세요 ", Toast.LENGTH_SHORT).show();
            etPwd.requestFocus();
        }
    }

    // 최대 자리수는 maxLength로 처리
    // 숫자만 입력은 inputType으로 처리
    // 13월 등의 처리 필요
    TextWatcher watcher = new TextWatcher() {
        String beforeText;

        @Override
        public void beforeTextChanged(CharSequence str, int start, int count, int after) {
            beforeText = str.toString();
        }

        @Override
        public void onTextChanged(CharSequence str, int start, int before, int count) {
            try{
                int strCount = str.toString().length();
                Log.d(TAG, "Count : " + strCount);

                if (strCount > 0) {
                    int number = Integer.parseInt(str.toString());
                    // 12가 넘을 경우 입력 취소 처리
                    if (number > 12){
                        etMonth.setText(beforeText);
                        etMonth.setSelection(start);
                    }
                }
                textView1.setText(strCount + " / 2");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    };
}