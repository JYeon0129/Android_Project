package com.example.wkddu.android_project_mcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class ToSMSReceiver extends BroadcastReceiver {
    String date_pattern = "^[0-9]{2}+\\/+[0-9]{2}$";
    String pay_pattern = "^[0-9]*$";
    String usage_pattern = "^[가-힣a-zA-Z0-9()]*$";
    String not_usage_pattern ="^[0-9]{3}.원*$";
    DBHandler dbHandler;
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            Log.d("onReceive()","문자가 수신되었습니다");
        }
        dbHandler = new DBHandler(context,DBHandler.DATABASE_NAME,null,1);
        // SMS 메시지를 파싱합니다.
        Bundle bundle = intent.getExtras();
        Object messages[] = (Object[])bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];

        for(int i = 0; i < messages.length; i++) {
            // PDU 포맷으로 되어 있는 메시지를 복원합니다.
            smsMessage[i] = SmsMessage.createFromPdu((byte[])messages[i]);
        }
        // SMS 수신 시간 확인
        Date curDate = new Date(smsMessage[0].getTimestampMillis());
        Log.d("문자 수신 시간", curDate.toString());

        // SMS 발신 번호 확인
        String origNumber = smsMessage[0].getOriginatingAddress();
        // SMS 메시지 확인
        String message = smsMessage[0].getMessageBody().toString();
        StringParser(message);
        Log.d("문자 내용", "발신자 : "+origNumber+", 내용 : " + message);
    }

    public void StringParser(String message){
        String msg[] = message.split("\n");
        ArrayList<String> msg_split = new ArrayList<>();
        boolean bank_flag = false;
        boolean date_flag = false;
        String month ="";
        String day = "";
        String payment = "";
        String usage = "";

        for(int i = 0; i < msg.length; i++){
            Log.v("sms_line",msg[i]);
            String temp[] = msg[i].split(" ");
            for(int j = 0; j < temp.length; j++){
                msg_split.add(temp[j]);
                Log.v("sms_space_split",temp[j]);
            }
        }
        for(int i = 0; i < msg_split.size(); i++){
            String str = msg_split.get(i);
            if(str.contains("신한체크") || str.contains("기업BC") || str.contains("국민")){
                bank_flag = true;
            }
            if(bank_flag){
                if(str.matches(date_pattern)){
                    month = str.split("\\/")[0];
                    day = str.split("\\/")[1];
                    Log.v("sms_date_split",month + "  " + day);
                    date_flag = true;
                }
            }
        }
        if(date_flag){
            for(int i = 0; i < msg_split.size(); i++){
                String str1 = msg_split.get(i).replace(",","");
                str1 = str1.replace("원","");
                if(str1.matches(pay_pattern)){
                    if(payment.equals("")){
                        payment = str1;
                    }
                    Log.v("sms_payment_split",payment);
                }
            }
            for(int i = 0; i <msg_split.size(); i++){
                String str2 = msg_split.get(i);
                if(str2.matches(usage_pattern) && !str2.contains("잔액") && !str2.matches(not_usage_pattern)&&!str2.contains("신한체크") && !str2.contains("기업BC")){
                    if(usage.equals("")){
                        usage = str2;
                    }
                    Log.v("sms_usage_split",usage);
                }
            }
        }
        Log.v("after_sms_payment_split",payment);
        Log.v("after_sms_usage_split",usage);
        if(!month.equals("")&& !day.equals("") && !payment.equals("") && !usage.equals("")){
            int pay = Integer.parseInt(payment);
            TABLE_SCH table_sch = new TABLE_SCH(month,day,pay,usage);
            dbHandler.addSch(table_sch);
            TABLE_SCH tt = dbHandler.getSch("2018",month,day,8,Integer.parseInt(payment),usage);
            Log.v("db_saved_sms",""+tt.getSpend());
            ArrayList<TABLE_SCH> sch = dbHandler.getSchAll();
            for(int i = 0; i< sch.size();i++){
                Log.v(""+i+" 번 째 spend ", ""+sch.get(i).getSpend());
            }
        }
    }
}
