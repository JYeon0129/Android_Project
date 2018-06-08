package com.example.wkddu.android_project_mcm;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpendFormFragment extends Fragment implements View.OnClickListener {
    Button spendFormCancle, spendFormSave, spendFormAddBill;
    TextView spendFormDateText, spendFormTypeText;
    EditText spendFormTitleEdit, spendFormCostEdit;
    View spendFormTypeView;


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


    }

    /*
     * 심다슬 : spendFormAddBill 클릭 시 영수증 추가되게 하면 됨당!
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spendFormCancle:
                break;

            case R.id.spendFormSave:
                break;

            case R.id.spendFormAddBill:
                break;
        }
    }

}
