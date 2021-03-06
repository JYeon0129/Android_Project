package com.example.wkddu.android_project_mcm;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpendFormFragment extends Fragment {
    Button spendFormCancle, spendFormSave, spendFormAddBill;
    TextView spendFormDateText, spendFormTypeText;
    EditText spendFormTitleEdit, spendFormCostEdit;
    View spendFormTypeView;
    Calendar selected;
    LinearLayout spendDetailType;
    Type type;

    Context context;
    DBHandler dbHandler;
    Clipboard clipboard;
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
        spendDetailType = (LinearLayout) getActivity().findViewById(R.id.spendDetailType);
        spendFormSave = (Button) getActivity().findViewById(R.id.spendFormSave);
        spendFormAddBill = (Button) getActivity().findViewById(R.id.spendFormAddBill);
        spendFormTypeView = (View) getActivity().findViewById(R.id.spendFormTypeView);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        context = getActivity().getApplicationContext();

        //clipboard정보 추가
        dbHandler = new DBHandler(context,DBHandler.DATABASE_NAME, null, 1);
        clipboard = dbHandler.getClipboard();
        Calendar calendar = Calendar.getInstance();
        if(clipboard != null){
            spendFormTitleEdit.setText(clipboard.getUsage());
            spendFormCostEdit.setText(clipboard.getPayment());
            spendFormDateText.setText(calendar.get(Calendar.YEAR) + "년 " +
                    clipboard.getMonth() + "월 " + clipboard.getDay() + "일");
            dbHandler.deleteClipboard();
        }
        else {
            spendFormDateText.setText(calendar.get(Calendar.YEAR) + "년 " +
                    (calendar.get(Calendar.MONTH) + 1) + "월 " + calendar.get(Calendar.DATE) + "일");
        }
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

        spendDetailType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                TypePickerFragment typePickerFragment = new TypePickerFragment();

                typePickerFragment.show(fragmentManager, "typePickerFragment");
            }
        });

        spendFormCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCalendar();
            }
        });

        spendFormSave.setOnClickListener(new View.OnClickListener() {
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
                String dated[] = resultDate.split("-");

                Log.v("aaaaaa",dated[1]);
                int s_spend = Integer.parseInt(spendFormCostEdit.getText().toString());
                String s_usage = spendFormTitleEdit.getText().toString();
                int defaultType;
                if (type != null) {
                    defaultType = type.getTypeNum();
                } else {
                    defaultType = 1;
                }
                TABLE_SCH table_sch = new TABLE_SCH(dated[0],dated[1],dated[2],defaultType,s_spend,s_usage);
                Log.v("year : " , dated[0]);
                Log.v("month : " , dated[1]);
                Log.v("day : " , dated[2]);
                dbHandler.addSch(table_sch);
                spendFormCostEdit.setText("");
                spendFormTitleEdit.setText("");
                Toast.makeText(context, "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                goToCalendar();
            }
        });

        /*
         * 심다슬 : spendFormAddBill 클릭 시 영수증 추가되게 하면 됨당!
         */

        spendFormAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                일단은 activity로 연결함
                클릭했을 경우, 사진, 갤러리 선택하는 팝업창 뜨게 하고
                제목과 가격을 인텐트로 넘겨 받음
                 */
                Intent intent=new Intent(getActivity().getApplicationContext(), BillRegisterActivity.class);
                startActivity(intent);

            }
        });
    }

    private void goToCalendar() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.mainFragmantContainer);
        transaction.remove(currentFragment);

        CalenderFragment calendarFragment = (CalenderFragment) new CalenderFragment();
        transaction.replace(R.id.mainFragmantContainer, calendarFragment);
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
        transaction.commit();


        ((MainActivity) getActivity()).currentFragment = ((MainActivity) getActivity()).CALENDAR_FRAGMENT;

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(spendFormCostEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(spendFormTitleEdit.getWindowToken(), 0);
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (selected == null) selected = Calendar.getInstance();
            selected.set(year, monthOfYear, dayOfMonth);
            spendFormDateText.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth +"일");
        }
    };


    public void setType(Type type) {
        this.type = type;
        spendFormTypeText.setText(type.getTypeName());
        spendFormTypeView.setBackgroundColor(context.getResources().getColor(type.getTypeColor()));
    }

}
