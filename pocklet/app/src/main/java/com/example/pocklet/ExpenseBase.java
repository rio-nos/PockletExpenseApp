package com.example.pocklet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseBase {
    private ArrayList<ExpenseEntry> entries;
    private ExpenseBase() {
        entries = new ArrayList<>();
    }

    public ArrayList<ExpenseEntry> getExpenses() {
        return entries;
    }

    private static ExpenseBase exp_base;

    public static ExpenseBase get() {
        if(exp_base == null)
        {
            exp_base = new ExpenseBase();
        }
        return exp_base;
    }

    public void addNewEntry(String category, double amount, Date date)
    {
        ExpenseEntry expense_entry = new ExpenseEntry(
                category, amount, date
        );
        get().getExpenses().add(expense_entry);
    }

    public double getCategoryAmount(String category)
    {
        ArrayList<ExpenseEntry> entries = get().getExpenses();
        double total = 0;
        for (int i = 0; i < entries.size(); ++i)
        {
            if (entries.get(i).getCategory().equals(category))
            {
                total+= entries.get(i).getAmount();
            }
        }
        return total;
    }

    public double total()
    {
        double t = 0;
        for (int i = 0; i < getExpenses().size(); ++i)
        {
            t += getExpenses().get(i).getAmount();
        }
        return t;
    }

}
