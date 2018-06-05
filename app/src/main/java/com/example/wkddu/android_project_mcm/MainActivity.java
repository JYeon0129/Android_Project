package com.example.wkddu.android_project_mcm;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int CALENDAR_FRAGMENT = 1;
    final int GROUP_FRAGMENT = 2;
    final int BILL_FRAGMENT = 3;
    final int SETTINGS_FRAGMENT = 4;

    LinearLayout menu_1, menu_2, menu_3, menu_4;

    int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        init();
    }

    public void init() {
        menu_1 = (LinearLayout) findViewById(R.id.menu_1);
        menu_2 = (LinearLayout) findViewById(R.id.menu_2);
        menu_3 = (LinearLayout) findViewById(R.id.menu_3);
        menu_4 = (LinearLayout) findViewById(R.id.menu_4);

        menu_1.setOnClickListener(this);
        menu_2.setOnClickListener(this);
        menu_3.setOnClickListener(this);
        menu_4.setOnClickListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        CalenderFragment calendarFragment = (CalenderFragment) new CalenderFragment();
        transaction.replace(R.id.mainFragmantContainer, calendarFragment);
        transaction.commit();

        menu_1.setSelected(true);
        currentFragment = CALENDAR_FRAGMENT;
    }

    @Override
    public void onClick(View v) {
        menu_1.setSelected(false);
        menu_2.setSelected(false);
        menu_3.setSelected(false);
        menu_4.setSelected(false);

        switch (v.getId()) {
            case R.id.menu_1:
                menu_1.setSelected(true);
                callFragment(CALENDAR_FRAGMENT);
                break;
            case R.id.menu_2:
                menu_2.setSelected(true);
                callFragment(GROUP_FRAGMENT);
                break;
            case R.id.menu_3:
                menu_3.setSelected(true);
                callFragment(BILL_FRAGMENT);
                break;
            case R.id.menu_4:
                menu_4.setSelected(true);
                callFragment(SETTINGS_FRAGMENT);
                break;
        }
    }

    private void callFragment(int fragmentNumber) {
        if (fragmentNumber != currentFragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

            switch (fragmentNumber) {
                case CALENDAR_FRAGMENT:
                    CalenderFragment calendarFragment = (CalenderFragment) new CalenderFragment();
                    transaction.replace(R.id.mainFragmantContainer, calendarFragment);
                    transaction.commit();
                    break;
                case GROUP_FRAGMENT:
                    GroupFragment groupFragment = (GroupFragment) new GroupFragment();
                    transaction.replace(R.id.mainFragmantContainer, groupFragment);
                    transaction.commit();
                    break;

                case BILL_FRAGMENT:
                    BillFragment billFragment = (BillFragment) new BillFragment();
                    transaction.replace(R.id.mainFragmantContainer, billFragment);
                    transaction.commit();
                    break;

                case SETTINGS_FRAGMENT:
                    SettingsFragment settingsFragment = (SettingsFragment) new SettingsFragment();
                    transaction.replace(R.id.mainFragmantContainer, settingsFragment);
                    transaction.commit();
                    break;
            }

            currentFragment = fragmentNumber;
        }
    }
}
