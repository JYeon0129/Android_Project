package com.example.wkddu.android_project_mcm;

public class TABLE_DAY {
    private String year;
    private String month;
    private String day;
    private int day_limit;
    private int day_spend;

    public TABLE_DAY(String year, String month, String day, int day_limit, int day_spend) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.day_limit = day_limit;
        this.day_spend = day_spend;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDay_limit() {
        return day_limit;
    }

    public void setDay_limit(int day_limit) {
        this.day_limit = day_limit;
    }

    public int getDay_spend() {
        return day_spend;
    }

    public void setDay_spend(int day_spend) {
        this.day_spend = day_spend;
    }
}
