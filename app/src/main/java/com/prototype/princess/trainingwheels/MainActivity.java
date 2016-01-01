package com.prototype.princess.trainingwheels;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button postButton = (Button) findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAsyncTask().execute();
            }
        });

    }



    public class MyAsyncTask extends AsyncTask <String, String, String> {

        private static final String TAG = "";
        String unDecodedresp;

        EditText incomeInput = (EditText) findViewById(R.id.incomeInput);
        String incomeValue = incomeInput.getText().toString();

        EditText debtInput = (EditText) findViewById(R.id.debtInput);
        String debtValue = debtInput.getText().toString();  //need to implement JSON parsing for this to be used


        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "post invoke", Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... params) {     // Runs in background thread

            String urlTarget = "https://studentloans.gov/myDirectLoan/mobile/repayment/computeRepaymentPlans.action";

            OkHttpClient myClient = new OkHttpClient();
           // String manuallyAddedLoans = "[{\"type\":{\"code\":\"D1\",\"name\":\"Direct Subsidized Loan\",\"category\":\"DIRECT_SUBSIDIZED\",\"eligibleRepaymentPrograms\":[{\"name\":\"SF\",\"eligible\":\"true\"},{\"name\":\"EF\",\"eligible\":\"true\"},{\"name\":\"RE\",\"eligible\":\"true\"},{\"name\":\"PA\",\"eligible\":\"true\"},{\"name\":\"IB\",\"eligible\":\"true\"},{\"name\":\"C3\",\"eligible\":\"true\"}]},\"balance\":\"54321\",\"interestRate\":\"6.6\",\"loanDate\":undefined,\"servicer\":undefined,\"firstDisbursementDate\":null,\"subsidyLossDate\":\"N/A\"}]";
            //beging build json array object
            JSONArray masterContainerarray = new JSONArray();
                JSONObject manualLoans =new JSONObject();
                    JSONObject type = new JSONObject();
                        JSONArray repaymentProgs = new JSONArray();
            try{
                masterContainerarray.put(manualLoans);
                manualLoans.put("type", type);
                type.put("code", "D1");
                type.put("name", "Direct Subsidized Loan");
                type.put("category", "DIRECT_SUBSIDIZED");

                type.put("eligibleRepaymentPrograms", repaymentProgs);

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

                    manualLoans.put("balance", debtValue);
                    manualLoans.put("interestRate", "6.3");
                    manualLoans.put("loanDate", "undefined"); //not sure if these need to be here
                    manualLoans.put("servicer", "undefined");
                    manualLoans.put("firstDisbursementDate", null);
                    manualLoans.put("subsidyLossDate", "N/A");

                }
                catch (JSONException e) {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonPayload = new String();
            jsonPayload = masterContainerarray.toString();
            jsonPayload = jsonPayload.replace("N\\/A", "N/A");

            //end build json array object

            RequestBody myForm = new FormEncodingBuilder()
                    .add("isMarried", "false")
                    .add("stateCode", "OR")
                    .add("grossIncome", incomeValue)
                    .add("spouseGrossIncome", "0")
                    .add("familySize", "1")
                    .add("manuallyAddedLoans", jsonPayload)
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

            Log.v(TAG, unDecodedresp);

            return null;

        }

        @Override
        public void onPostExecute(String resp) {
            super.onPostExecute(resp);
            Toast.makeText(getApplicationContext(), "end invoke", Toast.LENGTH_LONG).show();

            TextView respText = (TextView) findViewById(R.id.respText);
            respText.setText("");

            try {
                JSONObject parsedResponce = new JSONObject(unDecodedresp);
                JSONArray repayPlans = parsedResponce.getJSONArray("repaymentPlanTypes");

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