package com.prototype.princess.trainingwheels.stabs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
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

import com.prototype.princess.trainingwheels.R;
import com.prototype.princess.trainingwheels.AsyncRequest;

public class FragTabLoan extends ListFragment {



    public static String[] loanArray = new String[120];

    public static int arrayCount;
    public static int loanCounter;

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
    //fed perkins and private return no eligbl repay methods from web calculator
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

    String[] loanSummary;
    String lastloantype;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1loan, container, false);

        Button loanSelectbutton = (Button) view.findViewById(R.id.loanSelectbutton);
        loanSelectbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("Select Your Tax Filing State");
                builder1.setTitle("Select Loan Type");
                builder1.setItems(loantypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loanArray[arrayCount] = loanCodes[which];
                        arrayCount++;
                        loanArray[arrayCount] = loantypes[which];
                        arrayCount++;
                        loanArray[arrayCount] = loanCategory[which];
                        arrayCount++;

                        lastloantype=loantypes[which];
                    }
                });
                AlertDialog loanDialog = builder1.create();
                loanDialog.show();

            }
        });

        Button addLoan = (Button) view.findViewById(R.id.addLoan);
        addLoan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText debtInput = (EditText) v.getRootView().findViewById(R.id.debtInput);
                String debtValue = debtInput.getText().toString();

                EditText aprInput = (EditText) v.getRootView().findViewById(R.id.aprInput);
                String aprValue = aprInput.getText().toString();


                loanArray[arrayCount] = debtValue;
                arrayCount++;
                loanArray[arrayCount] = aprValue;
                arrayCount++;
                loanCounter++;

                String loanListEntry = "Loan #" +loanCounter + " " + lastloantype + " $" + debtValue + " @ %" + aprValue + "\n";
                loanSummary[0]= loanListEntry;

            }
        });

        return view;
    }

}