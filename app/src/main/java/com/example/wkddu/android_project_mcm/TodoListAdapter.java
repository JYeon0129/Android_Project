package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TodoListAdapter extends ArrayAdapter<TABLE_SCH>{
    ArrayList<TABLE_SCH> schedules;
    Context context;
    LayoutInflater inflater;

    public TodoListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TABLE_SCH> objects) {
        super(context, resource, objects);

        this.schedules = objects;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        if (v == null) {
            v = inflater.inflate(R.layout.spend_list_row, parent, false);

            TextView spendDateText = (TextView) v.findViewById(R.id.spendDateText);
            TextView spendTitleText = (TextView) v.findViewById(R.id.spendTitleText);
            TextView spendTypeText = (TextView) v.findViewById(R.id.spendTypeText);
            TextView spendCostText = (TextView) v.findViewById(R.id.spendCostText);
            View spendTypeView = (View) v.findViewById(R.id.spendTypeView);

            TABLE_SCH spend = getItem(position);
            String sch_date = spend.getYear() + "." + spend.getMonth() + "." + spend.getDay();
            spendDateText.setText(sch_date);
            spendTitleText.setText(spend.getUsage());
            spendCostText.setText(spend.getSpend()+"");
        }

        return v;
    }
}
