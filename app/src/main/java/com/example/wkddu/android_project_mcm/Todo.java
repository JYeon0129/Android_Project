package com.example.wkddu.android_project_mcm;

import java.util.Date;

public class Todo extends Schedule {
    // 할 일을 저장하는 친구인데 임시로 만든거라 맘대로 바꾸셔도 됩니다!
    public Todo(String title, int cost, int type, Date date) {
        this.title = title;
        this.cost = cost;
        this.type = type;
        this.date = date;
    }
}
