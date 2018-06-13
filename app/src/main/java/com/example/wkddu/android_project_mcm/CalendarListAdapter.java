package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarListAdapter extends ArrayAdapter<TABLE_SCH> {
    ArrayList<TABLE_SCH> sches;
    Context context;

    public CalendarListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<TABLE_SCH> objects) {
        super(context, resource, objects);
        this.sches = objects;
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

        TABLE_SCH schedule = sches.get(position);

        if (schedule != null) {
            View calRowColor = (View) v.findViewById(R.id.calRowColor);
            TextView calRowTitleText = (TextView) v.findViewById(R.id.calRowTitleText);
            TextView calRowCostText = (TextView) v.findViewById(R.id.calRowCostText);

            int todoType = schedule.getCategory();

            Helpers helpers = new Helpers();
            int todoColor = helpers.returnType(todoType).getTypeColor();

            calRowColor.setBackgroundColor(context.getResources().getColor(todoColor));

            calRowTitleText.setText(schedule.getUsage());
            calRowCostText.setText(schedule.getSpend()+"원");
        }

        return v;
    }
}
