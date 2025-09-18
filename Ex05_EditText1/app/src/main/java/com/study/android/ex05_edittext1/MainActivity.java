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

// 사용자 입력 유효성 검사와 키보드 제어를 다루는 예제입니다.
// 이 앱은 사용자 ID, 비밀번호, 그리고 생년월일을 입력받는 여러 개의
// EditText 위젯을 포함하며, 입력된 값에 대한 다양한 검증 로직을 구현합니다.
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
    protected void onCreate(Bundle savedInstanceState) {    // 앱이 시작될 때 실행
        super.onCreate(savedInstanceState);
        // activity_main.xml 레이아웃 파일을 화면에 표시합니다.
        setContentView(R.layout.activity_main);

        // findViewById(): XML 레이아웃에 정의된 뷰(View) 객체들,
        // 즉 TextView와 여러 EditText를 찾아와서 변수에 할당합니다.
        // 이를 통해 코드에서 각 뷰를 제어할 수 있게 됩니다.
        textView1 = findViewById(R.id.textView1);

        etId = findViewById(R.id.etId);
        etPwd = findViewById(R.id.etPwd);
        etYear = findViewById(R.id.etYear);
        etMonth = findViewById(R.id.etMonth);
        etDay = findViewById(R.id.etDay);

        // 월 입력시 유효성 체크
        // etMonth (월 입력란)에 **TextWatcher**를 등록합니다.
        // 이 리스너는 사용자가 월 입력란에 텍스트를 입력할 때마다 watcher
        // 객체에 정의된 메서드들을 호출하여 실시간으로 입력값을 검사하게 됩니다.
        etMonth.addTextChangedListener(watcher);
    }

    // inputType과 maxLength가 EditText에 지정되어 있으면 키보드에 [리턴]이 [다음]으로 됨
    // 맨 마지막 입력칸은 자동으로 [완료]가 됨
    // 입력칸이 키보드에 가려지면 자동으로 올라감.

    // 키보드 내리기 버튼. 키보드를 강제로 숨기는 기능을 수행
    public void onKeydownClicked(View v)
    {
        // InputMethodManager mgr = ...: InputMethodManager 객체를 가져와서
        // 키보드 제어에 필요한 기능을 사용할 수 있게 합니다.
        InputMethodManager mgr = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        // 현재 활성화된 키보드를 화면에서 숨깁니다.
        // 이는 보통 '키보드 내리기' 버튼을 클릭했을 때 사용됩니다.
        mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    // 로그인 버튼.  로그인 유효성 검사
    public void onLoginClicked(View v)
    {
        // etId (아이디 입력란)에 입력된 텍스트를 가져와 sId 변수에 저장합니다.
        sId = etId.getText().toString();
        sPwd = etPwd.getText().toString();

        if (sId.length() < 3 ) {    // 아이디의 길이가 3자 미만인지 확인
            // 만약 3자 미만이면, "아이디를 입력해 주세요"라는 Toast 메시지를
            // 띄우고 etId.requestFocus()를 호출하여 아이디 입력란에 포커스를 이동시킵니다.
            Toast.makeText(getApplicationContext(), "아이디를 입력해 주세요 ",
                    Toast.LENGTH_SHORT).show();
            etId.requestFocus();
        } else if (sPwd.length() < 5) { // 아이디 길이가 충분하면 비밀번호의 길이가 5자 미만인지 확인
            // 5자 미만이면, "비밀번호를 정확히 입력해 주세요"라는 Toast 메시지를 띄우고
            // 비밀번호 입력란에 포커스를 이동시킵니다.
            Toast.makeText(getApplicationContext(), "비밀번호를 정확히 입력해 주세요 ",
                    Toast.LENGTH_SHORT).show();
            etPwd.requestFocus();
        }
    }

    // 최대 자리수는 maxLength로 처리
    // 숫자만 입력은 inputType으로 처리
    // 13월 등의 처리 필요

    // 월 입력 유효성 검사 (TextWatcher 객체).
    // watcher 객체는 etMonth에 실시간으로 적용되는 입력 검사 로직을 포함
    TextWatcher watcher = new TextWatcher() {
        String beforeText;

        // 텍스트가 변경되기 전에 호출되며, 변경 전의 텍스트를 beforeText 변수에 저장합니다.
        // 이 정보는 유효하지 않은 입력이 들어왔을 때 이전 상태로 되돌리는 데 사용됩니다.
        @Override
        public void beforeTextChanged(CharSequence str, int start, int count, int after) {
            beforeText = str.toString();
        }

        // 텍스트가 변경될 때마다 호출
        @Override
        public void onTextChanged(CharSequence str, int start, int before, int count) {
            try{
                int strCount = str.toString().length();
                Log.d(TAG, "Count : " + strCount);

                if (strCount > 0) {
                    // 입력된 텍스트를 정수형으로 변환
                    int number = Integer.parseInt(str.toString());
                    // 12가 넘을 경우 입력 취소 처리. 입력된 숫자가 12를 초과하는지 확인
                    if (number > 12){
                        // 만약 12를 초과하면, etMonth.setText(beforeText)를 통해 이전 텍스트로 되돌리고,
                        // etMonth.setSelection(start)를 통해 커서 위치를 복구합니다. 이로써 사용자가
                        // 12보다 큰 월을 입력하지 못하게 합니다.
                        etMonth.setText(beforeText);
                        etMonth.setSelection(start);
                    }
                }
                // 현재 입력된 글자 수를 textView1에 표시합니다.
                textView1.setText(strCount + " / 2");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        // 텍스트 변경 후 호출되지만, 이 코드에서는 특별한 기능이 구현되어 있지 않습니다.
        @Override
        public void afterTextChanged(Editable editable) { }
    };
}