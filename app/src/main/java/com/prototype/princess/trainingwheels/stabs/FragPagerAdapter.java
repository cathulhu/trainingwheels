package com.prototype.princess.trainingwheels.stabs;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragPagerAdapter extends FragmentPagerAdapter {

    String[] tabtitles = {"Tax Info", "Loan Info", "Analysis"};

    private Context context;

    public FragPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new FragTabInfo();
        } else if (position == 1) {
            return new FragTabLoan();
        } else if (position == 2) {
            return new FragTabCalc();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }

}