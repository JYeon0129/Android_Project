package com.example.wkddu.android_project_mcm;

import android.util.Log;

public class Helpers {
    Helpers() {}

    /*
     * Type 리스트
     * index    이름           색상명
     * ------------------------------
     * 0        술/유흥          redpink
     * 1        식비            bluegreen
     * 2        주거/통신        orange
     * 3        생활용품         darkyellow
     * 4        의복/미용        pink
     * 5        건강/문화        skyblue
     * 6        교육            yellow
     * 7        교통            colorPrimaryDark
     * 8        기타            darkbrown
     * */

    public Type returnType (int typeNum) {
        Type type = new Type();
        type.setTypeNum(typeNum);

        switch (typeNum) {
            case 0:
                type.setTypeColor(R.color.redpink);
                type.setTypeName("술/유흥");
                break;
            case 1:
                type.setTypeColor(R.color.bluegreen);
                type.setTypeName("식비");
                break;
            case 2:
                type.setTypeColor(R.color.orange);
                type.setTypeName("주거/통신");
                break;
            case 3:
                type.setTypeColor(R.color.darkyellow);
                type.setTypeName("생활용품");
                break;
            case 4:
                type.setTypeColor(R.color.pink);
                type.setTypeName("의복/미용");
                break;
            case 5:
                type.setTypeColor(R.color.skyblue);
                type.setTypeName("건강/문화");
                break;
            case 6:
                type.setTypeColor(R.color.yellow);
                type.setTypeName("교육");
                break;
            case 7:
                type.setTypeColor(R.color.colorPrimaryDark);
                type.setTypeName("교통");
                break;
            case 8:
                type.setTypeColor(R.color.darkbrown);
                type.setTypeName("기타");
                break;
        }

        return type;
    }
}
