package com.study.android.ex22_recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

//  **RecyclerView**를 사용하여 동적인 목록을 만드는 어댑터(Adapter) 클래스입니다.
//  RecyclerView는 ListView보다 훨씬 효율적으로 목록을 관리하며,
//  특히 복잡한 레이아웃이나 대량의 데이터를 다룰 때 성능이 뛰어납니다.

// RecyclerView.Adapter<SingerAdapter.SingerItemViewHolder>:
//      이 클래스가 RecyclerView 어댑터임을 선언합니다. < > 안에 있는
//      SingerItemViewHolder는 이 어댑터가 사용할 뷰 홀더의 타입을 지정합니다.
public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerItemViewHolder> {
    // RecyclerView.ViewHolder: 이 클래스를 상속받아 뷰 홀더를 만듭니다.
    //      뷰 홀더는 각 리스트 항목의 뷰 객체들을 담아두는 "상자" 역할을 합니다.
    public class SingerItemViewHolder extends RecyclerView.ViewHolder{
        protected LinearLayout itemLayout;
        protected TextView textView1;
        protected TextView textView2;
        protected ImageView imageView1;

        // 생성자
        public SingerItemViewHolder(View view){
            super(view);
            itemLayout = view.findViewById(R.id.itemLayout);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            imageView1 = view.findViewById(R.id.imageView1);
        }
    }

    // RecycleView에 새로운 데이터를 보여주기 위해 필요한 ViewHolder를 생성해야 할 때 호출된다.
    // 뷰 홀더가 필요할 때 호출
    @Override
    public SingerItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // LayoutInflater.from(...).inflate(...)를 사용해 singer_item_view.xml
        // 레이아웃 파일을 메모리 상의 실제 뷰 객체로 만듭니다.
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.singer_item_view, viewGroup, false);

        // 새로 만든 뷰로 SingerItemViewHolder 객체를 생성하여 반환
        SingerItemViewHolder viewHolder = new SingerItemViewHolder(view);
        return viewHolder;
    }

    // ------------------------------------------------------

    // 어댑터가 필요로 하는 컨텍스트(앱 정보)와 데이터를 담을 리스트를 선언합니다.
    Context context;
    ArrayList<SingerItem> items = new ArrayList<>();

    // 생성자에서 Context를 받아 초기화
    public SingerAdapter(Context context){
        this.context = context;
    }

    // 외부에서 SingerItem 객체를 받아 items 리스트에 추가하는 메소드
    public void addItem(SingerItem item){
        items.add(item);
    }

    // ------------------------------------------------------

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    public Object getItem(int position){
        return items.get(position);
    }

    // ------------------------------------------------------
    // 아이템 클릭시 실행 함수
    // onClick 메소드를 정의하는 인터페이스입니다.
    // 이 인터페이스를 구현하여 RecyclerView 외부(예: MainActivity)에서 클릭 이벤트를 처리할 수 있게 합니다.
    public interface ItemClick{
        public void onClick(View view, int position);
    }
    private ItemClick itemClick;

    // 아이템 클릭시 실행 함수 등록 함수
    // 외부에서 구현한 클릭 이벤트 핸들러를 등록하는 메소드입니다.
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    // Adapter의 특성 위치(position)에 있는 데이터를 보여줘야 할때 호출된다.
    // 특정 위치(position)의 데이터를 뷰 홀더에 연결할 때 호출됩니다.
    // 스크롤을 통해 항목이 화면에 나타날 때마다 반복적으로 호출됩니다.
    @Override
    public void onBindViewHolder(@NonNull SingerItemViewHolder viewholder, int position) {
        ArrayList<SingerItem> items2 = items;
        // items 리스트를 복사하여 items2를 만들고, 이 리스트를 뒤집습니다.
        // 이로 인해 리스트에 새로운 항목을 추가할 때마다 가장 최신 항목이 맨 위에 오게 됩니다.
        Collections.reverse(items2);

        // viewholder.textView1.setText(...)와 같이 position에 해당하는
        // 데이터를 뷰 홀더의 뷰에 설정(바인딩)합니다.
        viewholder.textView1.setText(items2.get(position).getName());
        viewholder.textView2.setText(items2.get(position).getAge());
        viewholder.imageView1.setImageResource(items2.get(position).getResId());

        final int num = position;
        // 클릭 리스너: imageView1과 itemLayout에 setOnClickListener를 설정합니다.
        // 사용자가 이 뷰들을 클릭하면, 외부에서 등록한 itemClick 리스너를 호출해
        // 이벤트를 전달합니다. num = position을 사용하여 클릭된 항목의 위치 정보를 전달합니다.
        viewholder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null){
                    itemClick.onClick(v, num);
                }
            }
        });

        viewholder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null){
                    itemClick.onClick(v, num);
                }
            }
        });
    }
}
