package com.example.pocklet;

import java.util.Date;

public class BalanceEntry {
    private String type;
    private String category;
    private double amount;
    private Date date;

    public BalanceEntry(String type, String category, double amount, Date date) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = Double.parseDouble(amount);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
