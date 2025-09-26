package com.study.android.ex31_httpex1;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 에서 HTTP POST 통신을 수행하여 서버에 데이터를 전송하고,
// 그 응답 결과를 화면에 표시하는 액티비티(HttpPostActivity)입니다.
public class HttpPostActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TextView tvHtml2;

    // 1. 백그라운드 작업을 관리할 ExecutorService 정의
    ExecutorService executorService;
    // 2. UI 업데이트를 위해 메인 스레드에 연결된 Handler 정의
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_post);

        tvHtml2 = findViewById(R.id.tvHtml2);

        // ExecutorService 초기화: 스레드 풀을 생성합니다.
        // 단일 스레드 풀로, mainHandler를 메인 스레드와 연결하여 비동기 작업을 위한 준비를 마칩니다.
        executorService = Executors.newSingleThreadExecutor();
        // Handler 초기화: 메인 스레드의 Looper에 연결하여 UI 업데이트를 담당합니다.
        mainHandler = new Handler(Looper.getMainLooper());

    }

    // 액티비티가 파괴될 때 스레드 풀을 안전하게 종료합니다.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public void onBtnFinish(View v){
        finish();
    }

    // 버튼 클릭 시, 서버 주소를 구성하고 ContentValues 객체에
    // 서버로 전송할 데이터(예: userid, userpwd)를 담습니다.
    public void onBtnPost(View v){
        tvHtml2.setText("");

        String sUrl = getString(R.string.server_addr) + "/JspInServer/loginOk.jsp";

        try {
            ContentValues values = new ContentValues();
            values.put("userid", "홍길동");
            values.put("userpwd", "1234");

            // ExecutorService에 POST 작업을 제출 (백그라운드 스레드에서 실행)
            // 네트워크 통신 로직을 백그라운드 스레드에 넣어 실행시킵니다.
            executorService.execute(() -> {
                // 이 람다 내부가 기존 AsyncTask의 doInBackground() 역할입니다.
                // 서버 통신(POST)을 수행하고 결과를 받습니다.
                String result = performPostAction(sUrl, values);

                // Handler를 사용하여 결과를 메인 스레드로 전달합니다.
                // 통신이 완료되면, mainHandler를 통해 UI 업데이트
                // 함수(onPostActionCompleted)를 메인 스레드에서 실행하도록 요청합니다.
                mainHandler.post(() -> {
                    // 이 람다 내부가 기존 AsyncTask의 onPostExecute() 역할입니다.
                    onPostActionCompleted(result);
                });
            });
        } catch (Exception e) {
            Log.e(TAG, "POST Action execution failed", e);
            tvHtml2.setText("오류 발생: " + e.getMessage());
        }
    }

    // --- 기존 doInBackground 로직을 함수로 분리 ---
    // 네트워크 통신. 백그라운드 스레드에서 실행
    //  HTTP POST 요청을 보내고 서버로부터 응답 문자열을 받아 반환
    private String performPostAction(String url, ContentValues values) {
        String result;

        // RequestHttpURLConnection 객체 생성 및 request 메소드 호출
        RequestHttpURLConnection requestHttpURLConnection =
                new RequestHttpURLConnection();

        // RequestHttpURLConnection 클래스는 네트워크 I/O 작업을 수행해야 하므로
        // 반드시 백그라운드 스레드에서 호출되어야 합니다.
        result = requestHttpURLConnection.request(url, values);

        return result;
    }

    // --- 기존 onPostExecute 로직을 함수로 분리 ---
    //  UI 업데이트
    // performPostAction에서 받은 결과 문자열(result)을 확인하여 tvHtml2
    // 텍스트 뷰에 서버 응답 내용을 표시함으로써 화면을 업데이트합니다.
    private void onPostActionCompleted(String result) {
        // UI 업데이트는 반드시 이 함수 내(mainHandler.post 내부)에서 실행되어야 합니다.
        if (result != null && !result.isEmpty()) {
            // doInBackground()로 부터 리턴된 값이 s이므로 이를 출력합니다.
            tvHtml2.setText(result);
        } else {
            tvHtml2.setText("POST 통신 실패 또는 빈 응답");
        }
    }
}