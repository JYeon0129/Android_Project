package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TypePickerArrayAdapter extends BaseAdapter {
    int[] types;

    LayoutInflater inflater;
    Context context;

    public TypePickerArrayAdapter(Context context, int[] list) {
        this.context = context;
        this.types = list;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return types.length; }

    @Override
    public String getItem(int position) {
        return types[position]+"";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        TypePickerHolder holder = null;

        if (v == null) {
            v = inflater.inflate(R.layout.type_picker_row, parent, false);
            holder = new TypePickerHolder();

            holder.typePickerText = v.findViewById(R.id.typePickerText);
            holder.typePickerView = v.findViewById(R.id.typePickerView);

            v.setTag(holder);

        } else {
            holder = (TypePickerHolder) v.getTag();
        }

        Helpers helpers = new Helpers();
        Type type = helpers.returnType(types[position]);
        holder.typePickerText.setText(type.getTypeName());
        holder.typePickerView.setBackgroundColor(context.getResources().getColor(type.getTypeColor()));

        return v;
    }
}

class TypePickerHolder {
    View typePickerView;
    TextView typePickerText;
}
