package com.prototype.princess.trainingwheels;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;

import com.prototype.princess.trainingwheels.stabs.FragTabAnalysis;
import com.prototype.princess.trainingwheels.stabs.FragTabCalc;
import com.prototype.princess.trainingwheels.stabs.FragTabInfo;
import com.prototype.princess.trainingwheels.stabs.FragTabLoan;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class PostComThread implements Runnable {

    String stateChoice = FragTabInfo.statePick;
    String[] loanArray = FragTabLoan.loanArray;
    int arrayCount = FragTabLoan.arrayCount;
    int loanCounter = FragTabLoan.loanCounter;
    String familysizeInput = FragTabInfo.familysizeInput;
    String incomeValue = FragTabInfo.incomeValue;
    String spouseIncomeValue =FragTabInfo.spouseIncomeValue;
    String filingstatus = FragTabInfo.filingstatus;
    String isMarried = FragTabInfo.isMarried;
    String unDecodedresp;
    int loanArrayIndex;

    int first =0;

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);



        FragTabCalc.serverResponce = "Response Here -background";
        String urlTarget = "https://studentloans.gov/myDirectLoan/mobile/repayment/computeRepaymentPlans.action";



        OkHttpClient myClient = new OkHttpClient();

        JSONArray masterSingleContainer = new JSONArray();
        JSONArray masterSpouseContainer = new JSONArray();
        //JSONObject amanualLoan = new JSONObject();   //one amanualLoan object per single loan
        //JSONObject aspouseLoan = new JSONObject();   //one aspouseLoan object per spouse loan
        JSONArray repaymentProgs = new JSONArray();

        JSONObject sf = new JSONObject();
        repaymentProgs.put(sf);
        JSONObject ef = new JSONObject();
        repaymentProgs.put(ef);
        JSONObject re = new JSONObject();
        repaymentProgs.put(re);
        JSONObject pa = new JSONObject();
        repaymentProgs.put(pa);
        JSONObject ib = new JSONObject();
        repaymentProgs.put(ib);
        JSONObject c3 = new JSONObject();
        repaymentProgs.put(c3);

        JSONObject[] manualLoans = new JSONObject[loanCounter];
        JSONObject[] typeArray = new JSONObject[loanCounter];

        StringBuilder manualLoanConcat = new StringBuilder();
        manualLoanConcat.append("[");

        for (int i = 0; i < loanCounter; i++) {

            try {

                manualLoans[i] = new JSONObject();
                typeArray[i] = new JSONObject();

                manualLoans[i].put("type", typeArray[i]);
                typeArray[i].put("code", loanArray[loanArrayIndex]);
                loanArrayIndex++;
                typeArray[i].put("name", loanArray[loanArrayIndex]);
                loanArrayIndex++;
                typeArray[i].put("category", loanArray[loanArrayIndex]);
                loanArrayIndex++;

                typeArray[i].put("eligibleRepaymentPrograms", repaymentProgs);


                try {
                    sf.put("name", "SF");
                    sf.put("eligible", "true");

                    ef.put("name", "EF");
                    ef.put("eligible", "true");

                    re.put("name", "RE");
                    re.put("eligible", "true");

                    pa.put("name", "PA");
                    pa.put("eligible", "true");

                    ib.put("name", "IB");
                    ib.put("eligible", "true");

                    c3.put("name", "C3");
                    c3.put("eligible", "true");



                    manualLoans[i].put("balance", loanArray[loanArrayIndex]);
                    loanArrayIndex++;
                    manualLoans[i].put("interestRate", loanArray[loanArrayIndex]);
                    loanArrayIndex++;
                    manualLoans[i].put("loanDate", "undefined"); //not sure if these need to be here
                    manualLoans[i].put("servicer", "undefined");
                    manualLoans[i].put("firstDisbursementDate", "null");
                    manualLoans[i].put("subsidyLossDate", "N/A");

                    manualLoanConcat.append( manualLoans[i].toString());
                    if (i!=loanCounter-1) {
                        manualLoanConcat.append(",");
                    }

                } catch (JSONException e) {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        manualLoanConcat.append("]");
        loanCounter=0;
        arrayCount=0;

        String jsonSinglePayload = manualLoanConcat.toString();
        //String jsonSpousePayload = .toString();
        jsonSinglePayload = jsonSinglePayload.replace("N\\/A", "N/A");
        //jsonSpousePayload = jsonSpousePayload.replace("N\\/A", "N/A");

        //end build json array object

        RequestBody myForm = new FormEncodingBuilder()
                .add("isMarried", isMarried)
                .add("stateCode", stateChoice)
                .add("grossIncome", incomeValue)
                .add("spouseGrossIncome", spouseIncomeValue)
                .add("filingStatus", filingstatus)
                .add("familySize", familysizeInput)
                .add("manuallyAddedLoans", jsonSinglePayload)
                .add("spouseLoans", "[]")
                .build();

        Request myRequest = new Request.Builder()
                .url(urlTarget)
                .post(myForm)
                .build();

        Response myResponse = null;

        try {
            myResponse = myClient.newCall(myRequest).execute();
            unDecodedresp = myResponse.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }


        FragTabCalc.serverResponce = "";

        try {
            JSONObject parsedResponce = new JSONObject(unDecodedresp);
            JSONArray repayPlans = parsedResponce.getJSONArray("repaymentPlanTypes");

            Log.v("server responce is: ", parsedResponce.toString());

            for (int i = 0; repayPlans.getJSONObject(i) != null; i++) {

                JSONObject individualPlans = repayPlans.getJSONObject(i);

                String plantype = individualPlans.getString("repaymentPlan");
                int loanTime = individualPlans.getInt("loanPeriod");
                int initPayment = individualPlans.getInt("initialMonthlyPayment");
                int finPayment = individualPlans.getInt("finalMonthlyPayment");
                float forgiven = individualPlans.getInt("amountForgiven");
                int sumInterest = individualPlans.getInt("totalInterestPaid");
                int sumTotal = individualPlans.getInt("totalAmountPaid");
                //later I might want to see if I need to use the includesExtended, has BothLoanTypes, and hasConsolLoansOnly bools from post Response.
                FragTabCalc.serverResponce = FragTabCalc.serverResponce + ("Repayment Type: " + plantype + "\nLoan Period (Months): " + loanTime + "\nInitial Payment Amount: $" + initPayment + "\nFinal Payment Amount: $" + finPayment + "\nAmount Forgiven: $" + forgiven + "\nTotal Interest Payed: $" + sumInterest + "\nTotal Sum Payed: $" + sumTotal + "\n\n");


                    FragTabAnalysis.cost[i]=sumTotal-sumInterest;
                    FragTabAnalysis.interest[i]=sumInterest;
                    if (forgiven >= 50)
                    {

                        TaxCalc newTaxcalc = new TaxCalc();
                        FragTabAnalysis.tax[i]= newTaxcalc.Calculation((double)forgiven);
                    }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
