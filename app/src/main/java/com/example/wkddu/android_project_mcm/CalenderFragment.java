package com.example.wkddu.android_project_mcm;


import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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
    GridView calendarGridView, calendarDayTitle;
    CalendarGridAdapter calendarGridAdapter;
    CalendarDayAdapter calendarDayAdapter;
    SeekBar calendarSeekBar;
    TextView calendarMonthText, calendarYearText, calendarBalanceText;
    ImageButton calendarButton;

    Calendar calendar, beforeCalendar;
    String[] dayTitle = {"월", "화", "수", "목", "금", "토", "일"};
    ArrayList<String> dayList;
    SimpleDateFormat curYearFormat;
    SimpleDateFormat curMonthFormat;
    Date date;
    int year, month, beforeLastDay, budget, spend;
    /*
     * year, month : 현재 연, 월
     * beforeLastDay : 지난 달의 마지막 일
     * budget : 이번 달의 총 예산 (수입)
     * spend : 이번 달에 사용한 돈
     */

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
        calendarBalanceText = (TextView) getActivity().findViewById(R.id.calendarBalanceText);
        calendarButton = (ImageButton) getActivity().findViewById(R.id.calendarButton);
        calendarSeekBar = (SeekBar) getActivity().findViewById(R.id.calendarSeekBar);

        context = getActivity().getApplicationContext();
        calendar = Calendar.getInstance();
        beforeCalendar = Calendar.getInstance();
        dayList = new ArrayList<>();

        setCalendarSeekBar();

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                CreatePopupFragment calendarPopupFragment = new CreatePopupFragment();

                calendarPopupFragment.show(fragmentManager, "createPopupWindow");
            }
        });

        calendarSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        /* 오늘의 날짜 설정, 연/월/일로 따로 저장 */
        date = new Date(System.currentTimeMillis());
        curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        year = Integer.parseInt(curYearFormat.format(date));
        month = Integer.parseInt(curMonthFormat.format(date));

        setCalendarGridView();
    }

    /*
     * 이강민 : 잔액 보여주기
     * 해당 월의 총 예산 (수입), 사용한 돈을 DB에서 가져와서 잔액을 계산하고 화면에 뿌려주면 됩니다!
     * 여기서 화면 상단 바의 값을 설정합니다.
     */
    private void setCalendarSeekBar() {
        budget = 50000;
        spend = 10000;
        
        int percent = 0;
        if (budget != 0) {
            percent = (int)((spend / (float)budget) * 100);
        }

        calendarSeekBar.setProgress(percent);
        calendarBalanceText.setText("잔액 : " + (budget - spend) + "원");

        /*
         * 이강민 : Seekbar thumb 이미지 바꾸기
         * 목표로 했던 예산보다 실제 사용량이 많을 경우에는 우는 표정을,
         * 반대일 경우에는 웃는 표정을 보여줍니다.
         * 밑에 true가 들어간 자리가 웃는 표정이고 else가 우는 표정인데 저 조건문 내용만 바꾸면 됩니다!
         */

        if (true) {
            calendarSeekBar.setThumb(getActivity().getResources().getDrawable(R.drawable.custom_thumb_smile));
        } else {
            calendarSeekBar.setThumb(getActivity().getResources().getDrawable(R.drawable.custom_thumb_fail));
        }
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

        /* 캘린더 내부에 날짜와 할 일 채우기 */
        calendarGridAdapter = new CalendarGridAdapter(context, dayList, year, month-1);
        calendarGridView.setVerticalScrollBarEnabled(false);

        /* 캘린더 내부에 좌측, 우측 밀기 이벤트 적용 */
        calendarGridView.setOnTouchListener(new OnSwipeTouchListener(context) {
            private int CLICK_ACTION_THRESHOLD = 200;
            private float startX;
            private float startY;

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
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();

                        if (isAClick(startX, endX, startY, endY)) {
                            int p = ((GridView)v).pointToPosition((int)event.getX(), (int)event.getY());
                            long id = ((GridView)v).pointToRowId((int)event.getX(), (int)event.getY());

                            gridViewItemClick(((GridView)v), p, id);

                            return false;
                        }
                        break;
                }

                return this.gestureDetector.onTouchEvent(event);
            }

            private boolean isAClick(float startX, float endX, float startY, float endY) {
                float differenceX = Math.abs(startX - endX);
                float differenceY = Math.abs(startY - endY);
                return !(differenceX > CLICK_ACTION_THRESHOLD/* =5 */ || differenceY > CLICK_ACTION_THRESHOLD);
            }
        });

        calendarGridView.setAdapter(calendarGridAdapter);
    }

    /*
     * 캘린더의 칸 클릭 시 해당 리스트 팝업 창 호출하기
     * 지금 밑에는 ArrayList를 만들어서 랜덤으로 그냥 돌리고 있는데 다 지우고..
     * 저 밑에 setData 함수 두 번째 인자에 ArrayList<Todo>로 넘기면 됩니다!
     * 세 번째 인자는 해당 날짜의 지출 내역인데 ArrayList<Spend>로 넘기면 됩니다!
     * 오늘 연도는 year, 월은 month, 일은 dayList.get(position).substring(1)에 담겨 있으니
     * 이걸로 날짜 만들어서 쿼리 짜서 해당 날짜에 할 일들 가져오면 될 것 같아요!
     */

    public void gridViewItemClick(View v, int position, long id) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        CalendarPopupFragment calendarPopupFragment = new CalendarPopupFragment();

        DBHandler dbHandler = new DBHandler(context, null, null, 1);
        ArrayList<TABLE_SCH> sches = dbHandler
                .getSchSub(year+"", "0"+month+"", dayList.get(position).substring(1));

        if (sches.size() == 0) {
            /* 할 일도 없고 사용 내역도 없는 날짜의 경우 팝업이 뜨지 않습니담 */
        } else {
            calendarPopupFragment.setData(dayList.get(position), sches);
            calendarPopupFragment.show(fragmentManager, "calendarPopupWindow");
        }
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
