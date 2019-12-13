package com.example.pocklet;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BalanceTransactionActivity extends AppCompatActivity {

    private BalanceBase balance_base = BalanceBase.get();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance_data_layout);

        ArrayList<BalanceEntry> balance_entries = balance_base.getTransactions();

        ListView mListView = (ListView) findViewById(R.id.transactionList);

        BalanceListAdapter adapter = new BalanceListAdapter(
                this, R.layout.balance_adapter_view, balance_entries);
        mListView.setAdapter(adapter);
    }
}
