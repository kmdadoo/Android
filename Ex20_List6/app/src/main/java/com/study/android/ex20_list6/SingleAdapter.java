package com.study.android.ex20_list6;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

// *커스텀 리스트뷰(Custom ListView)**를 만드는 데 사용되는 어댑터(Adapter) 클래스입니다.
// 이전 버전들과 마찬가지로 리스트에 데이터를 연결하고 관리하는 역할을 하지만,
// 한 단계 더 나아가 리스트 항목 안의 버튼에 클릭 이벤트 기능을 추가한 것이 특징입니다.

// BaseAdapter를 상속받아 커스텀 어댑터의 기능을 구현
public class SingleAdapter extends BaseAdapter {
    private static final String TAG = "lecture";
    // 안드로이드 애플리케이션의 현재 상태(정보)를 담고 있는 객체입니다.
    // 뷰를 생성하거나 토스트 메시지를 띄울 때 필요합니다.
    Context context;
    // 리스트뷰에 표시할 SingleItem 객체들을 담아두는 리스트입니다.
    ArrayList<SingleItem> items = new ArrayList<>();

    // 생성자에서 Context를 전달받아 저장
    public SingleAdapter(Context context) {
        this.context = context;
    }

    // 리스트에 새로운 SingleItem 객체를 추가하는 메소드
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
//        SingleItemView view = new SingleItemView(context);

        SingleItemView view = null;
        if (convertView == null)
        {
            // 뷰가 아직 생성되지 않았을 때 (처음 화면에 나타날 때)를 의미
            // 이 경우, **new SingleItemView(context);**를 호출해 새로운 뷰를 만듭니다.
            view = new SingleItemView(context);
        }else {
            // 재활용할 수 있는 뷰가 있을 때 (스크롤하면서 화면 밖으로 나간 뷰가 있을 때)를 의미합니다.
            // 이 경우, convertView를 캐스팅하여 그대로 재활용합니다.
            // 새로운 뷰를 만들 필요가 없기 때문에 메모리와 CPU를 절약할 수 있습니다.
            // 이것이 바로 리스트뷰 성능 최적화의 핵심입니다.
            view = (SingleItemView) convertView;
        }

        // 현재 위치에 맞는 SingleItem 데이터를 가져옵니다.
        SingleItem item = items.get(position);

        // 가져온 데이터를 커스텀 뷰에 설정합니다.
        view.setName(item.getName());
        view.setTel(item.getTelNum());
        view.setImage(item.getResId());

        // ******************************************************************
        // 리스트뷰 안의 버튼 클릭 이벤트 처리
        // SingleItemView 내부의 R.id.button1을 찾아옵니다.
        Button button1 = view.findViewById(R.id.button1);
        // 이 코드는 리스트뷰의 항목 클릭 이벤트와 버튼 클릭 이벤트가 충돌하는 것을 방지합니다.
        // 이 설정이 없으면 버튼 클릭이 정상적으로 동작하지 않을 수 있습니다.
        button1.setFocusable(false);
        // 버튼이 클릭될 때 실행될 동작을 정의
        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 클릭된 항목의 전화번호를 가져와 tel: 형식의 URI 문자열로 만듭니다.
                String str = "tel:" + item.getTelNum();
                Log.d(TAG, str);
                // ACTION_VIEW 인텐트를 생성하여 전화 걸기 앱을 호출할 준비를 합니다.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
                // 준비된 intent를 실행하여 실제 전화 걸기 화면을 띄웁니다.
                context.startActivity(intent);
            }
        });

        // 완성된 뷰를 반환하면 리스트뷰가 해당 위치에 이 뷰를 표시
        return  view;
    }
}
