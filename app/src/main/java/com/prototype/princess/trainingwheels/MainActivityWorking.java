/*
package com.prototype.princess.trainingwheels;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivityWorking extends AppCompatActivity {

    public static final String TAG = MainActivityWorking.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button postButton = (Button) findViewById(R.id.postButton);

        String urlTarget = "http://httpbin.org/get?show_env=1";
                //"https://studentloans.gov/myDirectLoan/mobile/repayment/computeRepaymentPlans.action";
                //

        OkHttpClient myClient = new OkHttpClient();
        Request myRequest = new Request.Builder().url(urlTarget).build();

        Call myCall = myClient.newCall(myRequest);
        myCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response myResponse) throws IOException {

                try {

                    if (myResponse.isSuccessful()) {
                        Log.v(TAG, myResponse.body().string());
                    }

                } catch (IOException e) {
                    Log.e(TAG, "This is an error Exception: ", e);
                }

            }
        });

    }


    public class MyAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // Runs on UI thread- Any code you wants
            // to execute before web service call. Put it here.
            // Eg show progress dialog
        }

        @Override
        protected String doInBackground(String... params) {     // Runs in background thread

            return null;
        }

        @Override
        public void onPostExecute(String resp) {
            super.onPostExecute(resp);

            // runs in UI thread - You may do what you want with response
            // Eg Cancel progress dialog - Use result
        }

    }

}
*/