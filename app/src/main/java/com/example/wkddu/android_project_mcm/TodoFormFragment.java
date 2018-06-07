package com.example.wkddu.android_project_mcm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/*
 * 할 일 폼 화면입니다. 새로 등록하거나 기존 내용을 수정할 때 씁니다!
 * 여기서 해줘야 할 일은
 * 저장 누르면 새로 저장하거나 수정하는 것만 하면 됩니다!
 */

public class TodoFormFragment extends Fragment {
    EditText todoFormTitleEdit, todoFormCostEdit;
    TextView todoFormTypeText, todoFormBalanceText, todoFormAllowText, todoFormDateText;
    Button todoFormCancle, todoFormSave;
    View todoFormTypeView;

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
        todoFormBalanceText = (TextView) getActivity().findViewById(R.id.todoFormBalanceText);
        todoFormAllowText = (TextView) getActivity().findViewById(R.id.todoFormAllowText);
        todoFormDateText = (TextView) getActivity().findViewById(R.id.todoFormDateText);
        todoFormCancle = (Button) getActivity().findViewById(R.id.todoFormCancle);
        todoFormSave = (Button) getActivity().findViewById(R.id.todoFormSave);
        todoFormTypeView = (View) getActivity().findViewById(R.id.todoFormTypeView);

        if (todoFormTypeText != null) {

        }

        if (todoFormBalanceText != null) {

        }

        if (todoFormAllowText != null) {

        }

        if (todoFormDateText != null) {

        }

        todoFormCancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        todoFormSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

}
