package com.example.pocklet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BalanceBase {

    private ArrayList<BalanceEntry> balance_entries;

    private BalanceBase() {
        balance_entries = new ArrayList<>();
    }

    public ArrayList<BalanceEntry> getTransactions() {
        return balance_entries;
    }

    private static BalanceBase balance_base;

    public static BalanceBase get() {
        if(balance_base == null)
        {
            balance_base = new BalanceBase();
        }
        return balance_base;
    }

    public void addNewEntry(String type, String category, double amount, Date date)
    {
        BalanceEntry balance_entry = new BalanceEntry(
                type, category, amount, date
        );
        get().getTransactions().add(balance_entry);
    }

    public double total()
    {
        double t = 0.0;
        for (int i = 0; i < getTransactions().size(); ++i)
        {
            if (getTransactions().get(i).getType().trim().equals("Income"))
            {
                t += getTransactions().get(i).getAmount();
            }
            if (getTransactions().get(i).getType().trim().equals("Expense"))
            {
                t -= getTransactions().get(i).getAmount();
            }}
        return t;
    }
}
