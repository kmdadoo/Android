package com.study.android.ex04_layout2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//  레이아웃과 이미지 전환 기능을 구현합니다.
//  이 앱은 세 개의 레이아웃(layout1, layout2, layout3)과 하나의 이미지 뷰(imageView1)를 포함하며,
//  버튼 클릭에 따라 어떤 레이아웃을 보이게 할지, 또는 어떤 이미지를 표시할지를 결정합니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    ImageView imageView1;
    boolean imageSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //  앱이 처음 실행될 때 호출
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main): activity_main.xml 파일에 정의된 화면 레이아웃을 이 액티비티에 연결합니다.
        setContentView(R.layout.activity_main);

        // 이미지가 선택되었는지 여부를 추적하는 불린(boolean) 변수를 false로 초기화합니다.
        imageSelected = false;

        // findViewById(): activity_main.xml에 정의된 뷰(View)들을 찾아 각각
        // layout1, layout2, layout3, imageView1 변수에 할당합니다.
        // 이 과정을 통해 코드에서 각 뷰를 제어할 수 있게 됩니다.
        layout1 = findViewById(R.id.Layout1);
        layout2 = findViewById(R.id.Layout2);
        layout3 = findViewById(R.id.Layout3);

        imageView1 = findViewById(R.id.imageView1);
    }

    public void onBtn1Clicked(View v)
    {
        layout1.setVisibility(View.VISIBLE);    // layout1을 보이게 합니다.
        layout2.setVisibility(View.GONE);   // layout2를 숨깁니다.
        layout3.setVisibility(View.GONE);   // layout3를 숨깁니다.
    }

    public void onBtn2Clicked(View v)
    {
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);    // 보이게 한다.
        layout3.setVisibility(View.GONE);
    }

    public void onBtn3Clicked(View v)
    {
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.VISIBLE);     // 보이게 한다.
    }

    // 이미지 전환 기능
    // 이 메서드는 버튼 클릭 시 자동차(car)와 집(house) 이미지를 번갈아 표시합니다.
    public void onBtn6Clicked(View v)
    {
        if (imageSelected)  // imageSelected 변수가 true인지 확인합니다.
        {
            // if 조건이 true일 경우: 현재 집 이미지가 표시된 상태이므로,
            // imageView1.setImageResource(R.drawable.car)를 통해
            // 이미지를 자동차로 변경하고 imageSelected를 false로 바꿉니다.
            imageView1.setImageResource(R.drawable.car);
            imageSelected = false;
        } else {
            // 현재 자동차 이미지가 표시된 상태이므로,
            // imageView1.setImageResource(R.drawable.house)를 통해
            // 이미지를 집으로 변경하고 imageSelected를 true로 바꿉니다.
            imageView1.setImageResource(R.drawable.house);
            imageSelected = true;
            // setImageResource() 메서드는 R.drawable 디렉터리에 저장된
            // 이미지를 가져와 ImageView에 설정하는 데 사용됩니다.
        }
    }
}