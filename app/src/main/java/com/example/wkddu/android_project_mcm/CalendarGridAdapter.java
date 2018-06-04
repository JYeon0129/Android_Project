package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarGridAdapter extends BaseAdapter {
    List<String> list;

    LayoutInflater inflater;
    Context context;
    Calendar calendar;
    int gridViewHeight;

    public CalendarGridAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder = null;

        if (v == null) {
            v = inflater.inflate(R.layout.calendar_contents, parent, false);
            holder = new ViewHolder();

            holder.calDayText = v.findViewById(R.id.calDayText);
            holder.calBalanceBg = v.findViewById(R.id.calBalanceBg);
            holder.calBalanceText = v.findViewById(R.id.calBalanceText);
            holder.listView = v.findViewById(R.id.calListView);

            GridView layout = parent.findViewById(R.id.calendarGridView);
            gridViewHeight = layout.getHeight();

            v.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, gridViewHeight / 5));
            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }

        /* 캘린더 내용 채우기 */
        holder.calDayText.setText("" + getItem(position));
        holder.calBalanceText.setText((10000 + position * 100) + "");

        /* 어댑터로 할 일 리스트 세팅하기 */
        // arrayList로 한건 미리보기용입니다.
        ArrayList<Todo> todo = new ArrayList<>();
        if (position % 3 == 0) {
            todo.add(new Todo("술약속", 10000, 0));
        }
        if (position % 4 == 0) {
            todo.add(new Todo("밥약속", 5000, 1));
        }

        CalendarListAdapter calendarListAdapter = new CalendarListAdapter(context, R.layout.calendar_todo_row, todo);
        holder.listView.setAdapter(calendarListAdapter);

        //해당 날짜 텍스트 컬러,배경 변경
        calendar = Calendar.getInstance();

        //오늘 day 가져옴

        Integer today = calendar.get(Calendar.DAY_OF_MONTH);
        String sToday = String.valueOf(today);

        if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
            holder.calDayText.setTextColor(context.getResources().getColor(R.color.bluegreen));
        }

        return v;
    }
}

class ViewHolder {
    TextView calDayText;
    TextView calBalanceText;
    LinearLayout calBalanceBg;
    ListView listView;
}