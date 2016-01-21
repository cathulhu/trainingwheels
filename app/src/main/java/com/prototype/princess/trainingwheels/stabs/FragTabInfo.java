package com.prototype.princess.trainingwheels.stabs;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.prototype.princess.trainingwheels.R;
import com.prototype.princess.trainingwheels.AsyncRequest;

public class FragTabInfo extends Fragment {

    String[] stateList = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI",
            "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MH", "MI", "MN", "MO", "MS", "MT",
            "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR", "PW", "RI", "SC",
            "SD", "TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"};

    String[] taxFilingOptions = {"SINGLE","MARRIED_FILING_SINGLY", "MARRIED_FILING_JOINTLY", "HEAD_OF_HOUSEHOLD"};

    public static String statePick;
    public static String familysizeInput;
    public static String filingstatus;
    public static String isMarried="false";
    public static String incomeValue;
    public static String spouseIncomeValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab0info, container, false);

        Button stateButton = (Button) view.findViewById(R.id.stateButton);
        stateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("Select Your Tax Filing State");
                builder1.setItems(stateList, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        statePick = stateList[which];
                    }
                });
                AlertDialog loanDialog = builder1.create();
                loanDialog.show();

                EditText familysizeField = (EditText) view.findViewById(R.id.familysizeField);
                familysizeInput = familysizeField.getText().toString();

                EditText incomeInput = (EditText) view.findViewById(R.id.incomeInput);
                incomeValue = incomeInput.getText().toString();

                EditText spouseIncomeInput = (EditText) view.findViewById(R.id.spouseIncomeInput);
                spouseIncomeValue = spouseIncomeInput.getText().toString();
            }
        });

        RadioGroup taxStatusRadio = (RadioGroup) view.findViewById(R.id.taxInput);
        /*
        RadioGroup singleRadio = (RadioGroup) view.findViewById(R.id.singleRadio);
                RadioGroup marriedSepRadio = (RadioGroup) view.findViewById(R.id.marriedSepRadio);
                RadioGroup marriedTogRadio = (RadioGroup) view.findViewById(R.id.marriedTogRadio);
                RadioGroup headRadio = (RadioGroup) view.findViewById(R.id.taxInput);

         */



        taxStatusRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioGroup taxStatusRadio = (RadioGroup) view.findViewById(R.id.taxInput);
                RadioButton checkedRadioButton = (RadioButton)taxStatusRadio.findViewById(checkedId);

                int choice = taxStatusRadio.indexOfChild(checkedRadioButton)-1;
                filingstatus=taxFilingOptions[choice];

                if (choice == 1 || choice == 2) {
                    isMarried = "true";
                }
            }
        });



        return view;
    }

}