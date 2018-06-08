package com.example.wkddu.android_project_mcm;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpendFormFragment extends Fragment {
    Button spendFormCancle, spendFormSave, spendFormAddBill;
    TextView spendFormDateText, spendFormTypeText;
    EditText spendFormTitleEdit, spendFormCostEdit;
    View spendFormTypeView;
    Calendar selected;

    Context context;

    public SpendFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spend_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    public void init() {
        spendFormDateText = (TextView) getActivity().findViewById(R.id.spendFormDateText);
        spendFormTitleEdit = (EditText) getActivity().findViewById(R.id.spendFormTitleEdit);
        spendFormTypeText = (TextView) getActivity().findViewById(R.id.spendFormTypeText);
        spendFormCostEdit = (EditText) getActivity().findViewById(R.id.spendFormCostEdit);
        spendFormCancle = (Button) getActivity().findViewById(R.id.spendFormCancle);
        spendFormSave = (Button) getActivity().findViewById(R.id.spendFormSave);
        spendFormAddBill = (Button) getActivity().findViewById(R.id.spendFormAddBill);
        spendFormTypeView = (View) getActivity().findViewById(R.id.spendFormTypeView);

        context = getActivity().getApplicationContext();

        Calendar calendar = Calendar.getInstance();
        spendFormDateText.setText(calendar.get(Calendar.YEAR) + "년 " +
                (calendar.get(Calendar.MONTH) + 1) + "월 " + calendar.get(Calendar.DATE) + "일");

        spendFormDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar;

                if (selected != null) {
                    calendar = selected;
                } else {
                    calendar = Calendar.getInstance();
                }

                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        listener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DATE));

                dialog.show();
            }
        });

        spendFormCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.mainFragmantContainer);
                transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
                transaction.remove(currentFragment).commit();

                ((MainActivity) getActivity()).currentFragment = ((MainActivity) getActivity()).CALENDAR_FRAGMENT;
            }
        });

        spendFormSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*
         * 심다슬 : spendFormAddBill 클릭 시 영수증 추가되게 하면 됨당!
         */

        spendFormAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (selected == null) selected = Calendar.getInstance();
            selected.set(year, monthOfYear, dayOfMonth);
            spendFormDateText.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth +"일");
        }
    };
}
