package com.study.android.ex25_fragment2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

// 전체적인 기능은 ListFragment에서 사용자가 항목을 선택하면,
// 콜백(Callback) 인터페이스를 통해 ViewerFragment에 해당 이미지를 표시하도록 명령하는 것입니다.
public class MainActivity extends AppCompatActivity
        // MainActivity가 **ImageSelectionCallback**이라는 인터페이스를 구현했음을 선언합니다.
        // 이 선언 덕분에 ListFragment는 MainActivity를 ImageSelectionCallback 타입으로 인식하고,
        // 정의된 메서드를 호출할 수 있습니다.
        implements ImageSelectionCallback {
    private static final String TAG = "lecture";

    //  두 프래그먼트(ListFragment, ViewerFragment)를 제어하기 위한 객체 변수
    ListFragment listFragment;
    ViewerFragment viewerFragment;

    // 리스트에서 선택될 때 화면에 표시할 이미지들의 리소스 ID를 배열로 저장
    int[] images = {R.drawable.dream01, R.drawable.dream02, R.drawable.dream03};

    @Override
    // 처음 실행될 때 호출되는 메서드
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 프래그먼트를 관리하는 프래그먼트 매니저 객체를 얻습니다.
        FragmentManager manager = getSupportFragmentManager();
        // manager.findFragmentById(R.id.listFragment):
        //      activity_main.xml 레이아웃에 정의된 listFragment와 viewerFragment를 찾아 객체에 연결합니다.
        //      findFragmentById() 메서드는 정적으로 레이아웃에 포함된 프래그먼트를 찾는 데 사용됩니다.
        listFragment = (ListFragment)manager.findFragmentById(R.id.listFragment);
        viewerFragment = (ViewerFragment)manager.findFragmentById(R.id.viewerFragment);
    }

    @Override
    // 인터페이스에 정의된 필수 메서드로 이 메서드는 ListFragment에서 리스트 항목을 클릭했을 때 자동으로 호출
    // position 매개변수는 클릭된 리스트 항목의 위치(인덱스)를 전달받습니다.
    public void onImageSelected(int position) {
        // 전달받은 position을 이용해 images 배열에서 해당 이미지 리소스 ID를 찾아
        // viewerFragment의 setImage() 메서드를 호출합니다.
        // 이로써 ViewerFragment의 화면에 이미지가 변경되어 나타납니다.
        viewerFragment.setImage(images[position]);
    }
}