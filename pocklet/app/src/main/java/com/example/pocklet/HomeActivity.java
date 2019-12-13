package com.example.pocklet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;


import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomSheetAddDialog.BottomSheetListener  {

    private FirebaseAuth firebaseAuth;
    private Button addButton;
    private CardView incomeChart;
    private CardView expenseChart;
    private CardView balanceDisplay;
    private CardView incBarChart;
    private CardView expBarChart;
    private CardView balBarChart;
    private CardView startDemo;
    private CardView clearDemo;
    private TextView incomeText;
    private TextView expenseText;
    private TextView balanceText;
    private BarChart mpBarChart;
    private ExpenseBase expBase = ExpenseBase.get();
    private ArrayList<String> categories = new ArrayList<>(Arrays.asList(
            "Bills","Transportation","Food & Drink","Groceries","Shopping","Electronics","Other"));
    private IncomeBase income_base = IncomeBase.get();
    private ExpenseBase expense_base = ExpenseBase.get();
    private BalanceBase balance_base = BalanceBase.get();
    private ArrayList<IncomeEntry> income_entries = income_base.getIncomes();
    private ArrayList<ExpenseEntry> expense_entries = expense_base.getExpenses();
    private ArrayList<BalanceEntry> balance_entries = balance_base.getTransactions();

    private String[] mMonth = new String[] {
            "Jan", "Feb" , "Mar", "Apr", "May", "Jun",
            "Jul", "Aug" , "Sep", "Oct", "Nov", "Dec"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        incomeChart = (CardView) findViewById(R.id.incomeCard);
        expenseChart = (CardView) findViewById(R.id.expenseCard);
        balanceDisplay = (CardView) findViewById(R.id.balanceCard);
        incBarChart = (CardView) findViewById(R.id.incBarCard);
        expBarChart = (CardView) findViewById(R.id.expBarCard);
        balBarChart = (CardView) findViewById(R.id.balBarCard);
        startDemo = (CardView) findViewById(R.id.startDemoCard);
        clearDemo = (CardView) findViewById(R.id.clearDemoCard);
        addButton = (Button) findViewById(R.id.addButton);
        incomeText = (TextView) findViewById(R.id.incTotal);
        expenseText = (TextView) findViewById(R.id.expTotal);
        balanceText = (TextView) findViewById(R.id.balTotal);

        setValues();
//        startPop();
//        openChart();

        BarChart barChart = (BarChart) findViewById(R.id.chartContainer);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(38f, 0));
        entries.add(new BarEntry(52f, 1));
        entries.add(new BarEntry(65f, 2));
        entries.add(new BarEntry(30f, 3));
        entries.add(new BarEntry(85f, 4));
        entries.add(new BarEntry(19f, 5));
        entries.add(new BarEntry(75f, 6));

        BarDataSet bardataset = new BarDataSet(entries, "Dates");

        ArrayList<String> labels = new ArrayList<>();
        labels.add("Mon");
        labels.add("Tue");
        labels.add("Wed");
        labels.add("Thus");
        labels.add("Fri");
        labels.add("Sat");
        labels.add("Sun");

        BarData data = new BarData(bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        Description description = new Description();
        description.setText("Description");
        description.setEnabled(true);
        barChart.setDescription(description);  // set the description

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);

        firebaseAuth = FirebaseAuth.getInstance();

        incBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIncBar();
            }
        });
        expBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExpBar();
            }
        });
        balBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBalBar();
            }
        });

        startDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPop();
            }
        });

        clearDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPop();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetAddDialog bottomSheet = new BottomSheetAddDialog();
                bottomSheet.show(getSupportFragmentManager(), "bottomSheetDialog");
            }
        });

        incomeChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, IncomeChartActivity.class));
            }
        });

        expenseChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ExpenseChartActivity.class));
            }
        });

        balanceDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, BalanceTransactionActivity.class));
            }
        });
    }

    private void startPop() {

        income_base.getIncomes().clear();
        expense_base.getExpenses().clear();
        balance_base.getTransactions().clear();

        income_base.addNewEntry("Salary", 550.50, new Date(119, 0, 1, 0,0));
        income_base.addNewEntry("Property", 200.00, new Date(119, 0, 1, 0,0));
        income_base.addNewEntry("Profit", 250.00, new Date(119, 0, 1, 0,0));
        income_base.addNewEntry("Passive", 100.00, new Date(119, 3, 1, 6,0));
        income_base.addNewEntry("Interest", 20.00, new Date(120, 0, 1, 0,0));

        balance_base.addNewEntry("Income","Salary", 550.50, new Date(119, 0, 1, 0,0));
        balance_base.addNewEntry("Income","Property", 200.00, new Date(119, 0, 1, 0,0));
        balance_base.addNewEntry("Income","Profit", 250.00, new Date(119, 0, 1, 0,0));
        balance_base.addNewEntry("Income","Salary", 200.00, new Date(119, 6, 1, 0,0));
        balance_base.addNewEntry("Income","Passive", 100.00, new Date(119, 3, 1, 6,0));
        balance_base.addNewEntry("Income","Interest", 20.00, new Date(120, 0, 1, 0,0));

        expense_base.addNewEntry("Bills", 250.50, new Date(119, 1, 1, 0,0));
        expense_base.addNewEntry("Transportation", 50.00, new Date(119, 3, 1, 0,0));
        expense_base.addNewEntry("Food & Drink", 180.50, new Date(119, 6, 1, 12,0));
        expense_base.addNewEntry("Bills", 350.50, new Date(119, 0, 1, 0,0));
        expense_base.addNewEntry("Groceries", 200.00, new Date(119, 6, 1, 0,0));
        expense_base.addNewEntry("Shopping", 100.00, new Date(119, 3, 1, 6,0));
        expense_base.addNewEntry("Electronics", 20.15, new Date(120, 0, 1, 0,0));

        balance_base.addNewEntry("Expense", "Bills", 250.50, new Date(119, 1, 1, 0,0));
        balance_base.addNewEntry("Expense","Transportation", 50.00, new Date(119, 3, 1, 0,0));
        balance_base.addNewEntry("Expense","Food & Drink", 180.00, new Date(119, 6, 1, 12,0));
        balance_base.addNewEntry("Expense","Bills", 350.50, new Date(119, 0, 1, 0,0));
        balance_base.addNewEntry("Expense","Groceries", 200.00, new Date(119, 6, 1, 0,0));
        balance_base.addNewEntry("Expense","Shopping", 100.00, new Date(119, 3, 1, 6,0));
        balance_base.addNewEntry("Expense","Electronics", 20.15, new Date(120, 0, 1, 0,0));

        setValues();
    }

    private void clearPop() {
        income_entries.clear();
        expense_entries.clear();
        balance_entries.clear();
        setValues();
    }


    private void setValues() {
        double inc = 0.0;
        double exp = 0.0;
        double bal = 0.0;

        inc = income_base.total();
        exp = expense_base.total();
        bal = balance_base.total();

        incomeText.setText("$ " + String.valueOf(inc));
        expenseText.setText("$ " + String.valueOf(exp));
        balanceText.setText("$ " + String.valueOf(bal));
    }

    public void setChart() {

        BarChart barChart = (BarChart) findViewById(R.id.chartContainer);

//        BarDataSet barDataSet = new BarDataSet()

    }

    public void setIncBar()
    {

    }
    public void setExpBar()
    {

    }

    public void setBalBar()
    {

    }

    private void openChart() {
        int[] x = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] income = {2000, 2500, 2700, 3000, 2800, 3500, 3700, 3800};
        int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400};

        // Creating an  XYSeries for Income
        XYSeries incomeSeries = new XYSeries("Income");
        // Creating an  XYSeries for Expense
        XYSeries expenseSeries = new XYSeries("Expense");
        // Adding data to Income and Expense Series
        for (int i = 0; i < x.length; i++) {
            incomeSeries.add(x[i], income[i]);
            expenseSeries.add(x[i], expense[i]);
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        dataset.addSeries(incomeSeries);
        // Adding Expense Series to dataset
        dataset.addSeries(expenseSeries);

        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.WHITE);
        incomeRenderer.setPointStyle(PointStyle.CIRCLE);
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(2);
        incomeRenderer.setDisplayChartValues(true);

        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(Color.YELLOW);
        expenseRenderer.setPointStyle(PointStyle.CIRCLE);
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2);
        expenseRenderer.setDisplayChartValues(true);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Income vs Expense Chart");
        multiRenderer.setXTitle("Year 2012");
        multiRenderer.setYTitle("Amount in Dollars");
        multiRenderer.setZoomButtonsVisible(true);
        for (int i = 0; i < x.length; i++) {
            multiRenderer.addXTextLabel(i + 1, mMonth[i]);
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(incomeRenderer);
        multiRenderer.addSeriesRenderer(expenseRenderer);

        // Creating an intent to plot line chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);

        // Start Activity
        startActivity(intent);
    }

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.logoutMenu:
            {
                Logout();
            }
