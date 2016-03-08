package com.prototype.princess.trainingwheels;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

//repaye = 10% of discretionary income, IE (0.10*(agi-finhardshipline))/12

//paye = 10% of discretionary income OR std monthly payment, whichever is less

//IBR = 10% of discretionary income (for new borrowers after july 1 2014) OR  std monthly payments, whichever is less
//      OR 15% of discretionary income (if not a new borrower), OR  std monthly payments, whichever is less

//ICR = 20% of discretionary income or 12 year fixed plan * income percentage factor algorithm
        //income percentage factor:
        //take AGI and compare nearest (below and above) values for IPF, IE for 30k                                                 (36793 - 29337) = $10456 = income interval
        //minus the IPF from each of the below and above figures, here its                                                          100%-88% = 12% = income percentage factor interval
        //subtract closest chart value from income, here its                                                                        30,000-30,000 (its on chart) = $0
        //divide result by income interval (step 1 result),  here:                                                                  $0/$10456 = 0 <--- decimal result
        //multiply result by income percentage factor interval,                                                                     0 * 12 = 0% (note ones place 6.12 for 6.12% not 0.612)
        //Add the result to the income percentage factor that corresponds to the closest lesser chart value. Here =                 88.77% + 0%
        //The result is your income percentage factor.                                                                              88.77% = IPF
        //final payment is then                                                                                                     20% of discretionary income * 88.77%
        //see example PDF for a non zero example

public class PaymentCalc extends Fragment {

    List<Double> STDpayments;
    int principal;
    double monthlyRate;     //=rate/12

    double monthlyInterest;
    int standardPaymentMonthly; //will get this from other section of program

    public static int dumbTotalLoan;       //just adds up total of all loans with no type checking for now
    int familysize=0; //take this from loaninfo screen eventually
    int[] povertylevels = {11770};
    double AGI=20000; //take this from loaninfo screen (right now im just getting gross income, later implement actual AGI via gross minus deductions.
    double interestRateMonthly = (0.06)/12;     //fixed at 6 percent right now
    int numberMonths= 120; //fixed at 120 months for now

    int income;
    int interestTotal;
    double runningTotal;
    double workingTotal;
    double fixedPayment;

    static double finHardshipLine;
    double IBpaymentAnnual;
    double ICpaymentAnnual;
    double IBpaymentMonthly;

    double repayeAnnual;
    double payeAnnual;
    double IBRnewAnnual;
    double IBRoldAnnual;
    double ICRAnnual;

    public PaymentCalc()
    {
        finHardshipLine=povertylevels[familysize]*1.5;
        //code here will determine which IBR plans actually eligible for
    }

    public double stdPayments (int numberMonths)
    {
        // (rate*presentTotal) / (1- (1+rate)^-#months )

        dumbTotalLoan = 43000;
        runningTotal = dumbTotalLoan;
        double extendedFixed;

        fixedPayment = ( (interestRateMonthly)*runningTotal ) / ( 1- Math.pow( (1+interestRateMonthly), -numberMonths ) );

        return  fixedPayment;

    }

    public List<Double> IBR ()
    {
        List<Double> IBRepayeResults = new ArrayList<>();
        IBpaymentAnnual = (AGI-finHardshipLine);

        repayeAnnual = IBpaymentAnnual*0.10;
        payeAnnual= IBpaymentAnnual*0.10;
        IBRnewAnnual= IBpaymentAnnual *0.10;
        IBRoldAnnual = IBpaymentAnnual *0.15;

        IBRepayeResults.add(repayeAnnual/12);
        IBRepayeResults.add(payeAnnual/12);
        IBRepayeResults.add(IBRnewAnnual/12);
        IBRepayeResults.add(IBRoldAnnual/12);

        return IBRepayeResults;
    }

    public List<Double> ICR ()
    {
        List<Double> ICRepayeResults = new ArrayList<>();
        ICpaymentAnnual = (AGI-povertylevels[0]);   //note that for ICR discretionary income is just AGI-poverty level, no %150 here.
        ICRAnnual = ICpaymentAnnual * 0.2;

        double ICRfixed12 = this.stdPayments(144)*0.7273;   //ICR adjustment of 12 year fixed payment times 20000 ICR table adjustment

        ICRepayeResults.add(ICRAnnual/12);
        ICRepayeResults.add(ICRfixed12);

        return ICRepayeResults;
    }


}
