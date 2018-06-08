package com.example.wkddu.android_project_mcm;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TypePickerFragment extends DialogFragment {
    int[] types = {0, 1, 6, 2, 4, 3, 5, 7, 8};
    ListView typePickerListView;
    Context context;

    public TypePickerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type_picker, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    public void init() {
        context = getActivity();

        typePickerListView = getDialog().findViewById(R.id.typePickerListView);
        TypePickerArrayAdapter typePickerArrayAdapter = new TypePickerArrayAdapter(context, types);
        typePickerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoFormFragment todoFormFragment = (TodoFormFragment) getActivity()
                        .getSupportFragmentManager().findFragmentByTag("todoFormFragment");
                SpendFormFragment spendFormFragment = (SpendFormFragment) getActivity()
                        .getSupportFragmentManager().findFragmentByTag("spendFormFragment");
                BillFragment billFragment = (BillFragment) getActivity()
                        .getSupportFragmentManager().findFragmentByTag("billFragment");
                Helpers helpers = new Helpers();

                if (todoFormFragment != null) {
                    todoFormFragment.setType(helpers.returnType(types[position]));
                }

                if (spendFormFragment != null) {
                    spendFormFragment.setType(helpers.returnType(types[position]));
                }

                if (billFragment != null) {
                    billFragment.setType(helpers.returnType(types[position]));
                }

                getDialog().dismiss();
            }
        });
        typePickerListView.setAdapter(typePickerArrayAdapter);
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
        window.setLayout((int) (width * 0.6), (int) (height * 0.55));
        window.setGravity(Gravity.CENTER);
    }

}
