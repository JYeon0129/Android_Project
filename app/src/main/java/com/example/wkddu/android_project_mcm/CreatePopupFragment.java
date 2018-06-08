package com.example.wkddu.android_project_mcm;


import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePopupFragment extends DialogFragment {
    LinearLayout createBtn1, createBtn2;

    public CreatePopupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_popup, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    public void init() {
        createBtn1 = (LinearLayout) getDialog().findViewById(R.id.createBtn1);
        createBtn2 = (LinearLayout) getDialog().findViewById(R.id.createBtn2);

        createBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);

                TodoFormFragment todoFormFragment = (TodoFormFragment) new TodoFormFragment();
                transaction.add(R.id.mainFragmantContainer, todoFormFragment, "todoFormFragment");
                transaction.commit();

                ((MainActivity) getActivity()).currentFragment = ((MainActivity) getActivity()).TODO_CREATE;

                getDialog().dismiss();
            }
        });

        createBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);

                SpendFormFragment spendFormFragment = (SpendFormFragment) new SpendFormFragment();
                transaction.add(R.id.mainFragmantContainer, spendFormFragment, "spendFormFragment");
                transaction.commit();

                ((MainActivity) getActivity()).currentFragment = ((MainActivity) getActivity()).TODO_CREATE;

                getDialog().dismiss();
            }
        });
    }

    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        window.setBackgroundDrawableResource(R.drawable.set_border_radius_2);
        window.setLayout((int) (width * 0.7), (int) (height * 0.2));
        window.setGravity(Gravity.CENTER);
    }
}