//            case R.id.settingsMenu:
//            {
//
//            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onButtonClick(int start) {
        switch (start)
        {
            case 1:
                startActivityForResult(new Intent(HomeActivity.this, IncomeActivity.class),1);
                break;
            case 2:
                startActivityForResult(new Intent(HomeActivity.this, ExpenseActivity.class),2);
                break;
        }
    }

    public void calculateBalance()
    {
        double balTotal = 0.0;
        for (int i = 0; i < income_entries.size(); ++i)
        {
            balTotal += income_entries.get(i).getAmount();
        }
        for (int i = 0; i < expense_entries.size(); ++i)
        {
            balTotal -= expense_entries.get(i).getAmount();
        }
        balanceText.setText("$ " + balTotal);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == 1)
            {
                double incTotal = 0.0;
                for (int i = 0; i < income_entries.size(); ++i)
                {
                    incTotal  +=  income_entries.get(i).getAmount();
                }
                incomeText.setText("$ " + incTotal);
                calculateBalance();
            }
            if (requestCode == 2)
            {
                double expTotal = 0.0;
                for (int i = 0; i < expense_entries.size(); ++i)
                {
                    expTotal  +=  expense_entries.get(i).getAmount();
                }
                expenseText.setText("$ " + expTotal);
                calculateBalance();
            }
        }
    }


}
