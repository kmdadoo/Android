package com.study.android.ex17_list3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// 이전 예제들보다 더 발전된 형태로, 항목의 디자인과 데이터 관리를 별도의 클래스로 분리하여 효율성을 높였습니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    String[] names = { "홍길동", "강감찬", "을지문덕", "양만춘", "유관순"}; // 리스트
    String[] ages = { "20", "25", "30", "35", "40"}; // 리스트
    // res/drawable 폴더에 있는 이미지 리소스 ID를 저장하는 정수 배열
    int[] images = {R.drawable.face1, R.drawable.face2, R.drawable.face3,
            R.drawable.face1, R.drawable.face2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 3단계
        // 직접 만든 MyAdapter 객체를 생성합니다.
        final MyAdapter adapter = new MyAdapter();

        // XML 레이아웃의 ListView 객체를 가져와 연결합니다.
        ListView listView1 = findViewById(R.id.listView1);

        // MyAdapter를 ListView에 연결하여 데이터와 뷰를 관리하게 합니다.
        listView1.setAdapter(adapter);

        // 4 단계
        // 항목 클릭 리스너를 설정하여 사용자가 항목을 클릭했을 때의 동작을 정의합니다.
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int posion, long arg3) {
                Toast.makeText(getApplicationContext(), "selected : " + names[posion],
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG, "이름 :" + names[posion]); // 디버그
            }
        });
    }

    // 1단계
    // BaseAdapter를 상속받아 ListView의 각 항목을 만드는 로직을 담당
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 이전 예제와 달리, 항목을 만들 때 TextView나 LinearLayout을 직접 생성하지 않습니다.
        // 대신 SingleItemView라는 별도의 클래스를 활용합니다.
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            LinearLayout layout = new LinearLayout(getApplicationContext());
//            layout.setOrientation(LinearLayout.VERTICAL);
//
//            TextView view1 = new TextView( getApplicationContext());
//            view1.setText(names[position]);
//            view1.setTextSize(40.0f);
//            view1.setTextColor(Color.BLUE);
//
//            layout.addView(view1);
//
//            TextView view2 = new TextView( getApplicationContext() );
//            view2.setText( ages[position]);
//            view2.setTextSize(40.0f);
//            view2.setTextColor(Color.RED);
//
//            layout.addView(view2);
//
//            return layout;

            // 2단계
            // SingleItemView 객체를 새로 생성합니다.
            // 이 객체는 이미 single_item_view.xml 레이아웃을 내부적으로 로딩하고 있습니다.
            SingleItemView view = new SingleItemView(getApplicationContext());

            // SingleItemView가 제공하는 메서드를 호출하여 이름, 나이, 이미지 데이터를 설정합니다.
            view.setName(names[position]);
            view.setAge(ages[position]);
            view.setImage(images[position]);

            // 모든 데이터가 설정된 SingleItemView 객체를 ListView의 한 항목으로 반환합니다.
            return  view;
        }
    }
}
// 이 코드는 이름, 나이, 그리고 얼굴 이미지를 포함하는 리스트를 만듭니다.
// 각 항목의 디자인과 데이터 설정은 SingleItemView라는 별도의 클래스에 정의되어 있어
// 코드가 깔끔하고 재사용성이 높아집니다.
// 사용자가 리스트의 항목을 클릭하면, 해당 항목의 이름이 토스트 메시지로 화면에 표시됩니다.
// 이는 복잡한 리스트 항목을 효율적으로 관리하는 안드로이드 개발의 일반적인 패턴을 보여줍니다.