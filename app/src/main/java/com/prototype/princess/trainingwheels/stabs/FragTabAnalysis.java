package com.prototype.princess.trainingwheels.stabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FragTabAnalysis extends Fragment {

    int loanTime;
    int income = Integer.parseInt(FragTabInfo.incomeValue);
    double runningTotal;
    double runningIncome;
    double runningTaxRate;
    double workingTotal;
    double initPayment;
    double finPayment;
    double diffOfPayments;
    double forgiven;
    double taxburden;
    double taxchunks;
    double sumInterest;
    double sumTotal;
    double principal = sumTotal-sumInterest;
    int taxcoord1;
    int taxcoord2;

    double[] taxBracketsPercent2016 = {.10, .15, .25, .28, .33, .35, .396};
    double[] taxBracketsGrossSingle2016 = {9275, 37650, 91150, 190150, 413350, 415050};
    double[] taxBracketsGrossJoint2016 = {18550, 75300, 151900, 231450, 413350, 466950};
    double[] taxBracketsGrossHead2016 = {13250, 50400, 130150, 210800, 413350, 441000};
    double[][] taxContainer = {taxBracketsGrossSingle2016, taxBracketsGrossJoint2016, taxBracketsGrossHead2016};

    public void TaxCalc ()
    {

        runningTotal=forgiven;
        FilingCalc(); //determines whether Single, Joint, Head Tax filing status array used

        for (int i = 5; runningTotal > 0; i--)
        {
            if (runningTotal > taxContainer[taxcoord1][i])
            {
                double taxRate = taxBracketsPercent2016[i];
                workingTotal = taxRate * runningTotal;  //tax owed in this bracket
                runningTotal=runningTotal-(runningTotal-taxContainer[taxcoord1][i-1]);
                //ex: 500,000=500,000-(500,000-413,350) <--86350, new runningTotal=41350, next bracket
            }

        }

    }

    public void FilingCalc ()
    {
        if ( FragTabInfo.filingstatus.equals("SINGLE") || FragTabInfo.filingstatus.equals("MARRIED_FILING_SINGLY") )
        {
            taxcoord1 = 0;
        }
        else if ( FragTabInfo.filingstatus.equals("MARRIED_FILING_JOINTLY"))
        {
            taxcoord1 = 1;
        }
        else //use head of household rate
        {
            taxcoord1 = 2;
        }
    }


}

