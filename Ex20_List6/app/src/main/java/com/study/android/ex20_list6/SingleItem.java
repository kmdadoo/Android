package com.study.android.ex20_list6;

// 이 클래스는 이름, 나이, 이미지 리소스 ID를 담는 데이터 모델 역할을 합니다.
public class SingleItem {
    private String name;
    private String telNum;
    private int resId;
    private int gender;

    public SingleItem(String name, String telNum, int resId, int gender) {
        this.name = name;
        this.telNum = telNum;
        this.resId = resId;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
