package com.example.pocklet;

import java.util.ArrayList;
import java.util.Date;

public class IncomeBase {
    private ArrayList<IncomeEntry> entries;

    private IncomeBase() {
        entries = new ArrayList<>();
    }

    public ArrayList<IncomeEntry> getIncomes() {
        return entries;
    }


    private static IncomeBase inc_entry_base;

    public static IncomeBase get() {
        if(inc_entry_base == null)
        {
            inc_entry_base = new IncomeBase();
        }
        return inc_entry_base;
    }

    public void addNewEntry(String category, double amount, Date date)
    {
        IncomeEntry income_entry = new IncomeEntry(
                category, amount, date
        );
        get().getIncomes().add(income_entry);
    }

    public double getCategoryAmount(String category)
    {
        ArrayList<IncomeEntry> entries = get().getIncomes();
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
        for (int i = 0; i < getIncomes().size(); ++i)
        {
            t += getIncomes().get(i).getAmount();
        }
        return t;
    }
}
