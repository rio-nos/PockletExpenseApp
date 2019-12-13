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
import java.util.List;

public class IncomeChartActivity extends AppCompatActivity {

    private PieChart incPieChart;
    private IncomeBase incBase = IncomeBase.get();
    private ArrayList<IncomeEntry> income_entries = incBase.getIncomes();
    private ArrayList<String> categories = new ArrayList<>(Arrays.asList(
            "Salary","Property","Profit","Interest","Passive","Other"));
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_piechart_layout);

        incPieChart = (PieChart) findViewById(R.id.incomePieChart);

        Description description = new Description();
        description.setText("Your given incomes based on their categories.");
        description.setEnabled(true);
        incPieChart.setDescription(description);

        incPieChart.setUsePercentValues(true);
        incPieChart.setExtraOffsets(5, 10, 5, 5);

        incPieChart.setDragDecelerationFrictionCoef(0.90f);
        incPieChart.setDrawHoleEnabled(false);
        incPieChart.setTransparentCircleRadius(125f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        double total;
        for(int i = 0; i < categories.size(); ++i)
        {
            total = incBase.getCategoryAmount(categories.get(i));
            if (total > 0.0)
            {
                yValues.add(new PieEntry( (float) total, categories.get(i)));
            }
        }

        incPieChart.animateY(1300, Easing.EasingOption.EaseOutSine);

        PieDataSet dataSet = new PieDataSet(yValues, "Categories");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData((dataSet));

        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        data.setValueFormatter(new PercentFormatter());

        Legend l = incPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        incPieChart.setData(data);

    }
}
