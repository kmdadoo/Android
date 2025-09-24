package com.study.android.ex23_pager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

// 이 코드는 안드로이드 앱에서 ViewPager를 사용해 여러 화면을 좌우로 스와이프하며 볼 수 있게 구현한 예제입니다.
// 전체적인 기능은 "홍길동", "강감찬", "을지문덕"이라는 세 개의 텍스트를 각각 다른 페이지에 표시하고,
// 버튼을 눌러 특정 페이지로 이동하는 것입니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    ViewPager pager1;

    // 앱이 시작될 때 호출되는 메서드로, UI 레이아웃을 설정하고 ViewPager 객체를 찾습니다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pager1 = findViewById(R.id.pager1);
        // 기본3개 : 다음으로 숫자 조정
        // ViewPager가 현재 화면에 보이지 않는 페이지를 미리 몇 개 로딩할지 설정합니다.
        // 여기서는 양옆으로 3개씩 총 3개를 미리 로딩해 부드러운 전환을 돕습니다.
        pager1.setOffscreenPageLimit(3);

        MyPagerAdapter adapter = new MyPagerAdapter();
        //  ViewPager에 데이터를 제공하고 화면을 생성하는 MyPagerAdapter 클래스의 인스턴스를 연결합니다.
        pager1.setAdapter(adapter);
    }

    // 각 버튼을 클릭했을 때 호출되는 메서드입니다.
    //pager1.setCurrentItem(0), (1), (2):
    //      ViewPager가 특정 페이지(0, 1, 2)로 즉시 이동하도록 설정합니다.
    public void onBtn1Clicked(View v) {
        pager1.setCurrentItem(0);
    }

    public void onBtn2Clicked(View v) {
        pager1.setCurrentItem(1);
    }

    public void onBtn3Clicked(View v) {
        pager1.setCurrentItem(2);
    }

    // 이 클래스는 ViewPager의 핵심으로, 각 페이지의 데이터를 관리하고 화면을 만듭니다.
    class MyPagerAdapter extends PagerAdapter {
        String[] names = { "홍길동", "강감찬", "을지문덕"};

        // ViewPager가 만들어야 할 총 페이지의 수를 반환합니다.
        // names.length를 통해 배열에 있는 이름의 개수만큼 페이지를 만듭니다.
        @Override
        public int getCount() {
            return names.length;
        }

        // 뷰가 ViewPager에서 생성한 객체와 일치하는지 확인하는 필수 메서드입니다.
        // 일반적으로 view.equals(obj)를 반환하면 됩니다.
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view.equals(obj);
        }

        // 더 이상 사용하지 않는 페이지를 제거할 때 호출됩니다.
        // pager1.removeView((View) object)를 통해 메모리 관리를 위해 뷰를 삭제합니다.
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);

            pager1.removeView((View) object);
        }

        // 새로운 페이지를 만들어야 할 때 호출
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView view1 = new TextView( getApplicationContext() );
            //  배열의 위치에 맞는 이름을 가져와 TextView에 설정합니다.
            view1.setText(names[position]);
            view1.setTextSize(40.0f);
            view1.setTextColor(Color.BLUE);

            // LinearLayout에 TextView를 추가하고, 최종적으로 ViewPager에 이 레이아웃을 추가합니다.
            layout.addView(view1);
            pager1.addView(layout, position);

            // 반환값으로 만든 레이아웃 객체를 넘겨줍니다.
            return layout;
        }
    }
}