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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.graphics.Typeface.BOLD;

public class CalendarGridAdapter extends BaseAdapter {
    List<String> dayList;

    LayoutInflater inflater;
    Context context;
    Calendar calendar;
    int gridViewHeight;

    public CalendarGridAdapter(Context context, List<String> list) {
        this.context = context;
        this.dayList = list;
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
                    /* 주말은 빨간색으로 바꿔줍니다 */
                    if (position % 7 == 5 || position % 7 == 6) {
                        holder.calDayText.setTextColor(context.getResources().getColor(R.color.red));
                    }

                    /* 오늘의 날짜는 폰트 색깔을 바꿔줍니다 */
                    calendar = Calendar.getInstance();
                    Integer today = calendar.get(Calendar.DAY_OF_MONTH);
                    String sToday = String.valueOf(today);

                    if (sToday.equals(getItem(position))) {
                        holder.calDayText.setTextColor(context.getResources().getColor(R.color.bluegreen));
                        holder.calDayText.setTypeface(null, BOLD);
                    }

                    /*
                    배경색 설정하기
                    * 1. 회색 (lightgray) : 예산일 경우 (아직 날짜가 지나지 않음)
                    * 2. 빨간색 (danger) : 사용 가능 금액을 넘었을 경우
                    * 3. 파란색 (save) : 사용 가능 금액을 넘지 않았을 경우
                    */

                    // 미리보기로 대충 설정해뒀습니다!

                    if (Integer.parseInt(getItem(position).substring(1)) > today) { // 1
                        holder.calBalanceBg.setBackgroundColor(context.getResources().getColor(R.color.morelightgray));
                        holder.calBalanceText.setTextColor(context.getResources().getColor(R.color.darkgray));
                    } else {
                        if (position % 3 == 0) { // 2
                            holder.calBalancePlusMinus.setText("-");
                            holder.calBalanceBg.setBackgroundColor(context.getResources().getColor(R.color.danger));
                        } else { // 3
                            holder.calBalancePlusMinus.setText("+");
                            holder.calBalanceBg.setBackgroundColor(context.getResources().getColor(R.color.save));
                        }
                    }

                    /* 어댑터로 할 일 리스트 세팅하기 */
                    // arrayList로 한건 미리보기용입니다.
                    ArrayList<Todo> todo = new ArrayList<>();
                    if (position % 2 == 0) {
                        todo.add(new Todo("술약속", 10000, 0));
                    }
                    if (position % 3 == 0) {
                        todo.add(new Todo("밥약속", 5000, 1));
                    }

                    /* 쓸 수 있는 금액 or 사용한 금액 표기하기 */
                    holder.calDayText.setText(getItem(position).substring(1));
                    holder.calBalanceText.setText((10000 + position * 100) + "");

                    if (getItem(position).charAt(0) == 'a' || getItem(position).charAt(0) == 'b') {
                        holder.calAlphaView.setAlpha(0.6f);
                    }

                    RecyclerView.LayoutManager layoutManager;
                    layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    holder.calRecyclerView.setLayoutManager(layoutManager);

                    CalendarDotAdapter calendarDotAdapter = new CalendarDotAdapter(context, todo);

                    if (holder.calRecyclerView != null) {
                        holder.calRecyclerView.setAdapter(calendarDotAdapter);
                    }
                }
            } catch (Exception e) {

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