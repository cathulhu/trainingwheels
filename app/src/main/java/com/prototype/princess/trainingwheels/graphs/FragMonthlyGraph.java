package com.prototype.princess.trainingwheels.graphs;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.prototype.princess.trainingwheels.PaymentCalc;
import com.prototype.princess.trainingwheels.R;
import com.prototype.princess.trainingwheels.RepayPlan;

import java.util.ArrayList;
import java.util.List;

public class FragMonthlyGraph extends Fragment {

    static public int[] colors = {92,16,220};

    PaymentCalc aCalc = new PaymentCalc();
    double stdpayment = aCalc.stdPayments(120);   //ten year plan
    double efpayments = aCalc.stdPayments(300);   //ten year plan

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tabmonthgraph, container, false);


        String stdResult = "Fixed 10 year: " + Double.toString(stdpayment) + "\nFixed 25 Year: " + Double.toString(efpayments);

        List<Float> IBRresults = new ArrayList<>();
        IBRresults= aCalc.IBR();

        String IBRout ="Repay, Paye, IBRnew, IBRold: "+ "\n" + IBRresults.get(0).toString() + "\n" + IBRresults.get(1).toString() + "\n" + IBRresults.get(2).toString() + "\n" + IBRresults.get(3).toString();

        List<Float> ICresults = new ArrayList<>();
        ICresults= aCalc.ICR();

        String ICRout = "20% Discretionary Income: " + ICresults.get(0).toString() + "\nICR 12 year * income percentage factor: " + ICresults.get(1).toString();

        TextView stdOutput = (TextView) view.getRootView().findViewById(R.id.stdPayOutput);
        stdOutput.setText(stdResult);

        TextView IBROutput = (TextView) view.getRootView().findViewById(R.id.ibrPayOutput);
        IBROutput.setText(IBRout);

        TextView ICROutput = (TextView) view.getRootView().findViewById(R.id.icrPayOutput);
        ICROutput.setText(ICRout);







        LineChart chart2 = (LineChart) view.findViewById(R.id.chart);


        List<Entry> yvals1 = new ArrayList<>();   //1. make y values list for graph
        List<Entry> yvals2 = new ArrayList<>();   //1. make y values list for graph
        List<Entry> yvals3 = new ArrayList<>();   //1. make y values list for graph
        List<Entry> yvals4 = new ArrayList<>();   //1. make y values list for graph
        List<Entry> yvals5 = new ArrayList<>();   //1. make y values list for graph
        List<Entry> yvals6 = new ArrayList<>();   //1. make y values list for graph
        List<Entry> yvals7 = new ArrayList<>();   //1. make y values list for graph

        List<String> xvals1 = new ArrayList<String>();   //3. make x value labels

        int i=0;
        for(float monthlyPay : PaymentCalc.stdPaymentsList(120))
        {
            yvals1.add(new Entry(monthlyPay, i));
 //           xvals1.add("");
            i++;
        }

        i=0;
        for(float monthlyPay : PaymentCalc.stdPaymentsList(300))
        {
            yvals2.add(new Entry(monthlyPay, i));
            xvals1.add("Month " + String.valueOf(i));
            i++;
        }

        List<RepayPlan> allPlans =  RepayPlan.GetList();


        int months=0;
        String testRE= "RE";
        for(RepayPlan plan : allPlans)
        {
            if (plan.plantype.equals(testRE))        //slightly flawed time frame since months is based on assumptions my model doesn't make but ok for now
                {
                    months=plan.loanTime;

                    for (int j = 0; j <= months ; j++)
                    {
                        yvals3.add(new Entry(IBRresults.get(0), j) );
  //                      xvals1.add("");
                    }

                }
        }

        months=0;
        String testPA= "PA";
        for(RepayPlan plan : allPlans)
        {
            if (plan.plantype.equals(testPA))        //slightly flawed time frame since months is based on assumptions my model doesn't make but ok for now
            {
                months=plan.loanTime;

                for (int j = 0; j <= months ; j++)
                {
                    yvals4.add(new Entry(IBRresults.get(1), j) );
                    //                      xvals1.add("");
                }

            }
        }

        months=0;
        String testIB= "IB";
        for(RepayPlan plan : allPlans)
        {
            if (plan.plantype.equals(testIB))        //slightly flawed time frame since months is based on assumptions my model doesn't make but ok for now
            {
                months=plan.loanTime;

                for (int j = 0; j <= months ; j++)
                {
                    yvals5.add(new Entry(IBRresults.get(2), j) );
                    //                      xvals1.add("");
                }

            }
        }

        months=0;
        String testNB= "NB";
        for(RepayPlan plan : allPlans)
        {
            if (plan.plantype.equals(testNB))        //slightly flawed time frame since months is based on assumptions my model doesn't make but ok for now
            {
                months=plan.loanTime;

                for (int j = 0; j <= months ; j++)
                {
                    yvals6.add(new Entry(IBRresults.get(3), j) );
                    //                      xvals1.add("");
                }

            }
        }






        months=0;
        String testC3= "C3";
        for(RepayPlan plan : allPlans)
        {
            if (plan.plantype.equals(testC3))        //slightly flawed time frame since months is based on assumptions my model doesn't make but ok for now
            {
                months=plan.loanTime;

                for (int j = 0; j <= months ; j++)
                {

                    List<Float> ICRresult = aCalc.ICR();
                    float populate;

                    if (ICRresult.get(0)<ICRresult.get(1))
                    {
                        populate=ICRresult.get(0);
                    } else
                    {
                        populate=ICRresult.get(1);
                    }


                    yvals7.add(new Entry(populate, j) );
                    //                      xvals1.add("");
                }

            }
        }


        chart2.setDrawGridBackground(false);

        XAxis xAxis = chart2.getXAxis();
        //     xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = chart2.getAxisLeft();
        //     yAxis.setTextColor(Color.WHITE);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


        LineDataSet ydata1 = new LineDataSet(yvals1, "Fixed 10 Year");  //4. New BarDataSet to contain yvalues and descriptions
        LineDataSet ydata2 = new LineDataSet(yvals2, "Fixed 25 Year");
        LineDataSet ydata3 = new LineDataSet(yvals3, "REPAYE");
        LineDataSet ydata4 = new LineDataSet(yvals4, "PAYE");
        LineDataSet ydata5 = new LineDataSet(yvals5, "IBR");
        LineDataSet ydata6 = new LineDataSet(yvals6, "IBR-New");
        LineDataSet ydata7 = new LineDataSet(yvals7, "ICR");

        ydata1.setColor(-16777216);
        ydata1.setFillColor(-65536);
        ydata2.setColor(-16777216);
        ydata2.setFillColor(-78536);
        ydata3.setColor(-16777216);
        ydata3.setFillColor(-16776961);
        ydata4.setColor(-16777216);
        ydata4.setFillColor( -16711681);
        ydata5.setColor(-16777216);
        ydata5.setFillColor( -16711936);
        ydata6.setColor(-16777216);
        ydata6.setFillColor(-165281);
        ydata7.setColor(-16777216);
        ydata7.setFillColor(-9711681);

        ydata1.setDrawCircles(false);
        ydata2.setDrawCircles(false);
        ydata3.setDrawCircles(false);
        ydata4.setDrawCircles(false);
        ydata5.setDrawCircles(false);
        ydata6.setDrawCircles(false);
        ydata7.setDrawCircles(false);

        ydata1.setDrawFilled(true);
        ydata2.setDrawFilled(true);
        ydata3.setDrawFilled(true);
        ydata4.setDrawFilled(true);
        ydata5.setDrawFilled(true);
        ydata6.setDrawFilled(true);
        ydata7.setDrawFilled(true);


        List<ILineDataSet> dataSets = new ArrayList<>();     //5. Add the BarDataSet (ydata) to the set of all datasets available to graph within a List containing IBarDataSet objects
        dataSets.add(ydata1);
        dataSets.add(ydata2);
        dataSets.add(ydata3);
        dataSets.add(ydata4);
        dataSets.add(ydata5);
        dataSets.add(ydata6);
        dataSets.add(ydata7);

//        YAxis yRaXxis = chart2.getAxisRight();
//       yRaXxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


        LineData data = new LineData(xvals1, dataSets);        //6. Add the set of all data to the master data container along with the x labels;
       // data.setValueTextSize(10f);

        chart2.setData(data);

        //       Legend l = chart2.getLegend();
        //       l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        //       l.setCustom(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, new String[]{"Standard 10 Year", "Graduated 10 Year", "Extended 20 Year", "Extended Graduated 20 Year", "Revised P.A.Y.E 25 Year", "P.A.Y.E 20 Year", "Income Based 25 Year", "IBR New Borrowers", "Income Continent Repayment"});
        //       l.setTextColor(Color.WHITE);
        //       l.setTextSize(15f);

        chart2.setDescription("");
        chart2.animateXY(1000, 1000);
        chart2.invalidate();

        return view;
    }

    private int[] getColors() {

        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < stacksize; i++) {
            colors[i] = ColorTemplate.PASTEL_COLORS[i];
        }

        return colors;
    }










}