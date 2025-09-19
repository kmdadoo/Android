package com.study.android.ex12_datepickerdialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

// 두 개의 버튼 클릭 이벤트에 따라 각각 **날짜 선택 대화상자(DatePickerDialog)**와
// **시간 선택 대화상자(TimePickerDialog)**를 띄우는 기능을 구현합니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //  시작될 때 가장 먼저 호출
        super.onCreate(savedInstanceState);
        // activity_main.xml 레이아웃 파일을 화면에 표시합니다.
        setContentView(R.layout.activity_main);

        // 레이아웃에 있는 TextView를 코드와 연결하여, 나중에 날짜나 시간을 표시할 때 사용합니다.
        textView1 = findViewById(R.id.textView1);
    }

    // 날짜 선택 대화상자를 띄웁니다.
    public void onBtn1Clicked(View v)
    {
        // 현재 날짜와 시간 정보를 담는 Calendar 객체를 생성합니다.
        final Calendar c = Calendar.getInstance();

        // Calendar 객체에서 현재 연도, 월, 일을 가져와 변수에 저장
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // 날짜 선택 대화상자를 만듭니다.
        // this: 현재 컨텍스트(액티비티)를 전달합니다.
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                // 대화상자에 머티리얼 3 디자인의 어두운 테마를 적용합니다.
                // 이 테마는 앱의 전체 테마와 상관없이 대화상자만 어두운 스타일로 보이게 합니다.
                com.google.android.material.R.style.Theme_Material3_Dark,
                // 사용자가 날짜를 선택했을 때 실행될 동작을 정의합니다.
                new DatePickerDialog.OnDateSetListener(){
                    //사용자가 날짜를 확정하면 이 메소드가 호출
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // 선택된 날짜를 "일/월/년" 형식으로 조합하여 textView1에 표시합니다.
                        // 여기서 monthOfYear는 0부터 시작하므로 1을 더해줍니다.
                        textView1.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" +year);
                    }
                },mYear,mMonth,mDay);
        // 생성한 날짜 선택 대화상자를 화면에 띄웁니다.
        datePickerDialog.show();
    }

    // 시간 선택 대화상자를 띄웁니다.
    public void onBtn2Clicked(View v)
    {
        // 현재 시간을 가져오기 위해 Calendar 객체를 다시 생성합니다.
        final Calendar c = Calendar.getInstance();

        // 현재 시와 분을 가져와 변수에 저장
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // 시간 선택 대화상자를 만듭니다
        // this: 현재 컨텍스트를 전달합니다.
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                // 대화상자에 머티리얼 3 디자인의 어두운 테마를 적용하며, 상단 액션바는 표시하지 않습니다.
                com.google.android.material.R.style.Theme_Material3_Dark_NoActionBar,
                // 사용자가 시간을 선택했을 때 실행될 동작을 정의합니다.
                new TimePickerDialog.OnTimeSetListener() {
                    // 사용자가 시간을 확정하면 이 메소드가 호출
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // 선택된 시간을 "시:분" 형식으로 조합하여 textView1에 표시합니다.
                        textView1.setText(hourOfDay + ":" + minute);
                    }
                    // false: 시간을 24시간제(true)가 아닌 12시간제(false)로 표시하도록 설정합니다.
                },mHour, mMinute, false);
        // 생성한 시간 선택 대화상자를 화면에 띄웁니다.
        timePickerDialog.show();
    }
}