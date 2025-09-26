package com.study.android.ex31_httpex1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// HTTP GET 통신을 수행하여 서버의 데이터를 가져오고,
// 그 결과를 화면에 표시하는 액티비티(HttpGetActivity)입니다.
public class HttpGetActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TextView tvHtml;
    WebView webView;

    // 1. 백그라운드 작업을 관리할 ExecutorService 정의
    // 네트워크 통신 같은 **시간이 오래 걸리는 작업(I/O)**을
    // 백그라운드 스레드에서 실행하도록 관리하는 객체입니다.
    // (기존 AsyncTask의 스레드 관리 역할을 대체합니다.)
    ExecutorService executorService;
    // 2. UI 업데이트를 위해 메인 스레드에 연결된 Handler 정의
    // 결과를 안전하게 메인(UI) 스레드에 전달하여 화면(UI)을 업데이트할 수 있게 돕는 객체입니다.
    Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_get);

        tvHtml = findViewById(R.id.tvHtml1);
        webView = findViewById(R.id.webView1);

        // ExecutorService 초기화: 스레드 풀을 생성합니다.
        // 단일 스레드 실행기(newSingleThreadExecutor)로 초기화하여
        // 네트워크 작업을 위한 스레드 풀을 준비합니다
        executorService = Executors.newSingleThreadExecutor();
        // Handler 초기화: 메인 스레드의 Looper에 연결하여 UI 업데이트를 담당합니다.
        // UI 업데이트를 위해 메인 스레드와 연결
        mainHandler = new Handler(Looper.getMainLooper());
    }

    // 액티비티가 종료될 때 스레드 풀을 안전하게 종료합니다.
    // 액티비티가 종료될 때 실행 중인 비동기 작업을 안전하게 멈추고 스레드 풀을
    // 종료(shutdown())하여 메모리 누수를 방지합니다.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public void onBtnGet(View v){
        String sUrl = getString(R.string.server_addr) + "/JspInServer/login.jsp";
        Log.d(TAG, "sUrl1:"+sUrl);

        // ExecutorService에 네트워크 작업을 제출합니다. (백그라운드에서 실행)
        // 네트워크 통신 로직을 백그라운드 스레드에 넣어 실행시킵니다.
        executorService.execute(() -> {
            // 이 람다 내부가 기존 AsyncTask의 doInBackground() 역할입니다.
            // 실제로 네트워크 통신을 수행하는 함수를 호출하여 결과를 받습니다
            String result = performGetAction(sUrl);

            // Handler를 사용하여 결과를 메인 스레드로 전달합니다.
            // 네트워크 작업이 완료된 후, mainHandler를 통해 UI 업데이트
            // 코드(onGetActionCompleted)를 메인 스레드에 넣어 실행하도록 요청
            mainHandler.post(() -> {
                // 이 람다 내부가 기존 AsyncTask의 onPostExecute() 역할입니다.
                onGetActionCompleted(result);
            });
        });
    }

    public void onBtnFinish(View v){
        finish();
    }

    // --- 기존 doInBackground 로직을 함수로 분리 ---
    // 네트워크 통신
    private String performGetAction(String sUrl) {
        StringBuilder output = new StringBuilder();
        String sOutput = "";

        // 백그라운드 스레드에서 실행되며, 주어진 URL로 **HttpURLConnection**을
        // 열어 GET 방식으로 서버에 요청을 보냅니다.
        try {
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.d(TAG, "sUrl:" + sUrl);

            if (conn != null){
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // conn.setDoOutput(true); // GET 방식에서는 보통 불필요

                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK){
                    // 서버로부터 받은 응답(HTML 내용)을 **BufferedReader**를 통해 한 줄씩
                    // 읽어 String 형태로 만들어 반환합니다.
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null){
                        output.append(line).append("\n");
                    }
                    reader.close();
                    conn.disconnect();
                    sOutput = output.toString();
                    Log.d(TAG, sOutput);
                } else {
                    Log.d(TAG, "HTTP Error Code: " + resCode);
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, "Exception in processing response", ex);
        }
        return sOutput;
    }

    // --- 기존 onPostExecute 로직을 함수로 분리 ---
    // UI 업데이트
    // 메인(UI) 스레드에서 실행되며, performGetAction에서 받은 결과
    // 문자열(result)을 사용하여 화면을 업데이트합니다.
    private void onGetActionCompleted(String result) {
        // UI 업데이트는 반드시 이 함수 내(mainHandler.post 내부)에서 실행되어야 합니다.
        if (result != null && !result.isEmpty()) {
            // 텍스트 뷰에 서버 응답 문자열을 표시
            tvHtml.setText(result);
            // 웹 뷰에 해당 문자열을 HTML 콘텐츠로 로드하여 보여줍니다.
            webView.loadData(result, "text/html; charset=UTF-8", null);
        } else {
            tvHtml.setText("데이터 로드 실패 또는 응답 없음");
        }
    }
}