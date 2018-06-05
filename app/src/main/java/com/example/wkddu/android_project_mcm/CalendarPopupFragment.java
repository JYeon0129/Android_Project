package com.example.wkddu.android_project_mcm;


import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarPopupFragment extends DialogFragment {
    TextView calendarPopupDayText;
    String day;
    ArrayList<Todo> todoList;

    public CalendarPopupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.calendar_popup_window, null);
        getDialog().setCanceledOnTouchOutside(true);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    public void init() {
        calendarPopupDayText = (TextView) getActivity().findViewById(R.id.calendarPopupDayText);

        if (calendarPopupDayText != null) {
            calendarPopupDayText.setText(day+"일");
        }
    }

    /*이 프래그먼트가 받아야 하는 정보는
     * 1. String day : 무슨 요일인지
     * 2. ArrayList<Todo> todoList : 해당 요일에 해야할 일들
     */

    public void setData(String day, ArrayList<Todo> todoList) {
        this.day = day;
        this.todoList = todoList;
    }

    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        window.setBackgroundDrawableResource(R.drawable.set_border_radius);
        window.setLayout((int) (width * 0.7), (int) (height * 0.65));
        window.setGravity(Gravity.CENTER);
    }

}
