package com.example.wkddu.android_project_mcm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;


/*
 * 할 일 상세보기 화면입니다
 * 여기서 해줘야 할 일은
 * 클릭한 할 일의 자세한 내용을 가져와서 보여주기만 하면 됩니다!
 * 수정 버튼 누르면 TodoFormFragment에 내용만 채워서 보여주면 될 것 같아요
 */

public class TodoDetailFragment extends Fragment {
    TextView todoDetailTitleText, todoDetailCostText, todoDetailDateText,
        todoDetailTypeText, todoDetailBalanceText, todoDetailAllowText;
    Button todoDetailUpdate, todoDetailDelete;
    ImageButton todoDetailBack;
    View todoDetailTypeView;

    Todo todo;

    public TodoDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    public void init() {
        todoDetailTitleText = (TextView) getActivity().findViewById(R.id.todoDetailTitleText);
        todoDetailCostText = (TextView) getActivity().findViewById(R.id.todoDetailCostText);
        todoDetailDateText = (TextView) getActivity().findViewById(R.id.todoDetailDateText);
        todoDetailTypeText = (TextView) getActivity().findViewById(R.id.todoDetailTypeText);
        todoDetailBalanceText = (TextView) getActivity().findViewById(R.id.todoDetailBalanceText);
        todoDetailAllowText = (TextView) getActivity().findViewById(R.id.todoDetailAllowText);
        todoDetailUpdate = (Button) getActivity().findViewById(R.id.todoDetailUpdate);
        todoDetailDelete = (Button) getActivity().findViewById(R.id.todoDetailDelete);
        todoDetailBack = (ImageButton) getActivity().findViewById(R.id.todoDetailBack);
        todoDetailTypeView = (View) getActivity().findViewById(R.id.todoDetailTypeView);

        if (todoDetailTitleText != null) {
            todoDetailTitleText.setText(todo.getTodoName());
        }

        if (todoDetailCostText != null) {
            todoDetailCostText.setText(todo.getCost()+"원");
        }

        if (todoDetailDateText != null) {
            Date current = todo.getDate();
            String str = current.getYear() + "년 " + current.getMonth() + "월 " + current.getDay() + "일";
            todoDetailDateText.setText(str);
        }

        /*
         * 이강민 : 밑에 뷰들에 값 채워줘야 해요!
         * todoDetailTypeView : 타입 색상 보여주는 뷰입니다
         * todoDetailTypeText : 타입의 이름 (술약속인지 밥약속인지..) 보여주는 뷰입니다
         * todoDetailBalanceText : 현재 잔고가 얼마인지 보여주는 뷰입니다
         * todoDetailAllowText : 오늘 하루 허용된 용돈이 얼마인지 보여주는 뷰입니다
         *
         * 그리고 버튼 눌렀을 때 액션도 추가해줘야 합니다. 리스터는 붙여뒀습니다!
         * todoDetailUpdate : 수정 버튼 클릭
         * todoDetailDelete : 삭제 버튼 클릭
         */

        if (todoDetailTypeView != null) {

        }

        if (todoDetailTypeText != null) {

        }

        if (todoDetailBalanceText != null) {

        }

        if (todoDetailAllowText != null) {

        }

        todoDetailUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        todoDetailDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        todoDetailBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.mainFragmantContainer);
                transaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
                transaction.remove(currentFragment).commit();

                ((MainActivity) getActivity()).currentFragment = ((MainActivity) getActivity()).CALENDAR_FRAGMENT;

            }
        });
    }

    /*이 프래그먼트가 받아야 하는 정보는
     * Todo todo : 할 일에 대한 객체 자체만 받으면 된다.
     */

    public void setData(Todo todo) {
        this.todo = todo;
    }

}
