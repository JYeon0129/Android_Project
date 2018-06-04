package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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

        if (holder.dot != null) {
            switch (todoType) {
                case 0:
                    todoColor = R.color.bluegreen;
                    break;
                case 1:
                    todoColor = R.color.orange;
                    break;
            }

            GradientDrawable drawable = (GradientDrawable) holder.dot.getBackground();
            drawable.setColor(context.getResources().getColor(todoColor));
        }
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
