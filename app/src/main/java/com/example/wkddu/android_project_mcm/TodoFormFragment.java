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
    TextView todoFormTypeText, todoFormBalanceText, todoFormAllowText, todoFormDateText;
    View todoFormTypeView;
    Button todoFormCancle, todoFormSave, todoFormButton1, todoFormButton2, todoFormButton3;
    ListView todoFormListView;
    LinearLayout todoDetailType;
    Context context;
    Calendar selected;
    Type type;


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
        todoFormTitleEdit = (EditText) getActivity().findViewById(R.id.todoFormTitleEdit);
        todoFormCostEdit = (EditText) getActivity().findViewById(R.id.todoFormCostEdit);
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
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String resultDate = formatter.format(resultCal.getTime());

                int defaultType;
                if (type != null) {
                    defaultType = type.getTypeNum();
                } else {
                    defaultType = 1;
                }
            }
        });

        todoFormButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        todoFormButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        todoFormButton3.setOnClickListener(new View.OnClickListener() {
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
        ArrayList<Schedule> spends = new ArrayList<>();
        spends.add(new Schedule("술약속", 10000, 0,
            new Date(2018, 6, 6)));
        spends.add(new Schedule("닭발집 부숨", 22000, 0,
                new Date(2018, 6, 7)));
        spends.add(new Schedule("곱쏘*^^*", 14000, 0,
                new Date(2018, 6, 8)));
        spends.add(new Schedule("술약속", 3000, 0,
                new Date(2018, 6, 9)));

//        TodoListAdapter todoListAdapter = new TodoListAdapter(context, R.layout.spend_list_row, spends);
//        todoFormListView.setAdapter(todoListAdapter);
    }

    public void setType(Type type) {
        this.type = type;
        todoFormTypeText.setText(type.getTypeName());
        todoFormTypeView.setBackgroundColor(context.getResources().getColor(type.getTypeColor()));
    }

}
