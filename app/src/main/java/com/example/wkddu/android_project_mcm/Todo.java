package com.example.wkddu.android_project_mcm;

import java.util.Date;

public class Todo {
    // 할 일을 저장하는 친구인데 임시로 만든거라 맘대로 바꾸셔도 됩니다!
    String title; // 화면에 보일 약속의 이름입니다.
    Date date; // 일정의 날짜를 저장해둡니다.
    int cost; // 예상 비용입니다.
    int type; // 술약속인지 밥약속인지 회식인지 등 타입을 지정합니다. > Helper 클래스 참고

    public Todo(String title, int cost, int type, Date date) {
        this.title = title;
        this.cost = cost;
        this.type = type;
        this.date = date;
    }

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

    public void setTitle(String todoName) {
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
