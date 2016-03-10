package com.prototype.princess.trainingwheels;

import java.util.ArrayList;
import java.util.List;



public class Loan {

    double principal;
    double interestRate;
    String loanType;
    static List<Loan> allLoans = new ArrayList<>();

    public Loan(double principalinput, double interestRateinput, String type)
    {
        principal=principalinput;
        interestRate=interestRateinput;
        loanType=type;
        allLoans.add(this);
    }

    public static List<Loan> getAllLoans()
    {
        return allLoans;
    }


}