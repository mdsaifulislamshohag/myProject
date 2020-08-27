package com.me.noban.DengueSolution.BloodBank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.me.noban.DengueSolution.BloodBank.bloodFragment.ABnegative;
import com.me.noban.DengueSolution.BloodBank.bloodFragment.ABposetive;
import com.me.noban.DengueSolution.BloodBank.bloodFragment.Anegative;
import com.me.noban.DengueSolution.BloodBank.bloodFragment.Aposetive;
import com.me.noban.DengueSolution.BloodBank.bloodFragment.Bnegative;
import com.me.noban.DengueSolution.BloodBank.bloodFragment.Bposetive;
import com.me.noban.DengueSolution.BloodBank.bloodFragment.Onegative;
import com.me.noban.DengueSolution.BloodBank.bloodFragment.Oposetive;

public class pagerAdapter extends FragmentPagerAdapter {
    int numcount;

    public pagerAdapter(FragmentManager fm, int Numtabs) {
        super(fm);
        this.numcount = Numtabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Aposetive tab1 = new Aposetive();
                return tab1;
            case 1:
                Bposetive tab2 = new Bposetive();
                return tab2;
            case 2:
                Oposetive tab3 = new Oposetive();
                return tab3;
            case 3:
                ABposetive tab4 = new ABposetive();
                return tab4;
            case 4:
                Anegative tab5 = new Anegative();
                return tab5;
            case 5:
                Bnegative tab6 = new Bnegative();
                return tab6;
            case 6:
                Onegative tab7 = new Onegative();
                return tab7;
            case 7:
                ABnegative tab8 = new ABnegative();
                return tab8;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numcount;
    }
}
