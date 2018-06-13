package com.example.wkddu.android_project_mcm;

import java.util.Date;

public class Schedule {
    String title; // 화면에 보일 약속의 이름입니다.
    Date date; // 지출 내역의 날짜를 저장해둡니다.
    int cost; // 지출 비용입니다.
    int type; // 술약속인지 밥약속인지 회식인지 등 타입을 지정합니다. > Helper 클래스 참고

    public String getTitle() {
        return this.title;
    }

    public int getCost() {
        return this.cost;
    }

    public int getType() {
        return this.type;
    }

    public Date getDate() { return this.date; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDate(Date date) { this.date = date; }
}
