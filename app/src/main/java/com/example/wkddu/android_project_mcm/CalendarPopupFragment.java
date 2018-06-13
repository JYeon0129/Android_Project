package com.example.wkddu.android_project_mcm;


import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarPopupFragment extends DialogFragment {
    TextView calPopupDayText;
    ListView calPopupTodoListView;
    ListView calPopupSpendListView;
    String day;
    ArrayList<TABLE_SCH> sches;

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
        calPopupDayText = (TextView) getDialog().findViewById(R.id.calPopupDayText);
        calPopupTodoListView= (ListView) getDialog().findViewById(R.id.calPopupTodoListView);
        calPopupSpendListView= (ListView) getDialog().findViewById(R.id.calPopupSpendListView);

        calPopupDayText.setText(day.substring(1) + "일");

//        CalendarListAdapter calendarTodoListAdapter = new CalendarListAdapter(
//                getContext(), R.layout.calendar_todo_row, sches);
//
//        calPopupTodoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
//
//                TodoDetailFragment todoDetailFragment = (TodoDetailFragment) new TodoDetailFragment();
//                todoDetailFragment.setData(sches.get(position));
//                transaction.add(R.id.mainFragmantContainer, todoDetailFragment);
//                transaction.commit();
//
//                ((MainActivity) getActivity()).currentFragment = ((MainActivity) getActivity()).TODO_DETAIL;
//                getDialog().dismiss();
//            }
//        });
//
//        calPopupTodoListView.setAdapter(calendarTodoListAdapter);

        CalendarListAdapter calendarSpendListAdapter = new CalendarListAdapter(
                getContext(), R.layout.calendar_todo_row, sches);

        calPopupSpendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);

                SpendDetailFragment spendDetailFragment = (SpendDetailFragment) new SpendDetailFragment();
                spendDetailFragment.setData(sches.get(position));
                transaction.add(R.id.mainFragmantContainer, spendDetailFragment);
                transaction.commit();

                ((MainActivity) getActivity()).currentFragment = ((MainActivity) getActivity()).TODO_DETAIL;
                getDialog().dismiss();
            }
        });

        calPopupSpendListView.setAdapter(calendarSpendListAdapter);

    }

    /*이 프래그먼트가 받아야 하는 정보는
     * 1. String day : 무슨 요일인지
     * 2. ArrayList<Todo> todoList : 해당 요일에 해야할 일들
     */

    public void setData(String day, ArrayList<TABLE_SCH> sches) {
        this.day = day;
        this.sches = sches;
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
