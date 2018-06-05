package com.example.wkddu.android_project_mcm;

public class Helpers {
    Helpers() {}

    public int setTypeColor(int type) {
        int color = R.color.yellow;

        switch (type) {
            case 0:
                color = R.color.bluegreen;
                break;
            case 1:
                color = R.color.orange;
                break;
        }

        return color;
    }
}
