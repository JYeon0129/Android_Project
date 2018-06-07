package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.graphics.Typeface.BOLD;

public class CalendarDayAdapter extends BaseAdapter {
    String[] dayList;

    LayoutInflater inflater;
    Context context;

    public CalendarDayAdapter(Context context, String[] list) {
        this.context = context;
        this.dayList = list;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return dayList.length; }

    @Override
    public String getItem(int position) {
        return dayList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        DayViewHolder holder = null;

        if (v == null) {
            v = inflater.inflate(R.layout.calendar_day, parent, false);
            holder = new DayViewHolder();

            holder.calDayTitle = v.findViewById(R.id.calDayTitle);

            v.setTag(holder);

        } else {
            holder = (DayViewHolder) v.getTag();
        }

        /* 캘린더 내용 채우기 */
        holder.calDayTitle.setText(dayList[position]);

        if (position == 5 || position == 6) {
            holder.calDayTitle.setTextColor(context.getResources().getColor(R.color.red));
        }

        return v;
    }
}

class DayViewHolder {
    TextView calDayTitle;
}