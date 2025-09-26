package com.study.android.ex32_xmljsonex;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Stack;

// 드로이드 앱에서 **JSON(JavaScript Object Notation)**과
// XML(eXtensible Markup Language) 데이터를 분석하고 파싱하는
// 다양한 방법을 보여주는 MainActivity입니다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtn1Clicked(View v){
        getJsonData1();
    }

    public void onBtn2Clicked(View v){
        getJsonData2();
    }

    public void onBtn3Clicked(View v){
        getJsonData3();
    }

    public void onBtn4Clicked(View v){
        getXmlData1();
    }

    // 단순 배열([1,2,3,4,5])을 파싱
    // 전체 문자열을 **JSONObject**로 만든 후, 키인 "number"를 사용하여
    // **JSONArray**를 추출하고, 반복문으로 배열의 각 int 값을 가져옵니다.
    public void getJsonData1() {
        // {"number":[1,2,3,4,5]}
        String json4arr = "{\"number\":[1,2,3,4,5]}";
        try {
            JSONObject jobj = new JSONObject(json4arr);
            JSONArray jarr = jobj.getJSONArray("number");
            for (int i = 0; i < jarr.length() ; i++) {
                int tmp = jarr.getInt(i);
                Log.d(TAG, "Json Data : "+ tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getJsonData2() {
        // {"color":{"top":"red","bottom":"black","left":"blur","right":"green"}}
        // 중첩된 객체("color": {...}) 내의 특정 값을 파싱
        String json4obj =
                "{\"color\":{\"top\":\"red\",\"bottom\":\"black\"," +
                        "\"left\":\"blur\",\"right\":\"green\"}}";
        // 전체 객체에서 "color" 키를 사용하여 내부 **JSONObject**를 얻어낸 후,
        // 이 내부 객체에서 "left" 키의 String 값을 추출합니다.
        try {
            JSONObject jobj = new JSONObject(json4obj);
            JSONObject color = jobj.getJSONObject("color");
            if (color.has("left")){
                String top_color = color.getString("left");
                Log.d(TAG, "left color : "+ top_color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getJsonData3() {
        // {"menu": {"id": "file", "value": "File", "popup": { "menuitem": [ {"value": "New", "onclick": "CreateNewDoc()"}, {"value": "Open", "onclick": "OpenDoc()"}, {"value": "Close", "onclick": "CloseDoc()"}]}}}
        // 여러 단계로 중첩된 객체, 객체, 배열이 혼합된 복잡한 구조를 파싱
        String jString = "{\"menu\": {\"id\": \"file\", \"value\": \"File\", " +
                "\"popup\": { \"menuitem\": [ {\"value\": \"New\", \"onclick\": " +
                "\"CreateNewDoc()\"}, {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"}, " +
                "{\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}]}}}";
        // 단계별로 **getJSONObject()**와 **getJSONArray()**를 사용하여 깊이 탐색하며
        // 원하는 데이터를 추출합니다. 이 예제는 복잡한 데이터 구조에서 특정 배열의 모든
        // 항목을 반복하여 처리하는 방법을 보여줍니다.
        try {
            JSONObject jObject = new JSONObject(jString);
            JSONObject menuObject = jObject.getJSONObject("menu");

            String attributeId = menuObject.getString("id");
            String attributeValue = menuObject.getString("value");

            Log.d(TAG, "Menu id : " + attributeId);
            Log.d(TAG, "Menu value : " + attributeValue);

            JSONObject popupObject = menuObject.getJSONObject("popup");

            JSONArray menuitemArray = popupObject.getJSONArray("menuitem");

            for (int i = 0; i < menuitemArray.length() ; i++) {
                Log.d(TAG, "value : "+ menuitemArray.getJSONObject(i).getString("value"));
                Log.d(TAG, "onClick : "+ menuitemArray.getJSONObject(i).getString("onclick"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // XML 데이터 파싱 함수
    // res/xml 폴더의 test.xml 파일에 있는 데이터를 **XmlPullParser**를 사용하여 파싱합니다.
    private void getXmlData1() {
        try {
            ArrayList<String> aNumber = new ArrayList<String>();
            ArrayList<String> aWord   = new ArrayList<String>();
            ArrayList<String> aMean   = new ArrayList<String>();

            // XML 처리에 사용할 변수 선언
            int event = 0;
            String currentTag = null;
            Stack<String> tagStack = new Stack<String>();

            // 스택 사용 여부! 값을 바꿔서 실행해 보시고
            // <word>aaa<any>any text</any>bbb</word>에서 bbb의 결과를 확인해 보세요.
            boolean useStack = true;

            // XML 파서
            XmlPullParser parser = getResources().getXml(R.xml.test);

            // 파싱 시작
            // while 루프를 돌면서 파싱 이벤트를 확인하고, TEXT 이벤트일 때 currentTag와 ]
            // 일치하는 데이터 리스트에 값을 추가합니다.
            // 데이터를 당겨오면서(Pull) 파싱하는 방식으로, 이벤트 기반(START_TAG, TEXT, END_TAG)으로 동작합니다.
            while((event = parser.next()) != XmlPullParser.END_DOCUMENT) {
                switch(event) {
                    // 파서가 TEXT 이벤트(태그 사이의 실제 값)를 만났을 때, ]
                    // 어떤 태그의 값인지 알기 위해 currentTag 변수를 사용합니다.
                    // **Stack**은 중첩된 XML 구조를 처리할 때, 내부 태그 파싱을
                    // 마친 후 부모 태그로 안전하게 복귀하기 위해 사용됩니다.
                    case XmlPullParser.START_TAG:
                        // 시작 태그를 만나면 currentTag에 기록
                        if(useStack && currentTag != null) {
                            tagStack.push(currentTag);
                        }
                        currentTag = parser.getName();
                        break;

                    case XmlPullParser.TEXT:
                        // currentTag가 처리하고자 하는 태그이면...
                        if(currentTag != null) {

                            if (currentTag.equals("number")) {
                                String value = parser.getText();
                                aNumber.add(value);
                            } else if (currentTag.equals("word")) {
                                String value = parser.getText();
                                aWord.add(value);
                            } else if (currentTag.equals("mean")) {
                                String value = parser.getText();
                                aMean.add(value);
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        // 종료 태그를 만나면 currentTag 초기화
                        if(useStack && tagStack.size() > 0) {
                            currentTag = tagStack.pop();
                        }
                        else {
                            currentTag = null;
                        }
                        break;

                    default:
                        break;
                }
            }

            // 모든 파싱이 완료되면 수집된 데이터를 로그에 출력합니다.
            for(int i = 0; i < aNumber.size(); i++)
            {
                Log.d(TAG, "number : " + aNumber.get(i));
                Log.d(TAG, "word   : " + aWord.get(i));
                Log.d(TAG, "mean   : " + aMean.get(i));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}