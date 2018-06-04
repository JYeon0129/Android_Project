package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarListAdapter extends ArrayAdapter<Todo> {
    ArrayList<Todo> todoList;
    Context context;

    public CalendarListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Todo> objects) {
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
            v = vi.inflate(R.layout.calendar_todo_dot, null);
        }
        Todo todo = todoList.get(position);

        if (todo != null) {
            View calRowDot = (View) v.findViewById(R.id.calRowColor);

        }

        return v;
    }
}
