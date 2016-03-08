package com.prototype.princess.trainingwheels.stabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
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
import android.widget.TextView;

import com.prototype.princess.trainingwheels.PaymentCalc;
import com.prototype.princess.trainingwheels.R;
import com.prototype.princess.trainingwheels.AsyncRequest;
import com.prototype.princess.trainingwheels.graphs.FragTotalGraph;

import java.util.ArrayList;
import java.util.List;


public class FragTabInterpolation extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab4interp, container, false);


        Fragment totalGraphFragment = new FragTotalGraph();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
     //   transaction.add(R.id.fragment, totalGraphFragment).commit();


        PaymentCalc aCalc = new PaymentCalc();
        double payments = aCalc.stdPayments(120);   //ten year plan
        String stdResult = "Fixed 10 year: " + Double.toString(payments) + "\nFixed 25 Year: " + Double.toString(aCalc.stdPayments(300));

        List<Double> IBRresults = new ArrayList<>();
        IBRresults= aCalc.IBR();

        String IBRout ="Repay, Paye, IBRnew, IBRold: "+ "\n" + IBRresults.get(0).toString() + "\n" + IBRresults.get(1).toString() + "\n" + IBRresults.get(2).toString() + "\n" + IBRresults.get(3).toString();

        List<Double> ICresults = new ArrayList<>();
        ICresults= aCalc.ICR();

        String ICRout = "20% Discretionary Income: " + ICresults.get(0).toString() + "\nICR 12 year * income percentage factor: " + ICresults.get(1).toString();



        TextView stdOutput = (TextView) view.getRootView().findViewById(R.id.stdPayOutput);
        stdOutput.setText(stdResult);

        TextView IBROutput = (TextView) view.getRootView().findViewById(R.id.ibrPayOutput);
        IBROutput.setText(IBRout);

        TextView ICROutput = (TextView) view.getRootView().findViewById(R.id.icrPayOutput);
        ICROutput.setText(ICRout);

        return view;
    }

}
