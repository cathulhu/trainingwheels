package com.prototype.princess.trainingwheels;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            String manuallyAddedLoans = "[{\"type\":{\"code\":\"D1\",\"name\":\"Direct Subsidized Loan\",\"category\":\"DIRECT_SUBSIDIZED\",\"eligibleRepaymentPrograms\":[{\"name\":\"SF\",\"eligible\":\"true\"},{\"name\":\"EF\",\"eligible\":\"true\"},{\"name\":\"RE\",\"eligible\":\"true\"},{\"name\":\"PA\",\"eligible\":\"true\"},{\"name\":\"IB\",\"eligible\":\"true\"},{\"name\":\"C3\",\"eligible\":\"true\"}]},\"balance\":\"54321\",\"interestRate\":\"6.6\",\"loanDate\":undefined,\"servicer\":undefined,\"firstDisbursementDate\":null,\"subsidyLossDate\":\"N/A\"}]";

            RequestBody myForm = new FormEncodingBuilder()
                    .add("isMarried", "false")
                    .add("stateCode", "OR")
                    .add("grossIncome", incomeValue)
                    .add("spouseGrossIncome", "0")
                    .add("familySize", "1")
                    .add("manuallyAddedLoans", manuallyAddedLoans)
                    .add("spouseLoans", "[]")
                    .build();

            Request myRequest = new Request.Builder()
                    .url(urlTarget)
                    .post(myForm)
                    .build();

            Response myResponse = null;
            try {
                myResponse = myClient.newCall(myRequest).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
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

            TextView respText = (TextView)findViewById(R.id.respText);
            respText.setText("Data Received: " + unDecodedresp);

        }

    }

}