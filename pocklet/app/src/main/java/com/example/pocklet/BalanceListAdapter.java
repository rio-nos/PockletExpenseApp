package com.example.pocklet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BalanceListAdapter extends ArrayAdapter<BalanceEntry> {

    private static final String TAG = "BalanceListAdapter";

    private Context mContext;
    int mResource;


    public BalanceListAdapter(@NonNull Context context, int resource, List<BalanceEntry> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String type = getItem(position).getType();
        String category = getItem(position).getCategory();
        double amount = getItem(position).getAmount();
        Date date = getItem(position).getDate();

        BalanceEntry entry = new BalanceEntry(type, category, amount, date);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        LinearLayout llContainer = (LinearLayout) convertView.findViewById(R.id.entryContainer);
        TextView tvType = (TextView) convertView.findViewById(R.id.typeTextView);
        TextView tvCategory = (TextView) convertView.findViewById(R.id.categoryTextView);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.amountTextView);
        TextView tvDate1 = (TextView) convertView.findViewById(R.id.dateTextView1);
        TextView tvDate2 = (TextView) convertView.findViewById(R.id.dateTextView2);

        if (type.equals("Income"))
        {
            llContainer.setBackgroundResource(R.drawable.income_background);
        }
        if (type.equals("Expense"))
        {
            llContainer.setBackgroundResource(R.drawable.expense_background);
        }
        tvType.setText(type);
        tvCategory.setText(category);
        tvAmount.setText("$ " + String.valueOf(amount));

        DateFormat dateDF = new SimpleDateFormat("EEE, MMM d, ''y");
        DateFormat timeDF = new SimpleDateFormat("hh:mm aaa");
        String newDate = dateDF.format(date);
        String newTime = timeDF.format(date);
        tvDate1.setText(newDate);
        tvDate2.setText(newTime);

        return convertView;
    }
}











