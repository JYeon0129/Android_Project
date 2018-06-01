package com.example.wkddu.android_project_mcm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int CALENDAR_FRAGMENT = 1;
    final int BLANK_FRAGMENT = 2;

    Button menu_1, menu_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        menu_1 = (Button) findViewById(R.id.menu_1);
        menu_2 = (Button) findViewById(R.id.menu_2);

        menu_1.setOnClickListener(this);
        menu_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_1:
                callFragment(CALENDAR_FRAGMENT);
                break;
            case R.id.menu_2:
                callFragment(BLANK_FRAGMENT);
                break;
        }
    }

    private void callFragment(int fragmentNumber) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragmentNumber) {
            case CALENDAR_FRAGMENT:
                CalenderFragment calendarFragment = (CalenderFragment) new CalenderFragment();
                transaction.replace(R.id.mainFragmantContainer, calendarFragment);
                transaction.commit();
                break;
            case BLANK_FRAGMENT:
                BlankFragment blankFragment = (BlankFragment) new BlankFragment();
                transaction.replace(R.id.mainFragmantContainer, blankFragment);
                transaction.commit();
                break;
        }
    }
}
