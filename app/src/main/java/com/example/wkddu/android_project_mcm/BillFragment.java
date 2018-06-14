package com.example.wkddu.android_project_mcm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class    BillFragment extends Fragment {
    TextView billBalanceText, billTypeText;
    Button billButton1, billButton2, billButton3;
    LinearLayout billType;
    View billTypeView;
    ListView billListView;
    Type type;
    DBHandler dbHandler;
    ArrayList<TABLE_SCH> schedule;
    ArrayList<TABLE_SCH> list_sch;
    TodoListAdapter todoListAdapter;

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
        dbHandler = new DBHandler(getContext(),DBHandler.DATABASE_NAME,null, 1);
        billTypeText = (TextView) getActivity().findViewById(R.id.billTypeText);
        billTypeView = (View) getActivity().findViewById(R.id.billTypeView);
        billType = (LinearLayout) getActivity().findViewById(R.id.billType);
        billListView = (ListView) getActivity().findViewById(R.id.billListView);
        billButton1 = (Button) getActivity().findViewById(R.id.billButton1);
        billButton2 = (Button) getActivity().findViewById(R.id.billButton2);
        billButton3 = (Button) getActivity().findViewById(R.id.billButton3);

        type = new Type(1,"식비",R.color.bluegreen);
        selectList(type.getTypeNum(),14);

        todoListAdapter = new TodoListAdapter(getActivity(), R.layout.spend_list_row, list_sch);
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
                Log.v("click1","click btn1");
                selectList(type.getTypeNum(),15);
            }
        });

        billButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("click2","click btn2");
                selectList(type.getTypeNum(),31);
            }
        });

        billButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("click3","click btn3");
                selectList(type.getTypeNum(),91);
            }
        });
    }

    public void setType(Type type) {
        this.type = type;
        billTypeText.setText(type.getTypeName());
        billTypeView.setBackgroundColor(getActivity().getResources().getColor(type.getTypeColor()));
    }
    public void selectList(int select_type, int period){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date TodayDate = new Date();

        schedule = new ArrayList<>();
        list_sch = new ArrayList<>();
        schedule = dbHandler.getSchAll();
        for(int i = 0; i< schedule.size(); i++){
            if(schedule.get(i).getCategory() == select_type){ // type일치
                // 기간내 존재하는
                String schTime = schedule.get(i).getYear()+schedule.get(i).getMonth()+schedule.get(i).getDay();
                try {
                    Date schDate = simpleDateFormat.parse(schTime);
                    long diff = TodayDate.getTime() - schDate.getTime(); // 오늘부터 스케줄의 날짜 차이
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    if(diffDays < period && diffDays >= 0){
                        list_sch.add(schedule.get(i));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        todoListAdapter = new TodoListAdapter(getActivity(), R.layout.spend_list_row, list_sch);
        billListView.setAdapter(todoListAdapter);
    }
}
