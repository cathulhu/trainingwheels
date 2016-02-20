package com.prototype.princess.trainingwheels.stabs;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.prototype.princess.trainingwheels.AsyncRequest;
import com.prototype.princess.trainingwheels.PostComThread;
import com.prototype.princess.trainingwheels.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class FragTabAnalysis extends Fragment{

    static public int[] cost = new int[11];
    static public int[] interest = new int[11];
    static public double[] tax = new double[11];
    static public int[] colors = {92,16,220};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab3anal, container, false);

        BarChart chart = (BarChart) view.findViewById(R.id.chart);


        List<BarEntry> yvals = new ArrayList<>();   //1. make y values list for graph
//        yvals.add(new BarEntry(9, 0));              //2. Add y values (value, index) to list
//        yvals.add(new BarEntry(20, 1));

        {
            for (int i = 0; i < 8; i++)
            {
                float[] eachbar = {cost[i], interest[i], (float)tax[i] };
                yvals.add(new BarEntry(eachbar, i));
            }
        }

        List<String> xvals = new ArrayList<String>();   //3. make x value labels
        xvals.add("Standard 10 Year");
        xvals.add("Graduated 10 Year");
        xvals.add("Revised P.A.Y.E 25 Year");
        xvals.add("P.A.Y.E 20 Year");
        xvals.add("Income Based 25 Year");
        xvals.add("IBR New Borrowers");
        xvals.add("Income Continent Repayment");
        xvals.add("plan8");


        BarDataSet ydata = new BarDataSet(yvals, "Repayment Plan Breakdown");  //4. New BarDataSet to contain yvalues and descriptions
        //ydata.setBarSpacePercent(55f);
        //ydata.setColor(Color.rgb(92, 16, 220)); //only used for setting one bar, one color
        ydata.setColors(getColors());
        ydata.setStackLabels(new String[]{"Principal", "Interest", "Forgiven Taxes"});

        List<IBarDataSet> dataSets = new ArrayList<>();     //5. Add the BarDataSet (ydata) to the set of all datasets available to graph within a List containing IBarDataSet objects
        dataSets.add(ydata);

        // YAxis yaxis = chart.getAxisRight();
       // yaxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        chart.setDrawValueAboveBar(false);


        BarData data = new BarData(xvals, dataSets);        //6. Add the set of all data to the master data container along with the x labels;
        data.setValueTextSize(10f);

        chart.setData(data);

        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();

        return view;
    }

    private int[] getColors() {

        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < stacksize; i++) {
            colors[i] = ColorTemplate.VORDIPLOM_COLORS[i];
        }

        return colors;
    }










}

