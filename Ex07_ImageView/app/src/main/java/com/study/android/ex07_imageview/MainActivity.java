package com.study.android.ex07_imageview;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// 두 개의 ImageView 위젯을 사용하여 이미지를 위아래로 교체하는 기능을 구현합니다.
// '이미지 올리기' 버튼과 '이미지 내리기' 버튼을 클릭하면, 각각의 ImageView에
// 표시된 이미지가 서로 교체되는 방식으로 동작합니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";


    ImageView imageView1;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // 앱 초기화
        super.onCreate(savedInstanceState);
        // activity_main.xml에 정의된 레이아웃을 화면에 표시
        setContentView(R.layout.activity_main);

        // 이미지 뷰
        // XML 레이아웃 파일에 있는 ID가 imageView1인 ImageView 객체를 찾아 imageView1 변수에 할당
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);

        // imageView1에 background1 이미지를 설정합니다.
        imageView1.setImageResource(R.drawable.background1);
        // imageView2의 이미지를 '0'으로 설정합니다. 여기서 '0'은 이미지를 없앤다는 의미로,
        // imageView2는 초기 상태에서 아무 이미지도 표시하지 않게 됩니다.
        imageView2.setImageResource(0);

        // 뷰를 다시 그리도록 요청합니다.
        // 이 코드는 변경사항을 즉시 화면에 반영하는 역할을 합니다.
        imageView1.invalidate();
        imageView2.invalidate();
    }

    // imageDown() 메서드를 호출
    public void onBtn1Clicked(View v){
        imageDown();
    }

    //  이미지 올리기
    public void onBtn2Clicked(View v){
        imageUp();
    }

    // 위쪽 ImageView(imageView1)의 이미지를 제거합니다.
    //  아래쪽 ImageView(imageView2)에 background1 이미지를 설정합니다.
    //결과적으로 이미지가 imageView1에서 imageView2로 "내려가는" 것처럼 보입니다.
    private void imageDown() {
        imageView1.setImageResource(0);
        imageView2.setImageResource(R.drawable.background1);

        imageView1.invalidate();
        imageView2.invalidate();
    }

    // 위쪽 ImageView(imageView1)에 다시 background1 이미지를 설정합니다.
    // 아래쪽 ImageView(imageView2)의 이미지를 제거합니다.
    //결과적으로 이미지가 imageView2에서 imageView1로 "올라가는" 것처럼 보입니다.
    private void imageUp() {
        imageView1.setImageResource(R.drawable.background1);
        imageView2.setImageResource(0);

        imageView1.invalidate();
        imageView2.invalidate();
    }
}