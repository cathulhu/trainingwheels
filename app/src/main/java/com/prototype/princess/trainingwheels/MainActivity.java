package com.prototype.princess.trainingwheels;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    boolean isMarried;
    String[] loantypes =
            {       "Direct Subsidized Loan",
                    "Direct Unsubsidized Loan",
                    "Subsidized Federal Stafford Loan",
                    "Unsubsidized Federal Stafford Loan",
                    "Direct Subsidized Consolidation Loan",
                    "Direct Unsubsidized Consolidation Loan",
                    "FFEL Consolidation Loan",
                    "Direct PLUS Loan for Graduate/Professional Students",
                    "FFEL PLUS Loan for Graduate/Professional Students",
                    "Direct PLUS Loan for Parents",
                    "FFEL PLUS Loan for Parents",
                    "Direct PLUS Consolidation Loan",
                    " **NOT IMPLIMENTED** Federal Perkins Loan",
                    " **NOT IMPLIMENTED** Private Loan"};
                    //fed perkins and private return no eligb repay methods from web calculator
                    //later I can impliment my own private loan calculator

    String[] loanCategory =
            {
                    "DIRECT_SUBSIDIZED",
                    "DIRECT_UNSUBSIDIZED",
                    "FFEL_SUBSIDIZED",
                    "FFEL_UNSUBSIDIZED",
                    "DIRECT_SUBSIDIZED",
                    "DIRECT_UNSUBSIDIZED",
                    "FFEL_UNSUBSIDIZED",
                    "DIRECT_PLUS",
                    "FFEL_PLUS",
                    "DIRECT_PLUS",
                    "FFEL_PLUS",
                    "DIRECT_PLUS",
                    "PERKINS",
                    "ADDITIONAL"
            };

    String[] loanCodes = {"D1", "D2", "SF", "SU","D6","D5", "CL", "D3", "GB", "D4", "PL", "D7", "PU", "PV"};
    int loanChoice;

    String[] stateList = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI",
            "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MH", "MI", "MN", "MO", "MS", "MT",
            "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR", "PW", "RI", "SC",
            "SD", "TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"};

    String stateChoice;

    String[] loanArray = new String[120];
    int arrayCount;
    int loanCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RadioGroup taxInput = (RadioGroup) findViewById(R.id.taxInput);
        final RadioButton singleRadio = (RadioButton) findViewById(R.id.singleRadio);
        final RadioButton headRadio = (RadioButton) findViewById(R.id.headRadio);
        Button postButton = (Button) findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAsyncTask().execute();
            }
        });


        taxInput.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (singleRadio.isChecked() || headRadio.isChecked()) {
                    isMarried = false;
                } else {
                    isMarried = true;
                }
            }
        });


        Button loanSelectbutton = (Button) findViewById(R.id.loanSelectbutton);
        loanSelectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("Select Loan Type");
                builder1.setItems(loantypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loanArray[arrayCount]=loanCodes[which];
                        arrayCount++;
                        loanArray[arrayCount]=loantypes[which];
                        arrayCount++;
                        loanArray[arrayCount]=loanCategory[which];
                        arrayCount++;
                    }
                });
                AlertDialog loanDialog = builder1.create();
                loanDialog.show();
            }
        });

        Button stateButton = (Button) findViewById(R.id.stateButton);
        stateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle("Select Your Tax Filing State");
                builder2.setItems(stateList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stateChoice=stateList[which];
                    }
                });
                AlertDialog loanDialog = builder2.create();
                loanDialog.show();
            }
        });

        Button addLoan = (Button) findViewById(R.id.addLoan);
        addLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView loanList = (TextView) findViewById(R.id.loanList);

                EditText debtInput = (EditText) findViewById(R.id.debtInput);
                String debtValue = debtInput.getText().toString();

                EditText aprInput = (EditText) findViewById(R.id.aprInput);
                String aprValue = aprInput.getText().toString();


                loanArray[arrayCount]=debtValue;
                arrayCount++;
                loanArray[arrayCount]=aprValue;
                arrayCount++;
                loanCounter++;
                String loanListEntry = "Loan #"+ loanCounter +" $" + debtValue + " @ %" + aprValue + "\n";
                loanList.append(loanListEntry);
            }
        });

    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {

        String unDecodedresp;
        int loanArrayIndex;

        EditText familysizeField = (EditText) findViewById(R.id.familysizeField);
        String familysizeInput = familysizeField.getText().toString();

        EditText incomeInput = (EditText) findViewById(R.id.incomeInput);
        String incomeValue = incomeInput.getText().toString();

        EditText spouseIncomeInput = (EditText) findViewById(R.id.spouseIncomeInput);
        String spouseIncomeValue = spouseIncomeInput.getText().toString();

        String married = String.valueOf(isMarried);

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "post invoke", Toast.LENGTH_LONG).show();
            TextView respText = (TextView) findViewById(R.id.respText);
            TextView loanList = (TextView) findViewById(R.id.loanList);
            loanList.setText("");

        }

        @Override
        protected String doInBackground(String... params) {     // Runs in background thread

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
            Arrays.fill(loanArray, "0");
/*
            if (spouseLoannum != 0) ;
            {
                for (int i = 0; i < loanCounter; i++) {

                    try {
                        masterSpouseContainer.put(aspouseLoan);
                        aspouseLoan.put("type", type);

                        type.put("category", loanArray[loanArrayIndex]);
                        type.put("code", loanArray[loanArrayIndex++]);
                        type.put("name", loantypes[loanArrayIndex++]);

                        type.put("eligibleRepaymentPrograms", repaymentProgs);

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

                            aspouseLoan.put("balance", loantypes[loanArrayIndex++]);
                            aspouseLoan.put("interestRate", loantypes[loanArrayIndex++]);
                            aspouseLoan.put("loanDate", "undefined"); //not sure if these need to be here
                            aspouseLoan.put("servicer", "undefined");
                            aspouseLoan.put("firstDisbursementDate", null);
                            aspouseLoan.put("subsidyLossDate", "N/A");


                        } catch (JSONException e) {

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
*/
            String jsonSinglePayload = manualLoanConcat.toString();
            //String jsonSpousePayload = .toString();
            jsonSinglePayload = jsonSinglePayload.replace("N\\/A", "N/A");
            //jsonSpousePayload = jsonSpousePayload.replace("N\\/A", "N/A");

            //end build json array object

            RequestBody myForm = new FormEncodingBuilder()
                    .add("isMarried", married)
                    .add("stateCode", stateChoice)
                    .add("grossIncome", incomeValue)
                    .add("spouseGrossIncome", spouseIncomeValue)
                    .add("familySize", familysizeInput)
                    .add("manuallyAddedLoans", jsonSinglePayload)
                    .add("spouseLoans", "[]")
                    .build();

            Log.v("FormContents!", myForm.toString());


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

            return null;

        }

        @Override
        public void onPostExecute(String resp) {
            super.onPostExecute(resp);

            TextView respText = (TextView) findViewById(R.id.respText);
            respText.setText("");

            try {
                JSONObject parsedResponce = new JSONObject(unDecodedresp);
                JSONArray repayPlans = parsedResponce.getJSONArray("repaymentPlanTypes");

                //Log.v("server responce is: ", parsedResponce.toString());

                for (int i = 0; i < 9; i++) {

                    JSONObject individualPlans = repayPlans.getJSONObject(i);

                    String plantype = individualPlans.getString("repaymentPlan");
                    int loanTime = individualPlans.getInt("loanPeriod");
                    double initPayment = individualPlans.getInt("initialMonthlyPayment");
                    double finPayment = individualPlans.getInt("finalMonthlyPayment");
                    double forgiven = individualPlans.getInt("amountForgiven");
                    double sumInterest = individualPlans.getInt("totalInterestPaid");
                    double sumTotal = individualPlans.getInt("totalAmountPaid");
                    //later I might want to see if I need to use the includesExtended, has BothLoanTypes, and hasConsolLoansOnly bools from post Response.
                    respText.append("Repayment Type: " + plantype + "\nLoan Period (Months): " + loanTime + "\nInitial Payment Amount: $" + initPayment + "\nFinal Payment Amount: $" + finPayment + "\nAmount Forgiven: $" + forgiven + "\nTotal Interest Payed: $" + sumInterest + "\nTotal Sum Payed: $" + sumTotal + "\n\n");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}