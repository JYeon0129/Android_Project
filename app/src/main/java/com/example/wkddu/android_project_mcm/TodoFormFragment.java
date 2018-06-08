package com.example.wkddu.android_project_mcm;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/*
 * 할 일 폼 화면입니다. 새로 등록하거나 기존 내용을 수정할 때 씁니다!
 * 여기서 해줘야 할 일은
 * 저장 누르면 새로 저장하거나 수정하는 것만 하면 됩니다!
 */

public class TodoFormFragment extends Fragment implements View.OnClickListener {
    EditText todoFormTitleEdit, todoFormCostEdit;
    TextView todoFormTypeText, todoFormBalanceText, todoFormAllowText, todoFormDateText;
    View todoFormTypeView;
    ListView todoFormListView;
    Context context;


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

        context = getActivity().getApplicationContext();

        setTodoListAdapter();
    }

    /*
     * 이장연 : 지출 내역 매핑해주기
     * 해당 지출 타입에 맞는 거를 추려서 todoListAdapter에 넣어주면 됩니다!
     * todoFormButton1, todoFormButton2, todoFormButton3 버튼 클릭 이벤트도 구현해야 합니다
     */

    public void setTodoListAdapter() {
        ArrayList<Spend> spends = new ArrayList<>();
        spends.add(new Spend("술약속", 10000, 0,
            new Date(2018, 6, 6)));
        spends.add(new Spend("닭발집 부숨", 22000, 0,
                new Date(2018, 6, 7)));
        spends.add(new Spend("곱쏘*^^*", 14000, 0,
                new Date(2018, 6, 8)));
        spends.add(new Spend("술약속", 3000, 0,
                new Date(2018, 6, 9)));

        TodoListAdapter todoListAdapter = new TodoListAdapter(context, R.layout.spend_list_row, spends);
        todoFormListView.setAdapter(todoListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.todoFormCancle:
                break;

            case R.id.todoFormSave:
                break;

            case R.id.todoFormButton1:
                break;

            case R.id.todoFormButton2:
                break;

            case R.id.todoFormButton3:
                break;
        }
    }
}
