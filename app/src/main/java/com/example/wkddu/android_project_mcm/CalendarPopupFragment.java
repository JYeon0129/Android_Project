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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarPopupFragment extends DialogFragment {
    TextView calPopupDayText;
    ListView calPopupListView;
    ImageButton calPopupButton;
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
        calPopupDayText = (TextView) getDialog().findViewById(R.id.calPopupDayText);
        calPopupListView = (ListView) getDialog().findViewById(R.id.calPopupListView);
        calPopupButton = (ImageButton) getDialog().findViewById(R.id.calPopupButton);

        calPopupDayText.setText(day.substring(1) + "일");

        calPopupButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

                TodoFormFragment todoCreateFragment = (TodoFormFragment) new TodoFormFragment();
                transaction.replace(R.id.mainFragmantContainer, todoCreateFragment);
                transaction.commit();

                ((MainActivity) getActivity()).currentFragment = ((MainActivity) getActivity()).TODO_CREATE;

                getDialog().dismiss();
            }
        });

        CalendarListAdapter calendarListAdapter = new CalendarListAdapter(
                getContext(), R.layout.calendar_todo_row, todoList);

        calPopupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);

                TodoDetailFragment todoDetailFragment = (TodoDetailFragment) new TodoDetailFragment();
                todoDetailFragment.setData(todoList.get(position));
                transaction.add(R.id.mainFragmantContainer, todoDetailFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

                ((MainActivity) getActivity()).currentFragment = ((MainActivity) getActivity()).TODO_DETAIL;

                getDialog().dismiss();
            }
        });

        calPopupListView.setAdapter(calendarListAdapter);

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
