package com.study.android.ex26_tabbar;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

// 사용자가 탭을 클릭하는 동작에 따라 화면 내용을 동적으로 변경하는 멀티 화면 구조를 구현한 예제
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TabLayout tabLayout;

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;

    @Override
    // 앱이 시작될 때 호출되는 초기화 메서드
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // activity_main.xml 레이아웃 파일에 정의된 TabLayout 위젯을 찾아 객체에 연결합니다.
        tabLayout = findViewById(R.id.tabMenu);

        // 객체를 생성
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();

        // 프래그 먼트 컨테이너를 찾아와서 프래그먼트 담는다.
        // 프래그먼트 컨테이너 디자인시에는 비어 있어도 된다.
        // 앱이 처음 시작될 때, container라는 ID를 가진 레이아웃에 fragment1을 올려 화면에 표시합니다.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment1).commit();

        // 탭이 선택될 때의 이벤트를 처리하는 리스너를 추가합니다.
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            // 탭이 선택될 때마다 호출
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "POS:"+tab.getPosition());

                // 선택된 탭의 위치(인덱스)에 따라 다른 동작을 수행
                switch (tab.getPosition()){
                    // 첫 번째 탭(인덱스 0)이 선택되면, container 레이아웃에 **fragment1**을 교체
                    case 0:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container, fragment1).commit();
                        break;
                    case 1:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container, fragment2).commit();
                        break;
                    case 2:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container, fragment3).commit();
                        break;
                    case 3:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container, fragment4).commit();
                        break;
                    default:
                }
            }

            @Override
            // 탭이 선택 해제
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            // 선택된 탭이 다시 클릭될 때
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}