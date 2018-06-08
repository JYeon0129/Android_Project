package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarTodoListAdapter extends ArrayAdapter<Todo> {
    ArrayList<Todo> todoList;
    Context context;

    public CalendarTodoListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Todo> objects) {
        super(context, resource, objects);
        this.todoList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        View v = view;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_todo_row, null);
        }

        Todo todo = todoList.get(position);

        if (todo != null) {
            View calRowColor = (View) v.findViewById(R.id.calRowColor);
            TextView calRowTitleText = (TextView) v.findViewById(R.id.calRowTitleText);
            TextView calRowCostText = (TextView) v.findViewById(R.id.calRowCostText);

            int todoType = todo.getType();
            int todoColor = 0;

            Helpers helpers = new Helpers();
            todoColor = helpers.setTypeColor(todoType);

            calRowColor.setBackgroundColor(context.getResources().getColor(todoColor));

            calRowTitleText.setText(todo.getTodoName());
            calRowCostText.setText(todo.getCost()+"원");
        }

        return v;
    }
}
