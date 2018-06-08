package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarSpendListAdapter extends ArrayAdapter<Spend> {
    ArrayList<Spend> spendList;
    Context context;

    public CalendarSpendListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Spend> objects) {
        super(context, resource, objects);
        this.spendList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        View v = view;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_todo_row, null);
        }

        Spend spend = spendList.get(position);

        if (spend != null) {
            View calRowColor = (View) v.findViewById(R.id.calRowColor);
            TextView calRowTitleText = (TextView) v.findViewById(R.id.calRowTitleText);
            TextView calRowCostText = (TextView) v.findViewById(R.id.calRowCostText);

            int todoType = spend.getType();
            int todoColor = 0;

            Helpers helpers = new Helpers();
            todoColor = helpers.setTypeColor(todoType);

            calRowColor.setBackgroundColor(context.getResources().getColor(todoColor));

            calRowTitleText.setText(spend.getTitle());
            calRowCostText.setText(spend.getCost()+"원");
        }

        return v;
    }
}
