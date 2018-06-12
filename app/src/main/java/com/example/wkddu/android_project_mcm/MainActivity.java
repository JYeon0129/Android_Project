package com.example.wkddu.android_project_mcm;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int CALENDAR_FRAGMENT = 1;
    final int BILL_FRAGMENT = 2;
    final int SETTINGS_FRAGMENT = 3;
    final int TODO_DETAIL = 4;
    final int TODO_CREATE = 5;

    LinearLayout menu_1, menu_2, menu_3;

    int currentFragment; // 현재 프래그먼트 구별하기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
//TEST
        init();
    }

    public void init() {
        menu_1 = (LinearLayout) findViewById(R.id.menu_1);
        menu_2 = (LinearLayout) findViewById(R.id.menu_2);
        menu_3 = (LinearLayout) findViewById(R.id.menu_3);

        menu_1.setOnClickListener(this);
        menu_2.setOnClickListener(this);
        menu_3.setOnClickListener(this);

        /* 초기 프래그먼트는 캘린더 프래그먼트라 여기서 먼저 초기화해줍니다! */
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

        switch (v.getId()) {
            case R.id.menu_1:
                menu_1.setSelected(true);
                callFragment(CALENDAR_FRAGMENT);
                break;
            case R.id.menu_2:
                menu_2.setSelected(true);
                callFragment(BILL_FRAGMENT);
                break;
            case R.id.menu_3:
                menu_3.setSelected(true);
                callFragment(SETTINGS_FRAGMENT);
                break;
        }
    }

    private void callFragment(int fragmentNumber) {
        if (fragmentNumber != currentFragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

            /* 현재 프래그먼트와 다른 프래그먼트를 호출할 경우 replace 해줍니다 */
            switch (fragmentNumber) {
                case CALENDAR_FRAGMENT:
                    CalenderFragment calendarFragment = (CalenderFragment) new CalenderFragment();
                    transaction.replace(R.id.mainFragmantContainer, calendarFragment);
                    transaction.commit();
                    break;

                case BILL_FRAGMENT:
                    BillFragment billFragment = (BillFragment) new BillFragment();
                    transaction.replace(R.id.mainFragmantContainer, billFragment, "billFragment");
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
