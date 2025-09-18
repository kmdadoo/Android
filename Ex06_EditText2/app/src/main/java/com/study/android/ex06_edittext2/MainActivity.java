package com.study.android.ex06_edittext2;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

// EditText에 입력된 숫자에 콤마(,)를 자동으로 추가하여 통화 형식을 만드는 기능을 구현합니다.
// 사용자가 숫자를 입력할 때 실시간으로 세 자리마다 콤마가 찍히도록 하여, 금액이나 큰 숫자를 보기 쉽게 만들어줍니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    EditText inputMessage;
    String strAmount = "";  // 임시 저장값(콤마)

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // 앱이 실행될 때 호출
        super.onCreate(savedInstanceState);
        // activity_main.xml 레이아웃 파일을 화면에 연결합니다.
        setContentView(R.layout.activity_main);

        // activity_main.xml에 정의된 etMessage라는 ID의 EditText를 찾아와서 inputMessage 변수에 할당합니다
        inputMessage = findViewById(R.id.etMessage);

//        inputMessage.setInputType(InputType.TYPE_CLASS_NUMBER); // 숫자만 입력
        // inputMessage에 **TextWatcher**를 등록합니다.
        // 이 리스너는 사용자가 텍스트를 입력하거나 수정할 때마다 watcher 객체에 정의된
        // 메서드들을 호출하여 실시간으로 텍스트를 변환합니다.
        inputMessage.addTextChangedListener(watcher);
    }

    // 콤마 자동 추가
    // watcher 객체는 inputMessage에 입력되는 텍스트를 실시간으로 처리합니다.
    TextWatcher watcher = new TextWatcher() {
        // onTextChanged(): 텍스트가 변경되는 도중에 호출됩니다
        @Override
        public void onTextChanged(CharSequence str, int start, int count, int after) {
            // inputMessage.removeTextChangedListener(this): TextWatcher가 실행되는
            // 동안 무한 루프에 빠지는 것을 방지하기위해 리스너를 일시적으로 제거합니다.
            // (텍스트를 setText()로 다시 설정하면 onTextChanged가 다시 호출되기 때문입니다.)
            inputMessage.removeTextChangedListener(this);
            try {
                if (!str.toString().equals(strAmount)){
                    // 입력된 문자열에서 기존 콤마(,)를 모두 제거한 후,
                    // makeStringComma 메서드를 호출하여 새롭게 콤마를 추가합니다.
                    strAmount = makeStringComma(str.toString().replace(",", ""));
                    Log.d(TAG, "strAmount : " + strAmount);
                    // 콤마가 추가된 문자열을 EditText에 다시 설정합니다.
                    inputMessage.setText(strAmount);
                    // 텍스트가 변경된 후 커서가 항상 맨 끝에 위치하도록 설정하여 사용자 입력 경험을 자연스럽게 만듭니다.
                    inputMessage.setSelection(inputMessage.getText().length());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 처리가 완료되면 다시 TextWatcher를 등록하여 다음 입력에 대비합니다.
            inputMessage.addTextChangedListener(watcher);
        }

        @Override
        public void beforeTextChanged(CharSequence str, int start, int before, int count) {
            // 현재 입력된 텍스트를 strAmount 변수에 임시로 저장합니다.
            // 이 값은 beforeTextChanged에서 콤마를 제거하는 용도로 사용됩니다.
            strAmount = str.toString();
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    };

    // 콤마 추가 로직
    // 입력받은 문자열에 콤마를 추가하여 반환하는 메서드입니다.
    protected String makeStringComma(String str) {
        // 빈 문자열이 들어오면 그대로 빈 문자열을 반환
        if (str.length() == 0){
            return "";
        }
        // 콤마가 없는 문자열을 long 타입의 숫자로 변환합니다.
        long value = Long.parseLong(str);
        // DecimalFormat 클래스를 사용하여 숫자를 지정된 형식으로 포맷합니다.
        // "###,##0" 패턴은 세 자리마다 콤마를 추가하는 표준적인 통화 형식입니다.
        DecimalFormat dfmt = new DecimalFormat("###,##0");
        // 포맷된 문자열(1000000이 1,000,000으로)을 반환합니다
        return dfmt.format(value);
    }
}