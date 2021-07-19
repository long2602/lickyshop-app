package com.tlong.test.adapter;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tlong.test.activity.AccountFragment;
import com.tlong.test.activity.BillFragment;
import com.tlong.test.activity.ProductFragment;

public class adminViewAdapter extends FragmentStatePagerAdapter {
    public adminViewAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AccountFragment();
            case 1:
                return new ProductFragment();
            case 2:
                return new BillFragment();
            default:
                return new AccountFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title="Tài khoản";
                break;
            case 1:
                title="Sản phẩm";
                break;
            case 2:
                title="Hóa đơn";
                break;
        }
        return title;
    }
}
