package com.example.pocklet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;

import me.abhinay.input.CurrencyEditText;
import me.abhinay.input.CurrencySymbols;

public class ExpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String SER_ENTRIES = "SER_ENTRIES";

    private boolean added = false;
    private Spinner spinner;
    private Button quitButton;
    private Button addButton;
    private Button finishButton;
    private CurrencyEditText addExpenseAmount;
    private ArrayList<ExpenseEntry> entries;
    private BalanceBase balance_base = BalanceBase.get();
    private ExpenseBase expense_base = ExpenseBase.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense_layout);

        entries = expense_base.getExpenses();
        quitButton = findViewById(R.id.crossButton);
        addButton = findViewById(R.id.plusButton);
        finishButton = findViewById(R.id.checkButton);
        addExpenseAmount = findViewById(R.id.expAmountEditText);
        addExpenseAmount.setCurrency(CurrencySymbols.USA);
        addExpenseAmount.setDelimiter(false);
        addExpenseAmount.setSpacing(false);
        addExpenseAmount.setDecimals(true);
        addExpenseAmount.setSeparator(".");

        spinner = findViewById(R.id.expSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.expType,
                R.layout.custom_spinner);
        adapter.setDropDownViewResource(R.layout.custom_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entries.clear();
                startActivity(new Intent(ExpenseActivity.this, HomeActivity.class));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = addExpenseAmount.getText().toString();
                if ("".equals(temp)) {
                    String text = "Please add an expense amount.";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                } else {
                    added = true;
                    expense_base.addNewEntry(spinner.getSelectedItem().toString(), addExpenseAmount.getCleanDoubleValue(), new Date());
                    balance_base.addNewEntry("Expense", spinner.getSelectedItem().toString(), addExpenseAmount.getCleanDoubleValue(), new Date());
                    addExpenseAmount.setText("");
                    String text = "Succesfully added expense.";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = addExpenseAmount.getText().toString();
                if (entries.size() >= 0 && !("".equals(temp))) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(SER_ENTRIES, "check");
                    String text = "Successfully added expense. (1)";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, resultIntent);
                    expense_base.addNewEntry(spinner.getSelectedItem().toString(), addExpenseAmount.getCleanDoubleValue(), new Date());
                    balance_base.addNewEntry("Expense", spinner.getSelectedItem().toString(), addExpenseAmount.getCleanDoubleValue(), new Date());
                    finish();
                }
                else if (entries.size() > 0 && ("".equals(temp)) && added) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(SER_ENTRIES, "check");
                    String text = "Successfully added expenses. (2)";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    String text = "Did not add any expenses.";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedText = parent.getItemAtPosition(position).toString();
        if (!isEmpty(addExpenseAmount.toString()) && isEmpty(parent.getItemAtPosition(position).toString()))
        {
            String text = "Please select an income type.";
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        String text = "Please select an income type.";
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }
    private boolean isEmpty(String text) {
        return text.trim().length() == 0;
    }
}
