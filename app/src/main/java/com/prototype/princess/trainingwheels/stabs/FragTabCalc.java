package com.prototype.princess.trainingwheels.stabs;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.prototype.princess.trainingwheels.AsyncRequest;
import com.prototype.princess.trainingwheels.PostComThread;
import com.prototype.princess.trainingwheels.R;

public class FragTabCalc extends Fragment {

    public static String serverResponce="1";

    public void viewRefresh () {

        serverResponce="function";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab2calc, container, false);
        Button postButton = (Button) view.findViewById(R.id.postButton);
        TextView response = (TextView) view.findViewById(R.id.respText);
        response.setText(serverResponce);
        //notice view gives us access to all the view elements inside tab2calc via inflation.

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                new AsyncRequest().execute("", "", "");
                TextView respText = (TextView) view.getRootView().findViewById(R.id.respText);
                respText.setText(serverResponce);
                */

                TextView response = (TextView) view.findViewById(R.id.respText);
                Thread PostSender = new Thread(new PostComThread());
                PostSender.start();
                //viewRefresh();
                response.setText(serverResponce);

            }
        });

        return view;
    }

}