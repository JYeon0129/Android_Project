package com.example.wkddu.android_project_mcm;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
 * 할 일 폼 화면입니다. 새로 등록하거나 기존 내용을 수정할 때 씁니다!
 * 여기서 해줘야 할 일은
 * 저장 누르면 새로 저장하거나 수정하는 것만 하면 됩니다!
 */

public class TodoFormFragment extends Fragment {
    EditText todoFormTitleEdit, todoFormCostEdit;
    TextView todoFormTypeText, todoFormBalanceText, todoFormAllowText, todoFormDateText, todoFormCostText;
    View todoFormTypeView;
    Button todoFormCancle, todoFormSave, todoFormButton1, todoFormButton2, todoFormButton3;
    ListView todoFormListView;
    LinearLayout todoDetailType;
    Context context;
    Calendar selected;
    Type type;
    DBHandler dbHandler;
    ArrayList<TABLE_SCH> schedule;
    ArrayList<TABLE_SCH> list_sch;
    TodoListAdapter todoListAdapter;

    public TodoFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    public void init() {
        dbHandler = new DBHandler(getContext(),DBHandler.DATABASE_NAME,null, 1);
        todoFormTitleEdit = (EditText) getActivity().findViewById(R.id.todoFormTitleEdit);
        todoFormCostEdit = (EditText) getActivity().findViewById(R.id.todoFormCostEdit);
        todoFormCostText = (TextView) getActivity().findViewById(R.id.todoFormCostText);
        todoFormTypeText = (TextView) getActivity().findViewById(R.id.todoFormTypeText);
        todoFormDateText = (TextView) getActivity().findViewById(R.id.todoFormDateText);
        todoFormTypeView = (View) getActivity().findViewById(R.id.todoFormTypeView);
        todoFormListView = (ListView) getActivity().findViewById(R.id.todoFormListView);
        todoDetailType = (LinearLayout) getActivity().findViewById(R.id.todoDetailType);
        todoFormCancle = (Button) getActivity().findViewById(R.id.todoFormCancle);
        todoFormSave = (Button) getActivity().findViewById(R.id.todoFormSave);
        todoFormButton1 = (Button) getActivity().findViewById(R.id.todoFormButton1);
        todoFormButton2 = (Button) getActivity().findViewById(R.id.todoFormButton2);
        todoFormButton3 = (Button) getActivity().findViewById(R.id.todoFormButton3);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        context = getActivity().getApplicationContext();

        Calendar calendar = Calendar.getInstance();
        todoFormDateText.setText(calendar.get(Calendar.YEAR) + "년 " +
                        (calendar.get(Calendar.MONTH) + 1) + "월 " + calendar.get(Calendar.DATE) + "일");

        setTodoListAdapter();
        type = new Type(1,"식비",R.color.bluegreen);
        selectList(type.getTypeNum(),14);

        todoFormDateText.setOnClickListener(new View.OnClickListener() {
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

        todoDetailType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                TypePickerFragment typePickerFragment = new TypePickerFragment();

                typePickerFragment.show(fragmentManager, "typePickerFragment");
            }
        });

        todoFormCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCalendar();
            }
        });

        todoFormSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar resultCal;
                if (selected != null) {
                    resultCal = selected;
                } else {
                    resultCal = Calendar.getInstance();
                }
                String year = resultCal.get(Calendar.YEAR)+"";
                String month = resultCal.get(Calendar.MONTH)+"";
                String day = resultCal.get(Calendar.DAY_OF_MONTH)+"";

                int defaultType;
                if (type != null) {
                    defaultType = type.getTypeNum();
                } else {
                    defaultType = 1;
                }
                int spend = Integer.parseInt(todoFormCostEdit.getText().toString());
                String usage = todoFormTitleEdit.getText().toString();

                TABLE_SCH sch = new TABLE_SCH(year, month, day, defaultType, spend, usage);
                DBHandler dbHandler = new DBHandler(context, null, null, 1);
                dbHandler.addSch(sch);

                Toast.makeText(context, "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                goToCalendar();
            }
        });

        todoFormButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("click1","click btn1");
                selectList(type.getTypeNum(),15);
            }
        });

        todoFormButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("click2","click btn2");
                selectList(type.getTypeNum(),31);
            }
        });

        todoFormButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("click3","click btn3");
                selectList(type.getTypeNum(),91);
            }
        });
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (selected == null) selected = Calendar.getInstance();
            selected.set(year, monthOfYear, dayOfMonth);
            todoFormDateText.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth +"일");
        }
    };

    private void goToCalendar() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.mainFragmantContainer);
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
        transaction.remove(currentFragment).commit();

        ((MainActivity) getActivity()).currentFragment = ((MainActivity) getActivity()).CALENDAR_FRAGMENT;

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(todoFormCostEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(todoFormTitleEdit.getWindowToken(), 0);
    }

    /*
     * 이장연 : 지출 내역 매핑해주기
     * 해당 지출 타입에 맞는 거를 추려서 todoListAdapter에 넣어주면 됩니다!
     * todoFormButton1, todoFormButton2, todoFormButton3 버튼 클릭 이벤트도 구현해야 합니다
     * 대충 list 만들어서 샘플로 구현해뒀습니다!
     */

    public void setTodoListAdapter() {

    }

    public void setType(Type type) {
        this.type = type;
        todoFormTypeText.setText(type.getTypeName());
        todoFormTypeView.setBackgroundColor(context.getResources().getColor(type.getTypeColor()));
    }
    public void selectList(int select_type, int period){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date TodayDate = new Date();
        int period_total_spend=0;
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
                    if(diffDays < period && diffDays > 0){
                        list_sch.add(schedule.get(i));
                        period_total_spend += schedule.get(i).getSpend();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        if(list_sch.size() > 0){
            int p = period_total_spend / list_sch.size();
            String temp = String.valueOf(p);
            todoFormCostText.setText(temp+"원");
            Log.v("todoForm",""+temp+"원");
        }
        else{
            Log.v("todoForm","else");
            todoFormCostText.setText("0원");
        }
        todoListAdapter = new TodoListAdapter(getActivity(), R.layout.spend_list_row, list_sch);
        todoFormListView.setAdapter(todoListAdapter);
    }
}
