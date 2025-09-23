package com.study.android.ex22_recyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// **RecyclerView**를 사용하여 수평으로 스크롤되는 목록을 구현하는
// MainActivity 클래스입니다. 사용자가 버튼을 누르면 새로운 항목이
// 목록에 추가되고, 각 항목을 클릭하면 어떤 뷰를 클릭했는지에 따라 다른 메시지를 보여줍니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    // 변수 선언
    // RecyclerView에 데이터를 연결하고 관리하는 커스텀 어댑터
    SingerAdapter adapter;
    // 화면에 목록을 표시하는 RecyclerView 객체
    RecyclerView mRecyclerView;
    // 새로 추가되는 항목의 이름을 구분하기 위한 카운터 변수
    int nCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SingerAdapter 객체를 생성
        adapter = new SingerAdapter(this);

        // SingleItem 객체 3개를 미리 생성하여 어댑터에 추가
        SingerItem item1 = new SingerItem("홍길동1", "20", R.drawable.face1);
        adapter.addItem(item1);

        SingerItem item2 = new SingerItem("이순신2", "25", R.drawable.face2);
        adapter.addItem(item2);

        SingerItem item3 = new SingerItem("김유신3", "30", R.drawable.face3);
        adapter.addItem(item3);

        // XML 레이아웃 파일에 있는 RecyclerView를 찾아 변수에 연결
        mRecyclerView = findViewById(R.id.recyclerView1);
        // RecyclerView에 데이터를 제공할 어댑터를 설정
        mRecyclerView.setAdapter(adapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // RecyclerView의 항목들을 어떻게 배치할지 결정하는 레이아웃 매니저를 설정
        // LinearLayoutManager.HORIZONTAL: 항목들이 수평으로 나열되도록 설정합니다. (기본값은 VERTICAL입니다.)
        // true: 항목 순서를 역순으로 만듭니다. (기본값은 false)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, true));
        // 항목이 추가되었을 때, 자동으로 스크롤 위치를 가장 마지막 항목으로 이동시킵니다.
        mRecyclerView.scrollToPosition(adapter.getItemCount() -1);
        // 항목이 추가되거나 삭제될 때 기본 애니메이션 효과를 적용합니다.
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // SingerAdapter의 setItemClick 메소드를 호출하여 클릭 이벤트를 처리할 리스너를 등록합니다.
        adapter.setItemClick(new SingerAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                SingerItem item = (SingerItem) adapter.getItem(position);
                // 클릭된 뷰의 ID(view.getId())를 확인
                if (view.getId() == R.id.imageView1){
                    Toast.makeText(getApplicationContext(), "Image selected : " + item.getName(),
                            Toast.LENGTH_SHORT).show();
                }
                // 항목 전체)를 클릭하면 "Row selected" 토스트 메시지를 띄웁니다.
                if (view.getId() == R.id.itemLayout){
                    Toast.makeText(getApplicationContext(), "Row selected : " + item.getName(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        nCount = 1;
    }

    public void onBtnClicked(View v){
        nCount++;

        SingerItem item = new SingerItem("홍길동" + nCount, "20", R.drawable.face1 );
        // 새로 만들어진 SingerItem 객체를 어댑터의 데이터 리스트에 추가
        adapter.addItem(item);

        // 어댑터에 데이터가 변경되었음을 알려 RecyclerView가 화면을 갱신하도록 합니다.
        // 이 메소드가 호출되어야 새로 추가된 항목이 화면에 나타납니다.
        adapter.notifyDataSetChanged();
    }
}