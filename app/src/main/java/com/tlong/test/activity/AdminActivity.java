package com.tlong.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.tlong.test.R;
import com.tlong.test.adapter.*;
public class AdminActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    adminViewAdapter viewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        AnhXa();
    }

    private void AnhXa() {
        tabLayout=(TabLayout)findViewById(R.id.admin_tab);
        viewPager=(ViewPager)findViewById(R.id.admin_viewpaper);
        viewAdapter=new adminViewAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}