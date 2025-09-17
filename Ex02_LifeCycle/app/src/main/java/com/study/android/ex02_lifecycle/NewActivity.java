package com.study.android.ex02_lifecycle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// 액티비티 생명주기(Activity Lifecycle)를 보여주는 예제 코드입니다.
// 이 코드는 액티비티가 생성, 시작, 재개, 일시정지, 중단, 그리고 소멸될 때마다 특정 메서드가 호출되는 것을 시각적으로 확인하게 해줍니다.
public class NewActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    // 액티비티가 처음 생성될 때 호출
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_new.xml 레이아웃 파일을 화면에 표시합니다.
        setContentView(R.layout.activity_new);

        Toast.makeText(getApplicationContext(),"onCreate() 호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate");
    }

    // onStart(): 액티비티가 화면에 보이기 시작할 때 호출됩니다.
    // 사용자가 화면을 볼 수 있지만 아직 상호작용은 할 수 없습니다.
    @Override
    protected void onStart() {
        super.onStart();

        Toast.makeText(getApplicationContext(),"onStart() 호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart");
    }

    // onResume(): 액티비티가 화면의 가장 위에 올라와서 사용자와 상호작용할 수 있을 때 호출됩니다.
    // 액티비티가 실행 중인 상태입니다.
    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(getApplicationContext(),"onResume() 호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onResume");
    }

    // onPause(): 다른 액티비티가 화면 위로 올라와 현재 액티비티가 일시정지될 때 호출됩니다.
    // 부분적으로 가려져 있거나, 대화상자 같은 것이 나타났을 때 발생합니다.
    @Override
    protected void onPause() {
        super.onPause();

        Toast.makeText(getApplicationContext(),"onPause() 호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPause");
    }

    // onStop(): 액티비티가 더 이상 화면에 보이지 않게 될 때 호출됩니다.
    // 예를 들어, 다른 액티비티로 완전히 넘어갔을 때입니다.
    @Override
    protected void onStop() {
        super.onStop();

        Toast.makeText(getApplicationContext(),"onStop() 호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStop");
    }

    // onDestroy(): 액티비티가 소멸되기 직전에 호출됩니다.
    //      finish() 메서드 호출
    //      시스템 메모리가 부족할 때
    //      이때 모든 리소스를 해제해야 합니다.
    @Override
    protected void onDestroy() {
        super.onDestroy();

        Toast.makeText(getApplicationContext(),"onDestroy() 호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy");
    }



    public void onButton2Clicked(View v)
    {
        Log.d(TAG, "버튼2 클릭");
        // finish();: 현재 액티비티를 종료합니다.
        // 이 메서드가 호출되면 onPause(), onStop(), onDestroy() 순서로 생명주기 메서드들이 실행되며,
        // 최종적으로 화면이 사라지게 됩니다.
        finish();
    }
}


// 새로운 전화가 걸려오면 NewActivity의 onPause() 메서드가 가장 먼저 호출됩니다.
// 전화가 걸려왔을 때의 생명주기 흐름
// 안드로이드에서 현재 실행 중인 앱이 다른 활동(Activity)에 의해 가려지거나 방해받을 때,
// 현재 액티비티는 일시 정지(paused) 상태가 됩니다. 전화 수신 화면은 새로운 액티비티이기 때문에,
// NewActivity는 잠시 멈추게 됩니다. 이 과정에서 다음과 같은 순서로 메서드가 실행됩니다.
//
// 1. onPause(): 전화가 걸려오면, NewActivity는 더 이상 사용자와 상호작용할 수 없는 상태가 됩니다.
//      이 메서드는 다른 액티비티가 현재 액티비티를 가리기 시작할 때 호출됩니다. 여기서 진행 중이던 애니메이션을 멈추거나,
//      데이터 저장 등 가벼운 작업을 처리해야 합니다.
// 2. onStop(): 만약 전화 수신 화면이 NewActivity를 완전히 가려버리면,
//      onPause() 다음에 onStop()이 호출됩니다.
//      이 시점에서 NewActivity는 화면에 보이지 않는 상태가 됩니다.
//
// 전화를 끊고 다시 앱으로 돌아오면, onRestart() -> onStart() -> onResume() 순서로 메서드가 다시
// 호출되면서 NewActivity가 이전 상태로 복원됩니다.