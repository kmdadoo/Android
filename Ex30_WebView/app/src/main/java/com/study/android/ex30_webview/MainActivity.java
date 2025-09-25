package com.study.android.ex30_webview;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

// 안드로이드에서 WebView를 사용하여 웹 콘텐츠를 표시하고,
// 안드로이드 기능(전화, 문자 등)과 자바스크립트 간의 통신을 처리하는 전형적인 예제입니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    private final Handler handler = new Handler();
    public static CustomCircleProgressDialog progressDialog = null;

    WebView web;
    EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web = findViewById(R.id.web1);
        // 웹뷰 캐시를 지웁니다
        web.clearCache(true);                                      // 캐쉬 지우기
        // 캐시를 사용하지 않도록 설정합니다.
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 캐쉬 사용하지 않기
        //  자바스크립트 사용을 허용하여 동적인 웹 페이지나 자바스크립트 인터페이스 통신을 가능하게 합니다.
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDefaultTextEncodingName("UTF-8");

        // 앱 시작 시 구글 페이지를 로드
        web.loadUrl("https://www.google.com");
        // myWebView 클래스를 사용하여 웹 페이지 로딩, 이동, 에러 처리를 직접 관리하겠다고 선언합니다.
        web.setWebViewClient(new myWebView());
        // myWebChromeClient 클래스를 사용하여 자바스크립트의 alert(), confirm()
        // 같은 UI 처리를 직접 관리하겠다고 선언합니다.
        web.setWebChromeClient(new myWebChromeClient());
        web.setHorizontalScrollBarEnabled(false); // 세로 scroll 제거
        web.setVerticalScrollBarEnabled(false);    // 가로 scroll 제거
        // 안드로이드-자바스크립트 통신을 위한 핵심 설정입니다.
        // 웹 페이지의 자바스크립트 코드에서 **android.test()**와 같이 자바 코드를 호출할 수 있게 됩니다.
        web.addJavascriptInterface(new JavaScriptBridge(), "android");
        // 레이아웃에서 찾아 연결
        etMessage = findViewById(R.id.etMessage);
    }

    public void btnLocalHtml(View v){
        // 로컬 html 부르기
        // 앱의 assets 폴더에 있는 jsexam.html 파일을 로드
        web.loadUrl("file:///android_asset/jsexam.html");
    }

    public void btnWebHtml(View v){
        // 웹 html 부르기
        // https://www.google.com 같은 외부 URL을 로드
        web.loadUrl("https://www.google.com");
    }

    public void btnJSCall(View v){
        // 로컬 html 부르고 EditText에 값 입력 후 실행해야 함
        // EditText에 입력된 값을 가져와서 웹뷰의 자바스크립트 함수 **setMessage()**를 호출합니다.
        web.loadUrl("javascript:setMessage('"+etMessage.getText()+"')");
    }

    public void btnStringLoad(View v){
        // String 웹뷰로 부르기
        // 인코딩을 지정하여 Java 문자열로 작성된 HTML 내용을 웹뷰에 바로 표시합니다.
        String summary =
                "<html>" +
                        "<head>" +
                        "<meta charset=\"utf-8\">" +
                        "</head>" +
                        "<body>" +
                        "Hello~ 강감찬" +
                        "<img src='https://goo.gl/LvIXUL'/>" +
                        "</body>" +
                        "</html>";
        web.loadData(summary, "text/html; charset=UTF-8", null);
    }

    // 웹뷰에서 자바스크립트의 Alert/Confirm을 보여 줄 수 있도록 한다.
    // 웹 페이지의 UI 상호작용을 안드로이드 시스템에서 처리하도록 합니다.
    private class myWebChromeClient extends WebChromeClient
    {
        // 웹 페이지의 alert()나 confirm() 함수 호출을 가로채서,
        // **안드로이드 스타일의 AlertDialog**를 띄우고 사용자의 응답(확인/취소)을 자바스크립트에 전달합니다.
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final android.webkit.JsResult result){
            result.confirm();

            new AlertDialog.Builder(view.getContext())
                    //.setIcon(R.drawable.icon);
                    //.setTitle(R.string.title_activity_main)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setCancelable(true)
                    .create()
                    .show();

            return true;
        };

        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final android.webkit.JsResult result)
        {
            new AlertDialog.Builder(view.getContext())
                    //.setTitle(R.string.title_activity_main)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.cancel();
                                }
                            })
                    .setCancelable(false)
                    .create()
                    .show();

            return true;
        };
    }

    // 안드로이드 자체 WebView가 아닌 내가 만든 WebView 직접 사용한다고 명시
    // 페이지 이동, 로딩 상태 변화, 에러 발생 등 콘텐츠 관련 이벤트를 제어
    private class myWebView extends WebViewClient {

        // ------------------------------------------------------------------
        // 1. 최신 API (API 24 이상)에서 호출되는 메서드
        // ------------------------------------------------------------------
        @Override
        // 최신(API 24 이상) 및 구버전(API 23 이하) 메서드를 모두 재정의하여 하위 호환성을 확보했습니다.
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            // WebResourceRequest에서 Uri 객체를 얻어 analyzeUrl 로 전달
            return handleCustomUrl(view, request.getUrl());
        }

        // ------------------------------------------------------------------
        // 2. 사용 중단된 API (API 23 이하)에서 호출되는 메서드
        // 이 메서드는 하위 호환성을 위해 유지합니다.
        // ------------------------------------------------------------------
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // String url을 Uri 객체로 변환하여 analyzeUrl 로 전달
            return handleCustomUrl(view, Uri.parse(url));
        }

        // ------------------------------------------------------------------
        // 3. 실제 URL 처리 로직을 담은 공통 메서드
        // ------------------------------------------------------------------
        // handleCustomUrl공통 메서드를 호출하여 URL 스키마(프로토콜)를 분석합니다.
        private boolean handleCustomUrl(WebView view, Uri uri) {
            // Uri.getScheme()을 사용하여 프로토콜을 확인합니다.
            String scheme = uri.getScheme();
            Log.d(TAG, scheme);

            if (scheme == null) {
                return false; // scheme이 없으면 WebView가 기본적으로 처리하도록 맡깁니다.
            }

            switch (scheme) {
                case "http":
                case "https":
                    // 웹뷰에서 로딩하도록 허용
                    // 웹뷰 내부에서 로딩을 계속
                    view.loadUrl(uri.toString());
                    return true;
                // 해당 정보를 **Intent**에 담아 안드로이드의 다른 앱(메일, 문자, 지도)을 호출합니다.
                case "mailto":
                case "sms":
                    // 외부 앱으로 처리 (메일, 문자, 지도)
                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                    startActivity(i);
                    return true;
                case "geo":
                    Intent g = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(g);
                    return true;
                // 전화 걸기 전에 CALL_PHONE 권한을 체크합니다.
                // 권한이 없으면 토스트 메시지를, 있으면 ACTION_CALL 인텐트를 사용하여 전화를 겁니다.
                case "tel":
                    // 전화걸기 권한 체크 및 처리
                    int permissionCheck = ContextCompat.checkSelfPermission(
                            MainActivity.this,
                            Manifest.permission.CALL_PHONE);

                    if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getApplicationContext(),
                                "전화걸기를 사용하시려면 먼저 권한을 승인해 주세요.",
                                Toast.LENGTH_SHORT).show();
                        // 권한 요청 로직이 필요합니다 (MainActivity에서 처리해야 함)
                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL, uri);
                        startActivity(callIntent);
                    }
                    return true;

                default:
                    // 다른 모든 URL은 웹뷰가 처리하도록 맡깁니다.
                    return false;
            }
        }

        // 웹페이지 로딩이 시작할 때 처리
        @Override
        // onPageStarted (시작 시): CustomCircleProgressDialog를 띄워 로딩 중임을 표시합니다.
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressDialog = new CustomCircleProgressDialog(MainActivity.this);
            // 주변 클릭 터치 시 프로그래서 사라지지 않게 하기 : false
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        // ------------------------------------------------------------------
        // 1. [최신 메서드] API 23 (Android 6.0) 이상에서 호출됨
        // ------------------------------------------------------------------
        // 역시 최신/구버전 메서드를 모두 재정의하며, handleError 메서드를 호출하여 로딩
        // 실패 시 토스트 메시지를 출력하고 프로그레스 다이얼로그를 닫습니다.
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            // API 23 이상에서만 WebResourceError 객체를 사용할 수 있도록 분기 처리
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String description = error.getDescription().toString();

                // 공통 에러 처리 로직 호출
                handleError(description);
            } else {
                // 하위 호환성을 위해 이 메서드에서는 별도의 처리를 하지 않고,
                // 아래의 Deprecated 메서드가 호출되도록 시스템에 맡기거나,
                // 필요한 경우 여기서 처리할 수 있습니다.
            }
        }

        // ------------------------------------------------------------------
        // 2. [사용 중단된 메서드] API 22 (Android 5.1) 이하에서 호출됨 (하위 호환성 유지)
        // ------------------------------------------------------------------
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

            // 공통 에러 처리 로직 호출
            handleError(description);
        }

        // ------------------------------------------------------------------
        // 3. 공통 에러 처리 로직
        // ------------------------------------------------------------------
        private void handleError(String description) {
            // 1. Toast 메시지 출력 (전달받은 Context 사용)
            Toast.makeText(getApplicationContext(),
                    "Loading Error : " + description,
                    Toast.LENGTH_SHORT).show();

            // 2. 프로그레스 다이얼로그 닫기
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        // 웹페이지 로딩이 끝났을 때 처리
        // onPageFinished (종료 시): 다이얼로그를 닫습니다.
        @Override
        public void onPageFinished(WebView view, String url) {
            if (progressDialog!=null) {
                progressDialog.dismiss();
            }
        }
    }

    // 자바스크립트에서 Call할 클래스 함수. 자바 코드를 자바스크립트에 노출
    final class JavaScriptBridge {
        // 이 어노테이션이 붙은 test()와 testParams() 메서드는 웹 페이지의 자바스크립트
        // 코드에서 android.test() 또는 android.testParams('값') 형태로 호출될 수 있습니다.
        @JavascriptInterface
        public void test(){
            // handler.post(Runnable): 웹뷰 스레드가 아닌 안드로이드의 메인(UI) 스레드에서
            //      Toast 메시지 같은 UI 작업을 안전하게 실행하도록 보장합니다.
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "테스트", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Parameter must be final
        @JavascriptInterface
        public void testParams(final String arg){
            handler.post(new Runnable() {
                public void run() {
                    Log.d(TAG, "setMessage("+arg+")");
                    Toast.makeText(getApplicationContext(), "테스트파라미터:"+arg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // 이전키 눌렀을 때 WebView 히스토리 back. Back 버튼 처리
    @Override
    // 안드로이드의 기본 뒤로 가기 동작을 가로채서, 웹뷰에 이전 페이지 히스토리(web.canGoBack())가
    // 남아 있다면 앱 종료 대신 웹뷰의 이전 페이지로 돌아가도록(web.goBack()) 처리합니다.
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()){
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        web.stopLoading();
    }
}

