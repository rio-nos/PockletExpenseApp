package com.example.pocklet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.abhinay.input.CurrencyEditText;
import me.abhinay.input.CurrencySymbols;

public class IncomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String SER_ENTRIES = "SER_ENTRIES";

    private boolean added = false;
    private Spinner spinner;
    private Button quitButton;
    private Button addButton;
    private Button finishButton;
    private CurrencyEditText addIncomeAmount;
    private ArrayList<IncomeEntry> income_entries;
    private IncomeBase income_base = IncomeBase.get();
    private BalanceBase balance_base = BalanceBase.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_income_layout);

        income_entries = income_base.getIncomes();
        quitButton = findViewById(R.id.crossButton);
        addButton = findViewById(R.id.plusButton);
        finishButton = findViewById(R.id.checkButton);
        addIncomeAmount = findViewById(R.id.incAmountEditText);
        addIncomeAmount.setCurrency(CurrencySymbols.USA);
        addIncomeAmount.setDelimiter(false);
        addIncomeAmount.setSpacing(false);
        addIncomeAmount.setDecimals(true);
        addIncomeAmount.setDecimals(true);
        addIncomeAmount.setSeparator(".");

//        addIncomeAmount.setText("$0.00");
        addIncomeAmount.addTextChangedListener(new MoneyTextWatcher(addIncomeAmount));

        spinner = findViewById(R.id.incSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.incType,
                R.layout.custom_spinner);
        adapter.setDropDownViewResource(R.layout.custom_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = addIncomeAmount.getText().toString();
                if ("".equals(temp)) {
                    String text = "Please add an income amount.";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                } else {
                    added = true;
                    income_base.addNewEntry(spinner.getSelectedItem().toString(), addIncomeAmount.getCleanDoubleValue(), new Date());
                    balance_base.addNewEntry("Income", spinner.getSelectedItem().toString(), addIncomeAmount.getCleanDoubleValue(), new Date());
                    addIncomeAmount.setText("$0.00");
                    String text = "Succesfully added income.";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = addIncomeAmount.getText().toString();
                if (income_entries.size() >= 0 && !("$0.00".equals(temp))) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(SER_ENTRIES, "check");
                    income_base.addNewEntry(spinner.getSelectedItem().toString(), addIncomeAmount.getCleanDoubleValue(), new Date());
                    balance_base.addNewEntry("Income", spinner.getSelectedItem().toString(), addIncomeAmount.getCleanDoubleValue(), new Date());
                    String text = "Successfully added income. (1)";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
                else if (income_entries.size() > 0 && ("$0.00".equals(temp)) && added) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(SER_ENTRIES, "check");
                    String text = "Successfully added incomes. (2)";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    String text = "Did not add any incomes.";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedText = parent.getItemAtPosition(position).toString();
        if (!isEmpty(addIncomeAmount.toString()) && isEmpty(parent.getItemAtPosition(position).toString()))
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

    public class MoneyTextWatcher implements TextWatcher {
        private final WeakReference<EditText> editTextWeakReference;

        public MoneyTextWatcher(EditText editText) {
            editTextWeakReference = new WeakReference<EditText>(editText);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            EditText editText = editTextWeakReference.get();
            if (editText == null) return;
            String s = editable.toString();
            if (s.isEmpty()) return;
            editText.removeTextChangedListener(this);
            String cleanString = s.replaceAll("[$,.]", "");
            BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
            String formatted = NumberFormat.getCurrencyInstance().format(parsed);
            editText.setText(formatted);
            editText.setSelection(formatted.length());
            editText.addTextChangedListener(this);
        }
    }

}
