package com.example.wkddu.android_project_mcm;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ToClipboardListener extends Service implements ClipboardManager.OnPrimaryClipChangedListener{
    ClipboardManager clipboardManager;
    DBHandler dbHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(this);
        dbHandler = new DBHandler(this, DBHandler.DATABASE_NAME, null, 1);
        Log.v("clipboard ready","ready");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onPrimaryClipChanged() {
        boolean init_flag = false;
        boolean pay_flag = false;
        boolean title_flag = false;
        boolean day_flag = false;

        String month = "";
        String day = "";
        String usage = "";
        String payment = "";

        if (clipboardManager != null && clipboardManager.getPrimaryClip() != null) {
            ClipData data = clipboardManager.getPrimaryClip();
            Log.v("clipboard", "clip data - item : " + data.getItemAt(0).getText().toString());
            String e_str[] = data.getItemAt(0).getText().toString().split("\n");
            ArrayList<String> parse_result = new ArrayList<>();
            for(int i = 0; i < e_str.length; i++){
                String s_str[] = e_str[i].split(" ");
                for(int j = 0; j <s_str.length; j++) {
                    parse_result.add(s_str[j]);
                }
            }
            for(int i = 0; i<parse_result.size(); i++){
                init_flag = parse_result.get(i).toString().equals("-");
                String temp ="";
                int title_index = 2;
                String[] pay_date = null;
                if(init_flag){
                    pay_flag = parse_result.get(i+1).toString().equals("결제금액:");
                    title_flag = parse_result.get(i+1).toString().equals("구매처:");
                    day_flag = parse_result.get(i+1).toString().equals("일시:");

                    if(pay_flag){
                        temp = parse_result.get(i+2).toString().replace(",","");
                        temp = temp.replace("원","");
                        Log.v("split_payment",temp);
                        payment = temp;
                    }
                    else if(title_flag){
                        while(true) {
                            if(!parse_result.get(i+title_index).toString().equals("-")){
                                temp += parse_result.get(i+title_index).toString();
                                title_index++;
                            }
                            else break;
                        }
                        Log.v("split_title",temp);
                        usage = temp;
                    }
                    else if(day_flag){
                        temp = parse_result.get(i+2).toString();
                        Log.v("split_date",temp);
                        pay_date = temp.split("\\.");
                        month = pay_date[1];
                        day = pay_date[2];
                    }
                }
            }
            if(!payment.equals("") && !month.equals("") && !day.equals("") && !usage.equals("")){
                Clipboard clipboard = new Clipboard(month,day,usage,payment);
                dbHandler.addClipboard(clipboard);
            }
        }
        else {
            Log.e("Test", "No Manager or No Clip data");
        }
    }
}
