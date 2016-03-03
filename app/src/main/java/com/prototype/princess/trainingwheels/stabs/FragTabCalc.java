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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.prototype.princess.trainingwheels.R;
import com.prototype.princess.trainingwheels.AsyncRequest;

import java.util.ArrayList;

import com.prototype.princess.trainingwheels.AsyncRequest;
import com.prototype.princess.trainingwheels.PostComThread;
import com.prototype.princess.trainingwheels.R;
import com.prototype.princess.trainingwheels.RepayPlan;

public class FragTabCalc extends ListFragment {

    public static String serverResponce="1";

    public void viewRefresh () {

        serverResponce="function";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onActivityCreated(savedInstanceState);                                //will these maybe solve the problem of the results not appearing until I leave and come back to page? nope.
        final View view = inflater.inflate(R.layout.tab2calc, container, false);
        Button postButton = (Button) view.findViewById(R.id.postButton);
       // TextView response = (TextView) view.findViewById(R.id.respText);
       // response.setText(serverResponce);
        //notice view gives us access to all the view elements inside tab2calc via inflation.

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                new AsyncRequest().execute("", "", "");
                TextView respText = (TextView) view.getRootView().findViewById(R.id.respText);
                respText.setText(serverResponce);
                */

               // TextView response = (TextView) view.findViewById(R.id.respText);
                Thread PostSender = new Thread(new PostComThread());
                PostSender.start();
                //viewRefresh();
                //response.setText(serverResponce);

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, RepayPlan.GetListasString());
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

}