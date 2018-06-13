package com.example.wkddu.android_project_mcm;

import java.util.Date;

public class Spend extends Schedule {
    // 지출 내역을 저장하는 친구인데 임시로 만든거라 맘대로 바꾸셔도 됩니다!

    public Spend (String todoName, int cost, int type, Date date) {
        this.title = todoName;
        this.cost = cost;
        this.type = type;
        this.date = date;
    }
}
