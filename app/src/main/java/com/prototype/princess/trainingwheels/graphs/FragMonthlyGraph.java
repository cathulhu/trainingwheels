//package com.prototype.princess.trainingwheels.graphs;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
//import com.github.mikephil.charting.utils.ColorTemplate;
//import com.prototype.princess.trainingwheels.R;
//import com.prototype.princess.trainingwheels.RepayPlan;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FragMonthlyGraph extends Fragment {
//
//    static public int[] colors = {92,16,220};
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.tab3graph, container, false);
//
//        BarChart chart = (BarChart) view.findViewById(R.id.chart);
//
//
//        List<BarEntry> yvals = new ArrayList<>();   //1. make y values list for graph
////        yvals.add(new BarEntry(9, 0));              //2. Add y values (value, index) to list
////        yvals.add(new BarEntry(20, 1));
//
//
//        int i=0;
//        for(RepayPlan plan : RepayPlan.GetList())
//        {
//            float[] eachbar = {plan.principal, plan.sumInterest, plan.taxes };
//            yvals.add(new BarEntry(eachbar, i));
//            i++;
//        }
//
//        List<String> xvals = new ArrayList<String>();   //3. make x value labels
//        xvals.add("Standard 10 Year");
//        xvals.add("Graduated 10 Year");
//        xvals.add("Extended 20 Year");
//        xvals.add("Extended Graduated 20 Year");
//        xvals.add("Revised P.A.Y.E 25 Year");
//        xvals.add("P.A.Y.E 20 Year");
//        xvals.add("Income Based 25 Year");
//        xvals.add("IBR New Borrowers");
//        xvals.add("Income Continent Repayment");
//        //       xvals.add("x");
//
//        XAxis xAxis = chart.getXAxis();
//        //     xAxis.setTextColor(Color.WHITE);
//
//        YAxis yAxis = chart.getAxisLeft();
//        //     yAxis.setTextColor(Color.WHITE);
//        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//
//
//        BarDataSet ydata = new BarDataSet(yvals, "Repayment Plan Breakdown");  //4. New BarDataSet to contain yvalues and descriptions
//        ydata.setDrawValues(false);
//        //ydata.setBarSpacePercent(55f);
//        //ydata.setColor(Color.rgb(92, 16, 220)); //only used for setting one bar, one color
//        ydata.setColors(getColors());
//        ydata.setStackLabels(new String[]{"Principal", "Interest", "Forgiven Taxes"});
//
//        List<IBarDataSet> dataSets = new ArrayList<>();     //5. Add the BarDataSet (ydata) to the set of all datasets available to graph within a List containing IBarDataSet objects
//        dataSets.add(ydata);
//
//        YAxis yRaXxis = chart.getAxisRight();
//        yRaXxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        chart.setDrawValueAboveBar(false);
//
//
//        BarData data = new BarData(xvals, dataSets);        //6. Add the set of all data to the master data container along with the x labels;
//        data.setValueTextSize(10f);
//
//        chart.setData(data);
//
//        //       Legend l = chart.getLegend();
//        //       l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
//        //       l.setCustom(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, new String[]{"Standard 10 Year", "Graduated 10 Year", "Extended 20 Year", "Extended Graduated 20 Year", "Revised P.A.Y.E 25 Year", "P.A.Y.E 20 Year", "Income Based 25 Year", "IBR New Borrowers", "Income Continent Repayment"});
//        //       l.setTextColor(Color.WHITE);
//        //       l.setTextSize(15f);
//
//        chart.setDescription(" ");
//        chart.animateXY(1000, 1000);
//        chart.invalidate();
//
//        return view;
//    }
//
//    private int[] getColors() {
//
//        int stacksize = 3;
//
//        // have as many colors as stack-values per entry
//        int[] colors = new int[stacksize];
//
//        for (int i = 0; i < stacksize; i++) {
//            colors[i] = ColorTemplate.PASTEL_COLORS[i];
//        }
//
//        return colors;
//    }
//
//
//
//
//
//
//
//
//
//
//}