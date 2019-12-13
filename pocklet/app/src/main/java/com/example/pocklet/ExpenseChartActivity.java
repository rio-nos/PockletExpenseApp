package com.example.pocklet;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class ExpenseChartActivity extends AppCompatActivity {

    private PieChart expPieChart;
    private ExpenseBase expBase = ExpenseBase.get();
    private ArrayList<String> categories = new ArrayList<>(Arrays.asList(
            "Bills","Transportation","Food & Drink","Groceries","Shopping","Electronics","Other"));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_piechart_layout);

        expPieChart = (PieChart) findViewById(R.id.expensePieChart);

        Description description = new Description();
        description.setText("Your given incomes based on their categories.");
        description.setEnabled(true);
        expPieChart.setDescription(description);

        expPieChart.setUsePercentValues(true);
        expPieChart.setExtraOffsets(5, 10, 5, 5);

        expPieChart.setDragDecelerationFrictionCoef(0.90f);
        expPieChart.setDrawHoleEnabled(false);
        expPieChart.setTransparentCircleRadius(125f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        double total;
        for(int i = 0; i < categories.size(); ++i)
        {
            total = expBase.getCategoryAmount(categories.get(i));
            if (total > 0.0)
            {
                yValues.add(new PieEntry( (float) total, categories.get(i)));
            }
        }

        expPieChart.animateY(1300, Easing.EasingOption.EaseOutSine);

        PieDataSet dataSet = new PieDataSet(yValues, "Categories");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData((dataSet));

        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        data.setValueFormatter(new PercentFormatter());

        Legend l = expPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        expPieChart.setData(data);

    }
}
