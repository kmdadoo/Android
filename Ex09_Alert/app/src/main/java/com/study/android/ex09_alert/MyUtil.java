package com.study.android.ex09_alert;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

// **알림 대화상자(AlertDialog)**를 표시하는 MyUtil이라는 유틸리티 클래스예요.
// 이 클래스는 두 개의 AlertShow 메소드를 가지고 있는데, 각각 다른 방식으로 알림창을 띄우는 기능을 제공합니다.
public class MyUtil {
	// 메시지만 있는 간단한 알림창을 만들 때 사용
	public static void AlertShow(Context context, String msg) {
		// 알림창을 생성하기 위한 Builder 객체를 만들어요.
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// 사용자가 알림창 바깥을 터치하거나 뒤로가기 버튼을 눌러도 알림창이 닫히지 않도록 설정합니다.
        builder.setCancelable(false);
		// 알림창에 표시할 메시지(msg)를 설정해요. 메시지 앞뒤에 줄바꿈(\n)을 추가해 여백을 줍니다.
        builder.setMessage("\n"+msg+"\n");
		// 알림창에 **'확인'**이라는 긍정 버튼을 추가합니다. 버튼을 눌렀을 때 특별한 동작은 없도록 null로 설정했어요.
        builder.setPositiveButton("확인", null);

		// 설정한 내용으로 알림 대화상자 객체를 생성
        AlertDialog alert = builder.create();
		// 생성한 알림 대화상자를 화면에 표시
        alert.show();

		// AlertDialog 의  가운데 정렬을 위한.. setting
		// show()를 호출한 후에 알림창의 메시지 뷰(TextView)를 가져옵니다.
		// android.R.id.message는 안드로이드 시스템이 미리 정의해 둔 메시지 뷰의 ID예요.
		TextView messageView = (TextView)alert.findViewById(android.R.id.message);
		// 가져온 메시지 뷰의 텍스트를 가운데 정렬하도록 설정합니다.
		messageView.setGravity(Gravity.CENTER);
    }		

	// 제목과 메시지, 아이콘이 포함된 알림창을 만들 때 사용
	public static void AlertShow(Context context, String msg, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// 알림창의 제목 왼쪽에 경고 아이콘을 추가합니다.
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		// 알림창의 제목을 title 매개변수로 설정
		builder.setTitle(title);
		builder.setCancelable(false);
		builder.setMessage("\n"+msg+"\n");
		builder.setPositiveButton("확인", null);

        AlertDialog alert = builder.create();
        alert.show();

        // AlertDialog 의  가운데 정렬을 위한.. setting
		// show()를 호출한 후에 알림창의 메시지 뷰(TextView)를 가져옵니다.
		TextView messageView = (TextView)alert.findViewById(android.R.id.message);
		messageView.setGravity(Gravity.CENTER);
    }		

}
