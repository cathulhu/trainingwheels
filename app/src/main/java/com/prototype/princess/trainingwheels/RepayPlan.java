package com.prototype.princess.trainingwheels;


import java.util.ArrayList;
import java.util.List;

public class RepayPlan {

    public String plantype;
    public int loanTime;
    public int initPayment;
    public int finPayment;
    public int forgiven;
    public int sumInterest;
    public int sumTotal;
    static List<RepayPlan> allPlans = new ArrayList<RepayPlan>();

    public int principal;
    public int taxes;

    public RepayPlan(String pt, int lt, int ip, int fp, int fg, int si, int st) //constructor
    {
        plantype=pt;
        loanTime=lt;
        initPayment=ip;
        finPayment=fp;
        forgiven=fg;
        sumInterest=si;
        sumTotal=st;
        principal = sumTotal-sumInterest;
        TaxCalc newTaxcalc = new TaxCalc();
        taxes=newTaxcalc.Calculation(forgiven);

        //should perhaps use hash table later to convert abbreviation names into pretty names

        allPlans.add(this);
    }

    public static List<RepayPlan> GetAllPlans()
    {
        return allPlans;
    }

    public static List<String> GetListasString()
    {
        List<String> details = new ArrayList<>();
        String fields;

        for(RepayPlan plan : allPlans){
            fields="Type: " + plan.plantype + "\n" + "Time (Months): " + plan.loanTime + "\n" + "Init Payment: " + plan.initPayment + "\n" + "Final Payment: " + plan.finPayment + "\n" + "Principal: "+ plan.principal + "\n" + "Forgiven(Taxed): " + plan.forgiven + "\n" + "Taxes Owed: " + plan.taxes + "\n" + "Interest Payed: " + plan.sumInterest + "\n" + "Total Payed: " + plan.sumTotal + "\n";
            details.add(fields);
        }
        return details;
    }

    public static List<RepayPlan> GetList()
    {
        return allPlans;
    }

}
