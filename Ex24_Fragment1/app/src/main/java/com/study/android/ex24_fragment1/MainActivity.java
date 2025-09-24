package com.study.android.ex24_fragment1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

// 전체적인 흐름은 앱이 시작될 때 첫 번째 프래그먼트를 화면에 표시하고,
// 다른 프래그먼트에서 요청이 오면 화면을 전환하는 역할을 합니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    //  객체 선언
    Page1Fragment fragment1;
    Page2Fragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 객체를 생성
        fragment1 = new Page1Fragment();
        fragment2 = new Page2Fragment();

        // **프래그먼트 매니저(FragmentManager)**를 사용해 프래그먼트 트랜잭션(transaction)을 시작합니다.
        getSupportFragmentManager().beginTransaction()
                //.replace(R.id.container, fragment1): **R.id.container**라는 ID를 가진 레이아웃에
                //      **fragment1**을 교체하여 화면에 표시합니다.
                //.commit(): 이 트랜잭션을 최종적으로 실행합니다. 이 코드를 통해 앱이 처음 시작될 때
                //      Page1Fragment가 화면에 나타납니다.
                .replace(R.id.container, fragment1).commit();
    }

    // 이 메서드는 다른 프래그먼트(예: Page1Fragment의 버튼 클릭)에서 화면 전환을 요청할 때 호출됩니다.
    public void onFragmentChange(int index){
        if (index == 0){
            // 전달받은 인덱스 값이 0이면, 현재 화면의 fragment1을 **fragment2**로 교체합니다.
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment2).commit();
        } else if (index == 1) {
            // 전달받은 인덱스 값이 1이면, 현재 화면의 fragment2를 **fragment1**으로 다시 교체합니다.
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment1).commit();
        }
    }
}