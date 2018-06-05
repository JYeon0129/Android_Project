package com.example.wkddu.android_project_mcm;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    GridView calendarGridView, calendarDayTitle;
    CalendarGridAdapter calendarGridAdapter;
    CalendarDayAdapter calendarDayAdapter;
    TextView calendarMonthText;
    TextView calendarYearText;

    Calendar calendar, beforeCalendar;
    String[] dayTitle = {"월", "화", "수", "목", "금", "토", "일"};
    SimpleDateFormat curYearFormat;
    SimpleDateFormat curMonthFormat;
    SimpleDateFormat curDayFormat;
    Date date;
    int year, month, day, beforeLastDay;

    ArrayList<String> dayList;

    public CalenderFragment() {}

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
        calendarDayTitle = (GridView) getActivity().findViewById(R.id.calendarDayTitle);
        calendarMonthText = (TextView) getActivity().findViewById(R.id.calendarMonthText);
        calendarYearText = (TextView) getActivity().findViewById(R.id.calendarYearText);

        context = getActivity().getApplicationContext();
        calendar = Calendar.getInstance();
        beforeCalendar = Calendar.getInstance();
        dayList = new ArrayList<>();

        /* 오늘의 날짜 설정, 연/월/일로 따로 저장 */
        date = new Date(System.currentTimeMillis());
        curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        year = Integer.parseInt(curYearFormat.format(date));
        month = Integer.parseInt(curMonthFormat.format(date));
        day = Integer.parseInt(curDayFormat.format(date));

        setCalendarGridView();
    }

    private void setCalendarGridView() {
        /* 먼저 달력 내부를 초기화해줍니다. */
        dayList = new ArrayList<>();
        calendarGridView.setAdapter(null);

        /* 상단 월/일 부분을 세팅해줍니다.*/
        calendarMonthText.setText(month + "월");
        calendarYearText.setText(year + "년");

        /* 이번달 1일이 무슨요일인지... set(Year,Month,Day) */
        calendar.set(year, month - 1, 1);
        beforeCalendar.set(year, month - 2, 1);
        beforeLastDay = calendar.getActualMaximum(beforeCalendar.DAY_OF_MONTH);

        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);

        for (int i = dayNum - 2, j = beforeLastDay; i >= 0; i--) {
            dayList.add("b" + (beforeLastDay - i)); // before
        }

        calendar.set(Calendar.MONTH, month - 1);

        for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("c" + i); // current
        }

        int size = dayList.size();
        for (int i = 1; i <= 42 - size; i++) {
            dayList.add("a" + i); // after
        }

        /* 캘린더 상단 날짜 부분 내용 채우기 */
        calendarDayAdapter = new CalendarDayAdapter(context, dayTitle);
        calendarDayTitle.setAdapter(calendarDayAdapter);

        calendarGridAdapter = new CalendarGridAdapter(context, dayList);
        calendarGridView.setVerticalScrollBarEnabled(false);

        calendarGridView.setOnTouchListener(new OnSwipeTouchListener(context) {
            public void onSwipeRight() {
                if (month == 1) {
                    year --;
                    month = 12;
                } else {
                    month --;
                }

                setCalendarGridView();
                SlideAnimationUtil.slideInFromLeft(context, calendarGridView);
            }

            public void onSwipeLeft() {
                if (month == 12) {
                    year ++;
                    month = 1;
                } else {
                    month ++;
                }

                setCalendarGridView();
                SlideAnimationUtil.slideInFromRight(context, calendarGridView);
            }

            public boolean onTouch(View v, MotionEvent event) {
                return this.gestureDetector.onTouchEvent(event);
            }
        });

        calendarGridView.setAdapter(calendarGridAdapter);
    }
}

class OnSwipeTouchListener implements View.OnTouchListener {
    final GestureDetector gestureDetector;
    public OnSwipeTouchListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = true;
                }

                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    result = false;
                }

                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {}
    public void onSwipeLeft() {}
}

class SlideAnimationUtil {
    public static void slideInFromLeft(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_left);
    }

    public static void slideInFromRight(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_right);
    }

    private static void runSimpleAnimation(Context context, View view, int animationId) {
        view.startAnimation(AnimationUtils.loadAnimation(
                context, animationId
        ));
    }
}
