package com.study.android.ex27_thread1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// 백그라운드 스레드를 사용해 시간이 오래 걸리는 작업을 처리하고,
// 그 결과를 **핸들러(Handler)**를 통해 UI 스레드에 안전하게 반영하는 방법을 보여줍니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TextView textView1;
    Button button1;
    // 백그라운드 스레드와 UI 스레드 간의 통신을 담당하는 ProcessHandler 객체
    ProcessHandler handler; // 3단계

    @Override
    // 액티비티가 처음 생성될 때 호출되는 메서드
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 3 : 추가한 클래스를 이용한 핸들러 변수 만들기
        // ProcessHandler 객체를 생성해 handler 변수에 할당합니다.
        handler = new ProcessHandler();    // 3단계

        // 화면 레이아웃에서 찾아 변수에 연결
        textView1 = findViewById(R.id.textView1);
        button1 = findViewById(R.id.button1);
    }

    public void onBtn1Clicked(View v) {
        // 버튼을 비활성화
        // 이렇게 하면 사용자가 작업이 끝날 때까지 버튼을 여러 번 누르지 못하게 막을 수 있습니다.
        button1.setEnabled(false);  // 3단계

        // RequestThread라는 새로운 스레드 객체를 만들고 thread.start()를
        // 호출해 백그라운드 작업을 시작합니다.
        RequestThread thread = new RequestThread();
        thread.start();
    }

    // 백그라운드에서 실행되는 작업을 담당
    class RequestThread extends Thread{
        // 0부터 19까지 20번 반복하는 작업을 수행
        public void run(){
            for (int i=0; i<20; i++){
                Log.d(TAG, "Request Thread .. " + i);

                // 1 : 쓰레드에서 메인쓰레드의 객체로의 접근은 불가능
                // 백그라운드 스레드가 UI를 직접 수정할 수 없기 때문입니다.
                // 이렇게 직접 접근하면 CalledFromWrongThreadException 오류가 발생
//                textView1.setText("Request Thread1 .. " + i);   // 2단계

                // 4 : 핸들러에 전달할 메시지 작성
                // handler.obtainMessage()를 호출하여 Message 객체를 얻습니다.
                // 이 Message 객체는 Handler에게 보낼 데이터를 담는 용도입니다.
                Message msg = handler.obtainMessage();

                // 현재 반복 횟수(i)에 대한 정보를 담습니다.
                Bundle bundle = new Bundle();
                bundle.putString("data1", "Request Thread1.. "+ i);
                bundle.putString("data2", String.valueOf(i));
                msg.setData(bundle);

                // 메시지를 UI 스레드로 보냅니다.
                handler.sendMessage(msg);

                // 각 반복마다 1초씩 작업을 멈추게(대기하게) 합니다.
                // 이는 실제로는 네트워크 통신이나 복잡한 계산과 같은 오래 걸리는
                // 작업이 진행되는 것을 시뮬레이션한 것입니다.
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    // 2 : 핸들러 클래스 작성
    // Handler 클래스를 상속받아 구현된 클래스로,
    // 백그라운드 스레드가 보낸 메시지를 처리합니다.
    class ProcessHandler extends Handler {  // 3 단계
        @Override
        // RequestThread가 보낸 Message 객체를 받으면 이 메서드가 호출됩니다.
        public void handleMessage(Message msg) {
            // 5 : 핸들러에 메시지가 전달되면 원하는 동작 처리
            // Bundle 객체에 저장된 데이터를 가져옵니다.
            Bundle bundle = msg.getData();
            String data1 = bundle.getString("data1");
            String data2 = bundle.getString("data2");

            // textView1의 텍스트를 업데이트하여 UI를 변경
            textView1.setText(data1);

            // **data2의 값이 "19"**라면, 모든 작업이 완료된 것으로 판단하고
            // textView1의 텍스트를 "쓰레드 테스트"로 바꾸고, 처음에 비활성화했던
            // button1을 다시 활성화하여 사용자가 다음 작업을 시작할 수 있도록 합니다.
            if (data2.equals("19")){
                textView1.setText("쓰레드 테스트");
                button1.setEnabled(true);
            }else {
                button1.setEnabled(false);
            }
        }
    }
}