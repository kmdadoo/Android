package com.study.android.ex29_asynctask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 안드로이드 앱에서 **ExecutorService**와 **Handler**를 사용하여
// 백그라운드에서 진행되는 작업(여기서는 프로그레스바 증가)을 처리하고,
// 그 결과를 UI에 안전하게 반영하는 방법을 보여줍니다.
// 이는 기존의 AsyncTask를 대체하는 현대의 안드로이드 비동기 처리 방식입니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    // 화면에 보이는 프로그레스바 객체
    ProgressBar mProgress1;
    // 현재 프로그레스바의 진행 상태(0부터 100까지)를 저장하는 변수
    int mProgressStatus = 0;

    // 백그라운드 스레드에서 작업을 실행하고 관리하는 객체
    ExecutorService executorService;
    // 백그라운드 스레드에서 메인(UI) 스레드로 데이터를 전달하여 UI를 업데이트하는 데 사용
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress1 = findViewById(R.id.progressBar1);

        // Executors.newSingleThreadExecutor()로 초기화되어,
        // 한 번에 하나의 작업만 순차적으로 실행되도록 설정됩니다.
        executorService = Executors.newSingleThreadExecutor();
        // 초기화되어 메인 스레드에 연결
        handler = new Handler(Looper.getMainLooper());
    }

    public void onBtn1Clicked(View v) {
        // 버튼이 클릭되면, executorService.execute(...)를 호출하여 괄호
        // 안의 Runnable 객체를 백그라운드 스레드에 제출하고 즉시 실행합니다.
        executorService.execute(() -> {
            // 백그란운드 작업
            while (mProgressStatus < 100) {
                try {
                    // 시간이 걸리는 네트워크 통신이나 파일 작업 등을 흉내 낸 것입니다.
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                mProgressStatus++;  // 진행 상태를 1씩 증가

                // 백그라운드 스레드에서 UI를 직접 조작하면 오류가 발생하므로,
                // handler.post()를 사용해 UI 스레드로 돌아가 작업을 수행하도록 요청합니다.
                handler.post(() -> { // 진행 업데이트
                    // 현재 mProgressStatus 값으로 프로그레스바를 업데이트하라는 요청을 UI 스레드에 보냅니다.
                    mProgress1.setProgress(mProgressStatus);
                });
            }

            // 작업 완료: 루프가 완전히 끝난 후, 마지막 handler.post(...)
            // 코드를 통해 프로그레스바를 최종적으로 100으로 설정하고 작업이 완료되었음을 확실히 합니다.
            handler.post(() -> {
                mProgress1.setProgress(mProgressStatus);
            });
        });
    }

    // 백그라운드에서 사용하던 스레드 자원을 깨끗하게 해제하여 메모리 누수를 방지하는 데 필수적입니다.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}