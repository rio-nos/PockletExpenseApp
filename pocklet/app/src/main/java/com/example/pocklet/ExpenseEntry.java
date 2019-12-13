package com.example.pocklet;

import java.util.Date;

public class ExpenseEntry {
    private String category;
    private double amount;
    private Date date;

    public ExpenseEntry(String category, double amount, Date date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String type) {
        this.category = type;
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
