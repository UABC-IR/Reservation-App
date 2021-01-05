package com.example.reservationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.reservationapp.Fragments.MyReservationFragment;
import com.example.reservationapp.Fragments.TopFragment;
import com.example.reservationapp.Fragments.UserInfoFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager =  findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout =findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_create_reservation) {
            Intent intent = new Intent(MainActivity.this, CreateReservationActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter{
        public SectionsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new TopFragment();
                case 1: return new MyReservationFragment();
                case 2: return new UserInfoFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return getResources().getText(R.string.home_tab);
                case 1: return getResources().getText(R.string.reservation_tab);
                case 2: return getResources().getText(R.string.profile_tab);
            }
            return null;
        }
    }
}