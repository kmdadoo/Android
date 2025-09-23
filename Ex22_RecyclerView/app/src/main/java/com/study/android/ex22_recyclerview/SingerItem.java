package com.study.android.ex22_recyclerview;

// 이 클래스는 이름, 나이, 이미지 리소스 ID를 담는 데이터 모델 역할을 합니다.
public class SingerItem {
    private String name;
    private String age;
    private int resId;

    public SingerItem(String name, String age, int resId) {
        this.name = name;
        this.age = age;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
