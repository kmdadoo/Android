package com.study.android.ex28_thread2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// 백그라운드 작업과 UI 업데이트를 분리함으로써, 앱이 멈추거나 '응답 없음'
// 오류가 발생하는 것을 방지합니다. 
// 특히, Handler와 Runnable을 함께 사용하는 이 방식은 안드로이드에서
// 스레드 간의 UI 업데이트를 위한 가장 일반적이고 효율적인 방법 중 하나입니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TextView textView1;
    Handler handler;
    ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 백그라운드 스레드가 UI 스레드에 작업을 전달할 수 있게 해줍니다.
        handler = new Handler();

        textView1 = findViewById(R.id.textView1);
        progressBar1 = findViewById(R.id.progressBar1);
    }

    public void onBtn1Clicked(View v) {
        // 버튼이 눌리면 RequestThread라는 새로운 스레드 객체를 만들고
        // thread.start()를 호출해 백그라운드 작업을 시작합니다.
        RequestThread thread = new RequestThread();
        thread.start();
    }

    // 백그라운드 스레드
    class RequestThread extends Thread{
        public void run(){
            for (int i=0; i<100; i++){
                Log.d(TAG, "Request Thread .. " + i);

                // 별도의 핸들러 클래스를 만들지 않고 바로 처리한다.
                // 다른 클래스에서 i 의 값이 변경될 수 있으므로
                // final 로 바꾸거나 다른 변수에 담아서 전달
                int index = i;
                // UI를 직접 수정할 수 없으므로, **handler.post()**를
                // 사용해 UI 스레드에 작업을 전달합니다.
                // handler.post(new Runnable() { ... })는 괄호 안의
                // Runnable 객체를 UI 스레드의 메시지 큐에 넣어줍니다.
                handler.post(new Runnable() {
                    @Override
                    // run() 메서드 안에 UI 변경 코드(textView1의 텍스트와 progressBar1의
                    // 진행률 업데이트)를 작성합니다. 이 코드는 UI 스레드에서 실행되기 때문에
                    // 안전하게 UI를 변경할 수 있습니다.
                    public void run() {
                        textView1.setText("Request Thread1 .. " + index);
                        progressBar1.incrementProgressBy(1);
                    }
                });

                try {
                    // 다운로드나 데이터 처리와 같은 시간이 걸리는 작업을 시뮬레이션한 것입니다.
                    Thread.sleep(200);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}