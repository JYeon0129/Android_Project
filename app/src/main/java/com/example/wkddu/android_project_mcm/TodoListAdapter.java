package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TodoListAdapter extends ArrayAdapter<Spend>{
    ArrayList<Spend> spends;
    Context context;
    LayoutInflater inflater;

    public TodoListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Spend> objects) {
        super(context, resource, objects);

        this.spends = objects;
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

            Spend spend = getItem(position);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
            spendDateText.setText(simpleDateFormat.format(spend.getDate()));
            spendTitleText.setText(spend.getTitle());
            spendCostText.setText(spend.getCost()+"");
        }

        return v;
    }
}
