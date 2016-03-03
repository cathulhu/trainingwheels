package com.prototype.princess.trainingwheels;


import java.util.ArrayList;
import java.util.List;

public class RepayPlan {

    String plantype;
    int loanTime;
    int initPayment;
    int finPayment;
    int forgiven;
    int sumInterest;
    int sumTotal;
    static List<RepayPlan> allPlans = new ArrayList<RepayPlan>();

    public RepayPlan(String pt, int lt, int ip, int fp, int fg, int si, int st) //constructor
    {
        plantype=pt;
        loanTime=lt;
        initPayment=ip;
        finPayment=fp;
        forgiven=fg;
        sumInterest=si;
        sumTotal=st;

        allPlans.add(this);
    }

    public static List<String> GetPlanNames()
    {
        List<String> names = new ArrayList<>();

        for(RepayPlan plan : allPlans){
            names.add(plan.plantype);
        }
        return names;
    }

    public static List<String> GetList()
    {
        List<String> details = new ArrayList<>();
        String fields;

        for(RepayPlan plan : allPlans){
            fields="Type: " + plan.plantype + "\n" + "Time (Months): " + plan.loanTime + "\n" + "Init Payment: " + plan.initPayment + "\n" + "Final Payment: " + plan.finPayment + "\n" + "Forgiven(Taxed): " + plan.forgiven + "\n" + "Interest Payed: " + plan.sumInterest + "\n" + "Total Payed: " + plan.sumTotal + "\n\n";
            details.add(fields);
        }
        return details;
    }

}
