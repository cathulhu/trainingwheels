package com.prototype.princess.trainingwheels;

import com.prototype.princess.trainingwheels.stabs.FragTabInfo;

public class TaxCalc {

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
    double incomeBracketDiff;

    double[] taxBracketsPercent2016 = {.10, .15, .25, .28, .33, .35, .396};
    double[] taxBracketsGrossSingle2016 = {0, 9275, 37650, 91150, 190150, 413350, 415050};
    double[] taxBracketsGrossJoint2016 = {0, 18550, 75300, 151900, 231450, 413350, 466950};
    double[] taxBracketsGrossHead2016 = {0, 13250, 50400, 130150, 210800, 413350, 441000};
    double[][] taxContainer = {taxBracketsGrossSingle2016, taxBracketsGrossJoint2016, taxBracketsGrossHead2016};

    public TaxCalc() {
        //this.Calculation(forgiven);
    }

    public double Calculation (double forgivenAndIncome)
    {

        runningTotal=forgivenAndIncome;
        FilingCalc(); //determines whether Single, Joint, Head Tax filing status array used

        for (int i = 6; runningTotal > 0; i--)
        {
            if (runningTotal > taxContainer[taxcoord1][i])                                              //for smaller example, 40k forgiven... 40k<415k, loop, 40k<413k, 40k<190k 40k<91l, 40k<37k = yes, start at i=2
            {                                                                       // example first time around loop income(loan forgiveness)= 500k
                double taxRate = taxBracketsPercent2016[i];                         //.396,
                incomeBracketDiff= runningTotal-(taxContainer[taxcoord1][i]);       //lets do single filing, rt=500k - (415050) , so iBD =84950
                runningTotal= runningTotal - incomeBracketDiff;                     //                       rt now = 500k- 84950, rt = 415050
                workingTotal += taxRate * incomeBracketDiff;                        // wt = 0.396*84950 = 33640.2
            }                                                                                      //second time around loop
                                                                                    //.35 rate,
        }                                                                           // Ibd = 415050-413350 = 1700
                return workingTotal;                                                // rt = 415050-1700 = 413350
    }                                                                               // wt = 0.35 * 1700 = 33640.2 + 595 = 34235.2

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
