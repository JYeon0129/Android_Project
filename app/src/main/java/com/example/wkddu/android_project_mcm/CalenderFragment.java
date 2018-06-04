package com.example.wkddu.android_project_mcm;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends Fragment {
    Context context;
    GridView calendarGridView;
    CalendarGridAdapter calendarGridAdapter;
    TextView calendarTopText;

    Calendar calendar;
    String[] day = {"월", "화", "수", "목", "금", "토", "일"};
    ArrayList<String> dayList;

    public CalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    public void init() {
        calendarGridView = (GridView) getActivity().findViewById(R.id.calendarGridView);
        calendarTopText = (TextView) getActivity().findViewById(R.id.calendarTopText);
        context = getActivity().getApplicationContext();
        calendar = Calendar.getInstance();
        dayList = new ArrayList<>();

        /* 오늘의 날짜 설정, 연/월/일로 따로 저장 */
        final Date current = new Date(System.currentTimeMillis());
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        calendarTopText.setText(curYearFormat.format(current) + "/" + curMonthFormat.format(current));

        /* 이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day) */
        calendar.set(Integer.parseInt(curYearFormat.format(current)),
            Integer.parseInt(curMonthFormat.format(current)) - 1, 1);

        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);

        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }

        setCalendarDate(calendar.get(Calendar.MONTH) + 1);

//        for (int i=0; i<7; i++) {
//            View v = getLayoutInflater().inflate(R.layout.calendar_day, null);
//            TextView calDayTitle = (TextView) v.findViewById(R.id.calDayTitle);
//            calDayTitle.setText(day[i]);
//            calendarGridView.addView(v);
//        }

//

        calendarGridAdapter = new CalendarGridAdapter(context, dayList);
        calendarGridView.setAdapter(calendarGridAdapter);
    }

    private void setCalendarDate(int month) {
        calendar.set(Calendar.MONTH, month - 1);

        for (int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }
    }
}
