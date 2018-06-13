package com.example.wkddu.android_project_mcm;

public class TABLE_MONTH {
    private String year;
    private String month;
    private int total_budget;
    private int total_spend;
    private int transfer_remain;

    public TABLE_MONTH(String year, String month, int total_budget, int total_spend, int transfer_remain) {
        this.year = year;
        this.month = month;
        this.total_budget = total_budget;
        this.total_spend = total_spend;
        this.transfer_remain = transfer_remain;
    }

    public TABLE_MONTH(String year, String month, int total_budget, int transfer_remain) {
        this.year = year;
        this.month = month;
        this.total_budget = total_budget;
        this.transfer_remain = transfer_remain;
        this.total_spend = 0;
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

    public int getTotal_budget() {
        return total_budget;
    }

    public void setTotal_budget(int total_budget) {
        this.total_budget = total_budget;
    }

    public int getTotal_spend() {
        return total_spend;
    }

    public void setTotal_spend(int total_spend) {
        this.total_spend = total_spend;
    }

    public int getTransfer_remain() {
        return transfer_remain;
    }

    public void setTransfer_remain(int transfer_remain) {
        this.transfer_remain = transfer_remain;
    }
}
