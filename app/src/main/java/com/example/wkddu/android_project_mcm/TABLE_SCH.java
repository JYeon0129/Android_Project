package com.example.wkddu.android_project_mcm;

public class TABLE_SCH {
    private String year;
    private String month;
    private String day;
    private int category;
    private int spend;
    private String usage;

    public TABLE_SCH(String year, String month, String day, int category, int spend, String usage) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.category = category;
        this.spend = spend;
        this.usage = usage;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSpend() {
        return spend;
    }

    public void setSpend(int spend) {
        this.spend = spend;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
