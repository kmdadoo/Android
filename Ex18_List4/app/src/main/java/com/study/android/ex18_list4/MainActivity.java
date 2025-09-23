package com.study.android.ex18_list4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// **커스텀 리스트뷰(Custom ListView)**를 구현하는 코드입니다.
// 사용자가 이름을 입력하고 '추가' 버튼을 누르면, 새로운 항목이 리스트에 동적으로 추가되는 기능을 보여줍니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

//    String[] names = { "홍길동", "강감찬", "을지문덕", "양만춘", "유관순"};
//    String[] ages = { "20", "25", "30", "35", "40"};
//    int[] images = {R.drawable.face1, R.drawable.face2, R.drawable.face3,
//            R.drawable.face1, R.drawable.face2};

    // 변수 선언
    MyAdapter adapter;
    EditText editText1;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MyAdapter();  // MyAdapter를 초기화합

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

    // BaseAdapter클래스를 상속받아 직접 만든 MyAdapter를 사용해 리스트뷰에 보여줄 데이터를 관리합니다.
    class MyAdapter extends BaseAdapter {
        //  데이터를 담을 리스트
        ArrayList<SingleItem> items = new ArrayList<>();

        // 리스트에 새로운 항목을 추가하는 메소드
        public void addItem(SingleItem item){
            items.add(item);
        }

        // 리스트의 전체 항목 수를 반환합니다.
        // 리스트뷰가 몇 개의 항목을 표시해야 하는지 알 수 있게 해줍니다.
        @Override
        public int getCount() {
            return items.size();
        }

        // 지정된 위치(position)의 항목을 반환
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        //  지정된 위치의 ID를 반환합니다. 여기서는 위치 자체를 ID로 사용
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 리스트의 각 항목(row)에 대한 뷰를 생성하고 데이터를 설정하는 가장 중요한 메소드
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // SingleItemView라는 커스텀 뷰를 새로 생성
            SingleItemView view = new SingleItemView(getApplicationContext());

            // 현재 위치에 맞는 SingleItem 데이터를 가져옵니다.
            SingleItem item = items.get(position);

            // 가져온 데이터를 커스텀 뷰에 설정합니다.
            view.setName(item.getName());
            view.setAge(item.getAge());
            view.setImage(item.getResId());

            // 완성된 뷰를 반환하면 리스트뷰가 해당 위치에 이 뷰를 표시
            return  view;
        }
    }
}