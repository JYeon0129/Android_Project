package com.example.wkddu.android_project_mcm;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int CALENDAR_FRAGMENT = 1;
    final int GROUP_FRAGMENT = 2;
    final int BILL_FRAGMENT = 3;
    final int SETTINGS_FRAGMENT = 4;
    final int TODO_DETAIL = 5;
    final int TODO_CREATE = 6;
    final int smsReceiveRequest = 1;
    final int smsReadRequest = 2;
    LinearLayout menu_1, menu_2, menu_3, menu_4;

    int currentFragment; // 현재 프래그먼트 구별하기
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        init();
    }

    public void init() {
        askPermission(new String[]{Manifest.permission.RECEIVE_SMS}, smsReceiveRequest);
        askPermission(new String[]{Manifest.permission.READ_SMS}, smsReadRequest);

        Intent clipintent = new Intent(getApplicationContext(), ToClipboardListener.class);
        startService(clipintent);

        menu_1 = (LinearLayout) findViewById(R.id.menu_1);
        menu_2 = (LinearLayout) findViewById(R.id.menu_2);
        menu_3 = (LinearLayout) findViewById(R.id.menu_3);
        menu_4 = (LinearLayout) findViewById(R.id.menu_4);

        menu_1.setOnClickListener(this);
        menu_2.setOnClickListener(this);
        menu_3.setOnClickListener(this);
        menu_4.setOnClickListener(this);

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

            /* 현재 프래그먼트와 다른 프래그먼트를 호출할 경우 replace 해줍니다 */
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
    boolean checkAppPermission(String[] requestPermission){
        boolean[] requestResult= new boolean[requestPermission.length];
        for(int i=0; i< requestResult.length; i++){
            requestResult[i] = (ContextCompat.checkSelfPermission(this,
                    requestPermission[i]) == PackageManager.PERMISSION_GRANTED);
            if(!requestResult[i]){
                return false;
            }
        }return true;
    }

    void askPermission(String[] requestPermission, int REQ_PERMISSION) {
        ActivityCompat.requestPermissions(this,requestPermission,REQ_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case smsReceiveRequest :
                if(!checkAppPermission(permissions)) {// 퍼미션동의했을때할일
                    Toast.makeText(this, "권한이 거절됨", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case smsReadRequest :
                if(!checkAppPermission(permissions)) {// 퍼미션동의했을때할일
                    Toast.makeText(this, "권한이 거절됨", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        Log.d("onDestory()","브로드캐스트리시버 해제됨");
    }
}
