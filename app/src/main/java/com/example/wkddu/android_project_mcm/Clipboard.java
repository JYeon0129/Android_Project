package com.example.wkddu.android_project_mcm;

public class Clipboard {
    private String month;
    private String day;
    private String usage;
    private String payment;

    public Clipboard(String month, String day, String usage, String payment) {
        this.month = month;
        this.day = day;
        this.usage = usage;
        this.payment = payment;
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

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
