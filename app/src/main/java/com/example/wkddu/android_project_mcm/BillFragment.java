package com.example.wkddu.android_project_mcm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillFragment extends Fragment {
    TextView billBalanceText, billTypeText;
    Button billButton1, billButton2, billButton3;
    LinearLayout billType;
    View billTypeView;
    ListView billListView;
    Type type;


    public BillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    /*
     * billBalanceText에 현재 잔액 써주시고,
     * 각 버튼 이벤트만 달아주시면 됩니다!
     */

    public void init() {
        billBalanceText = (TextView) getActivity().findViewById(R.id.billBalanceText);
        billTypeText = (TextView) getActivity().findViewById(R.id.billTypeText);
        billTypeView = (View) getActivity().findViewById(R.id.billTypeView);
        billType = (LinearLayout) getActivity().findViewById(R.id.billType);
        billListView = (ListView) getActivity().findViewById(R.id.billListView);
        billButton1 = (Button) getActivity().findViewById(R.id.billButton1);
        billButton2 = (Button) getActivity().findViewById(R.id.billButton2);
        billButton3 = (Button) getActivity().findViewById(R.id.billButton3);

        ArrayList<Schedule> spends = new ArrayList<>();
        spends.add(new Schedule("술약속", 10000, 0,
                new Date(2018, 6, 6)));
        spends.add(new Schedule("닭발집 부숨", 22000, 0,
                new Date(2018, 6, 7)));
        spends.add(new Schedule("곱쏘*^^*", 14000, 0,
                new Date(2018, 6, 8)));
        spends.add(new Schedule("술약속", 3000, 0,
                new Date(2018, 6, 9)));

        TodoListAdapter todoListAdapter = new TodoListAdapter(getActivity(), R.layout.spend_list_row, spends);
        billListView.setAdapter(todoListAdapter);

        billType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                TypePickerFragment typePickerFragment = new TypePickerFragment();

                typePickerFragment.show(fragmentManager, "typePickerFragment");
            }
        });

        billButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        billButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        billButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setType(Type type) {
        this.type = type;
        billTypeText.setText(type.getTypeName());
        billTypeView.setBackgroundColor(getActivity().getResources().getColor(type.getTypeColor()));
    }

}
