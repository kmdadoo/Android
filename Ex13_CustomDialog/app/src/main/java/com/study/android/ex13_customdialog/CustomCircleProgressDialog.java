package com.study.android.ex13_customdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

// 사용자 지정(커스텀) 다이얼로그를 만드는 코드
public class CustomCircleProgressDialog extends Dialog {
    // 이 클래스의 생성자입니다. 다이얼로그를 생성할 때 context(맥락) 정보를 전달받습니다.
    // 이 context는 다이얼로그가 어디에 속해 있는지(예: 특정 액티비티)를 알려주는 역할을 합니다.
    public CustomCircleProgressDialog(@NonNull Context context)
    {
        super(context);
        // 기본 제목 표시줄(타이틀 바)을 없애달라고 요청하는 코드입니다.
        // 이렇게 하면 로딩 다이얼로그가 깔끔하게 보입니다.
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // No Title
        // 다이얼로그의 화면을 어떤 레이아웃으로 채울지 지정합니다.
        // 이 코드는 **custom_circle_progress.xml**이라는 이름의 레이아웃 파일을
        // 사용하여 다이얼로그의 화면을 구성하겠다는 의미입니다.
        // 보통 이 XML 파일 안에 회전하는 로딩 이미지를 담아둡니다.
        setContentView(R.layout.custom_circle_progress);
    }
}
