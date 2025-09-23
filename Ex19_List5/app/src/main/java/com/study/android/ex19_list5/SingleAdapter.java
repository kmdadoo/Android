package com.study.android.ex19_list5;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;

// *커스텀 리스트뷰(Custom ListView)**를 만드는 데 사용되는 어댑터(Adapter) 클래스입니다.
// 이전 코드(ex18_list4)의 MyAdapter와 거의 같지만, getView() 메소드에 중요한 최적화 기법이 추가되었습니다.

// BaseAdapter를 상속받아 커스텀 어댑터의 기능을 구현
public class SingleAdapter extends BaseAdapter {
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
        view.setAge(item.getAge());
        view.setImage(item.getResId());

        // 디버깅 용도로, getView() 메소드가 언제 호출되는지 보여주는 메시지입니다.
        // 스크롤할 때마다 계속 호출되는 것을 확인할 수 있습니다.
        Toast.makeText(context, "getView() called : " + position,
                Toast.LENGTH_SHORT).show();

        // 완성된 뷰를 반환하면 리스트뷰가 해당 위치에 이 뷰를 표시
        return  view;
    }
}
