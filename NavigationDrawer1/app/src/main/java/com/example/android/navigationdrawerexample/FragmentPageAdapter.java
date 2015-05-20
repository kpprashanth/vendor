package com.example.android.navigationdrawerexample;

/**
 * Created by prashanth on 18/3/15.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
public class FragmentPageAdapter extends FragmentPagerAdapter {
    public static int choice;

    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int arg0) {
        choice=arg0;
        // TODO Auto-generated method stub
        switch (arg0) {
            case 0:
               return new MyFragment();
            case 1:
                return new MyFragment();
            case 2:
                return new MyFragment();
            default:
                break;
        }
        return null;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }
}
