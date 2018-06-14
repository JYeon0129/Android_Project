package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.graphics.Typeface.BOLD;

/*
 * 메인 캘린더의 각 칸의 내용을 채워주는 어댑터입니다
 * 여기서 해줘야 할 일은
 * 1. 해당 날짜에 얼마를 썼는지, 얼마를 써야 하는지 뿌려주고
 * 2. 내장 DB에서 각 날짜에 해당하는 할 일 리스트들 끌어와서 뿌려주면 됩니다!
 */

public class CalendarGridAdapter extends BaseAdapter {
    List<String> dayList;

    LayoutInflater inflater;
    Context context;
    Calendar calendar;
    int gridViewHeight, year, month;
    DBHandler dbHandler;

    public CalendarGridAdapter(Context context, List<String> list, int year, int month) {
        this.context = context;
        this.dayList = list;
        this.year = year;
        this.month = month;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dayList.size();
    }

    @Override
    public String getItem(int position) {
        return dayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        GridViewHolder holder = null;
        dbHandler = new DBHandler(context,DBHandler.DATABASE_NAME,null,1);
        if (v == null) {
            v = inflater.inflate(R.layout.calendar_contents, parent, false);
            holder = new GridViewHolder();

            holder.calDayText = v.findViewById(R.id.calDayText);
            holder.calBalanceBg = v.findViewById(R.id.calBalanceBg);
            holder.calBalanceText = v.findViewById(R.id.calBalanceText);
            holder.calBalancePlusMinus = v.findViewById(R.id.calBalancePlusMinus);
            holder.calRecyclerView = v.findViewById(R.id.calRecyclerView);
            holder.calAlphaView = v.findViewById(R.id.calAlphaView);

            GridView layout = parent.findViewById(R.id.calendarGridView);
            gridViewHeight = layout.getHeight();

            v.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, (gridViewHeight - 10) / 6));
            v.setTag(holder);

            try {
                /* 캘린더 내용 채우기 */
                if (getItem(position) != "") {
                    if(getItem(position).charAt(0) == 'c') {
                        TABLE_DAY table_day = new TABLE_DAY("" + year, "" + (month + 1), "" + getItem(position).substring(1), 10000, 20000);
                        dbHandler.addDay(table_day);
                    }
                    /* 주말은 빨간색으로 바꿔줍니다 */
                    if (position % 7 == 5 || position % 7 == 6) {
                        holder.calDayText.setTextColor(context.getResources().getColor(R.color.red));
                    }

                    /* 오늘의 날짜는 폰트 색깔을 바꿔줍니다 */
                    calendar = Calendar.getInstance();
                    Integer today = calendar.get(Calendar.DAY_OF_MONTH);
                    Integer currentMonth = calendar.get(Calendar.MONTH);
                    String sToday = String.valueOf(today);

                    if (sToday.equals(getItem(position).substring(1)) && month == currentMonth) {
                        holder.calDayText.setTextColor(context.getResources().getColor(R.color.bluegreen));
                        holder.calDayText.setTypeface(null, BOLD);
                    }

                    /*
                    * 이강민 : 배경색 설정하기
                    * 1. 회색 (lightgray) : 예산일 경우 (아직 날짜가 지나지 않음)
                    * 2. 빨간색 (danger) : 날짜가 지났고, 사용 가능 금액(예산)을 넘었을 경우
                    * 3. 파란색 (save) : 날짜가 지났고, 실제로 쓴 돈이 사용 가능 금액(예산)을 넘지 않았을 경우
                    * 미리보기로 대충 설정해뒀습니다! 이거 참고해서 해주시면 될 것 같아요.
                    */

                    if (getItem(position).charAt(0) == 'a' ||
                            currentMonth < month ||
                            Integer.parseInt(getItem(position).substring(1)) > today) { // 1번 케이스
                        holder.calBalanceBg.setBackgroundColor(context.getResources().getColor(R.color.morelightgray));
                        holder.calBalanceText.setTextColor(context.getResources().getColor(R.color.darkgray));
                    } else {
                        int day_limit = dbHandler.getDay(""+year,""+(month+1),""+today).getDay_limit();
                        int day_spend = dbHandler.getDay(""+year,""+(month+1),""+today).getDay_spend();
                        if (day_limit < day_spend) { // 2번 케이스
                            holder.calBalanceBg.setBackgroundColor(context.getResources().getColor(R.color.danger));
                        } else { // 3번 케이스
                            holder.calBalanceBg.setBackgroundColor(context.getResources().getColor(R.color.save));
                        }
                    }

                    /* 쓸 수 있는 금액 or 사용한 금액 표기하기 */
                    holder.calDayText.setText(getItem(position).substring(1));

                    String b = dbHandler.getDay(year+"", (month+1)+"", getItem(position).substring(1)).getDay();
                    int a = dbHandler.getDay(year+"", (month+1)+"", getItem(position).substring(1)).getDay_limit();
                    if(dbHandler.getDay(year+"", (month+1)+"", getItem(position).substring(1)).getDay_limit() != 0){
                        Log.v("day",b + " " + a);
                        holder.calBalanceText.setText(dbHandler.getDay(year+"", (month+1)+"", getItem(position).substring(1)).getDay());
                    }
                    if (getItem(position).charAt(0) == 'a' || getItem(position).charAt(0) == 'b') {
                        holder.calAlphaView.setAlpha(0.6f);
                    }

                    /*
                     * 어댑터로 할 일 리스트 세팅하기
                     * 해당 날짜에 해야 할 일들을 내장 DB에서 끌고 와서
                     * ArrayList<Todo>로 만들어서 밑에 있는 calendarDotAdapter 생성자의 두 번째 인자로 주면 됩니다!
                     * ArrayList로 한건 미리보기용입니다. 이거 지우고 하시면 됩니다!
                     */

                    ArrayList<TABLE_SCH> schedules = dbHandler.getSchSub(year+"", (month+1)+"", getItem(position).substring(1));
                    Log.v("count : ", getItem(position).substring(1)+" : " + schedules.size());

                    if (schedules == null) {
                        schedules = new ArrayList<>();
                    }

                    RecyclerView.LayoutManager layoutManager;
                    layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    holder.calRecyclerView.setLayoutManager(layoutManager);

                    CalendarDotAdapter calendarDotAdapter = new CalendarDotAdapter(context, schedules);

                    if (holder.calRecyclerView != null) {
                        holder.calRecyclerView.setAdapter(calendarDotAdapter);
                    }

                }
            } catch (Exception e) {
                Log.v("calRecycle","ddddddddddddddddddddddddd");
            }

        } else {
            holder = (GridViewHolder) v.getTag();
        }

        return v;
    }
}

class GridViewHolder {
    TextView calDayText;
    TextView calBalanceText;
    TextView calBalancePlusMinus;
    LinearLayout calBalanceBg;
    RecyclerView calRecyclerView;
    View calAlphaView;
}