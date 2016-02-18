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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.prototype.princess.trainingwheels.AsyncRequest;
import com.prototype.princess.trainingwheels.PostComThread;
import com.prototype.princess.trainingwheels.R;
import java.util.ArrayList;
import java.util.List;


public class FragTabAnalysis extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab3anal, container, false);

        BarChart chart = (BarChart) view.findViewById(R.id.chart);

        List<BarEntry> yvals = new ArrayList<>();   //1. make y values list for graph
        yvals.add(new BarEntry(9, 0));              //2. Add y values (value, index) to list
        yvals.add(new BarEntry(20, 1));

        List<String> xvals = new ArrayList<String>();   //3. make x value labels
        xvals.add("one");
        xvals.add("two");

        BarDataSet ydata = new BarDataSet(yvals, "label");  //4. New BarDataSet to contain yvalues and descriptions
        ydata.setBarSpacePercent(55f);
        ydata.setColor(Color.rgb(192, 255, 140));

        List<IBarDataSet> dataSets = new ArrayList<>();     //5. Add the BarDataSet (ydata) to the set of all datasets available to graph within a List containing IBarDataSet objects
        dataSets.add(ydata);

        BarData data = new BarData(xvals, dataSets);        //6. Add the set of all data to the master data container along with the x labels;
        data.setValueTextSize(10f);

        chart.setData(data);

        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();

        return view;
    }
}







//public class FragTabAnalysis extends Fragment {
//
//    int loanTime;
//    int income = Integer.parseInt(FragTabInfo.incomeValue);
//    double runningTotal;
//    double runningIncome;
//    double runningTaxRate;
//    double workingTotal;
//    double initPayment;
//    double finPayment;
//    double diffOfPayments;
//    double forgiven;
//    double taxburden;
//    double taxchunks;
//    double sumInterest;
//    double sumTotal;
//    double principal = sumTotal-sumInterest;
//    int taxcoord1;
//    int taxcoord2;
//    double incomeBracketDiff;
//
//    double[] taxBracketsPercent2016 = {.10, .15, .25, .28, .33, .35, .396};
//    double[] taxBracketsGrossSingle2016 = {0, 9275, 37650, 91150, 190150, 413350, 415050};
//    double[] taxBracketsGrossJoint2016 = {0, 18550, 75300, 151900, 231450, 413350, 466950};
//    double[] taxBracketsGrossHead2016 = {0, 13250, 50400, 130150, 210800, 413350, 441000};
//    double[][] taxContainer = {taxBracketsGrossSingle2016, taxBracketsGrossJoint2016, taxBracketsGrossHead2016};
//
//    public void TaxCalc ()
//    {
//
//        runningTotal=forgiven;
//        FilingCalc(); //determines whether Single, Joint, Head Tax filing status array used
//
//        for (int i = 6; runningTotal > 0; i--)
//        {
//            if (runningTotal > taxContainer[taxcoord1][i])
//            {
//                double taxRate = taxBracketsPercent2016[i];
//                incomeBracketDiff= runningTotal-(taxContainer[taxcoord1][i]);
//                runningTotal= runningTotal - incomeBracketDiff;
//                //ex: 500,000=500,000-(500,000-413,350) <--86350, new runningTotal=41350, next bracket
//                workingTotal += taxRate * incomeBracketDiff;  //tax owed
//            }
//
//        }
//
//    }
//
//    public void FilingCalc ()
//    {
//        if ( FragTabInfo.filingstatus.equals("SINGLE") || FragTabInfo.filingstatus.equals("MARRIED_FILING_SINGLY") )
//        {
//            taxcoord1 = 0;
//        }
//        else if ( FragTabInfo.filingstatus.equals("MARRIED_FILING_JOINTLY"))
//        {
//            taxcoord1 = 1;
//        }
//        else //use head of household rate
//        {
//            taxcoord1 = 2;
//        }
//    }
//
//
//}

