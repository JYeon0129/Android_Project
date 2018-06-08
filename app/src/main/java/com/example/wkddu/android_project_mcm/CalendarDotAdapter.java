package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/*
 * 캘린더의 각 칸에 일정을 점으로 찍어주는 어댑터입니다
 * 여기서 해줘야 할 일은
 * 해당 날짜에 있는 일정을 불러와서 타입에 따라 점만 찍어주면 됩니다!
 */

public class CalendarDotAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<Todo> todoList;
    Context context;

    public CalendarDotAdapter(Context context, ArrayList<Todo> Data) {
        this.context = context;
        this.todoList = Data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.calendar_todo_dot, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        int todoType = todoList.get(position).getType();
        int todoColor = 0;

        Helpers helpers = new Helpers();
        todoColor = helpers.setTypeColor(todoType);

        GradientDrawable drawable = (GradientDrawable) holder.dot.getBackground();
        drawable.setColor(context.getResources().getColor(todoColor));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    View dot;

    public MyViewHolder(View v) {
        super(v);
        dot = (View) v.findViewById(R.id.calRowDot);
    }
}
