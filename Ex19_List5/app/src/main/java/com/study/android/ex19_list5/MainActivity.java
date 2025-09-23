package com.study.android.ex19_list5;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

//    String[] names = { "홍길동", "강감찬", "을지문덕", "양만춘", "유관순"};
//    String[] ages = { "20", "25", "30", "35", "40"};
//    int[] images = {R.drawable.face1, R.drawable.face2, R.drawable.face3,
//            R.drawable.face1, R.drawable.face2};

    // 변수 선언
    SingleAdapter adapter;
    EditText editText1;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        adapter = new MyAdapter();
        adapter = new SingleAdapter(this);

        // SingleItem 객체 3개를 미리 생성하여 어댑터에 추가
        SingleItem item1 = new SingleItem("홍길동", "20", R.drawable.face1);
        adapter.addItem(item1);

        SingleItem item2 = new SingleItem("이순신", "25", R.drawable.face2);
        adapter.addItem(item2);

        SingleItem item3 = new SingleItem("김유신", "30", R.drawable.face3);
        adapter.addItem(item3);

        // R.id.listView1을 찾아 어댑터를 설정하고, 항목 클릭 리스너를 추가
        ListView listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int posion, long arg3) {
                SingleItem item = (SingleItem)adapter.getItem(posion);
                Toast.makeText(getApplicationContext(), "selected : " + item.getName(),
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG, "이름 :" + item.getName()); // 디버그
            }
        });

        // 변수에 연결
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
    }

    // 동적 데이터 추가: onBtn1Clicked 메소드에서 EditText에 입력된
    //      름과 나이를 가져와 SingleItem 객체를 만들고, 이를 어댑터에 추가합니다.
    public void onBtn1Clicked(View v){
        // editText1과 editText2의 텍스트를 가져옵니다.
        String inputName = editText1.getText().toString();
        String inputAge = editText2.getText().toString();

        // 새로운 SingleItem 객체를 생성하여 어댑터에 추가합니다.
        SingleItem item = new SingleItem(inputName, inputAge, R.drawable.face1);
        adapter.addItem(item);
        // adapter.notifyDataSetChanged()를 호출하여 어댑터에 데이터가
        // 변경되었음을 알리고, 리스트뷰를 자동으로 갱신합니다.
        // 이 함수가 없으면 화면에 새로 추가된 항목이 보이지 않습니다.
        adapter.notifyDataSetChanged();
    }
}