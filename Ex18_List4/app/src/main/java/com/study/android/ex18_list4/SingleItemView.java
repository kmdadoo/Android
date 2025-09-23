package com.study.android.ex18_list4;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

// 리스트뷰(ListView)나 리사이클러뷰(RecyclerView)의 각 항목을 독립적으로 디자인하고
// 관리하기 위해 만든 클래스입니다.
// 이 클래스는 **single_item_view.xml**이라는 레이아웃 파일을 사용하여 하나의 항목(item) 뷰를 구성하는 역할을 합니다.
public class SingleItemView extends LinearLayout {
    // 멤버 변수 선언
    TextView textView1;
    TextView textView2;
    ImageView imageView1;

    // 생성자
    public SingleItemView(Context context){
        super(context); // 부모 클래스인 LinearLayout의 생성자를 호출

        // LayoutInflater는 XML 레이아웃 파일을 실제 뷰 객체로 변환해주는 역할을 합니다. 이 객체를 가져옵니다.
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //  가장 중요한 부분
        // R.layout.single_item_view: 이 코드가 single_item_view.xml 레이아웃 파일을 메모리에 로딩합니다.
        //this, true: 로딩된 뷰를 현재 클래스(SingleItemView)에 자동으로 추가합니다.
        //      이로써 single_item_view.xml에 정의된 모든 UI 요소가 SingleItemView 객체의 일부가 됩니다.
        inflater.inflate(R.layout.single_item_view, this, true);

        // 로딩된 레이아웃에서 각각의 뷰들을 찾아와 멤버 변수에 연결합니다.
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        imageView1 = findViewById(R.id.imageView1);
    }

    // 외부에서 문자열(이름)을 받아와 textView1에 설정
    public void setName(String name){
        textView1.setText(name);
    }

    // 외부에서 문자열(나이)을 받아와 textView2에 설정
    public void setAge(String age){
        textView2.setText(age);
    }

    // 외부에서 이미지 리소스 ID(int)를 받아와 imageView1에 설정
    public void setImage(int imaNum) {
        imageView1.setImageResource(imaNum);
    }
}
