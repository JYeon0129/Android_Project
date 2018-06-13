package com.example.wkddu.android_project_mcm;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
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
    Context context;
    TextView todoDetailTitleText, todoDetailCostText, todoDetailDateText,
        todoDetailTypeText, todoDetailBalanceText, todoDetailAllowText, todoDetailAfterText;
    Button todoDetailUpdate, todoDetailDelete;
    ImageButton todoDetailBack;
    View todoDetailTypeView;

    Schedule todo;

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
        context = getActivity().getApplicationContext();
        todoDetailTitleText = (TextView) getActivity().findViewById(R.id.todoDetailTitleText);
        todoDetailCostText = (TextView) getActivity().findViewById(R.id.todoDetailCostText);
        todoDetailDateText = (TextView) getActivity().findViewById(R.id.todoDetailDateText);
        todoDetailTypeText = (TextView) getActivity().findViewById(R.id.todoDetailTypeText);
        todoDetailBalanceText = (TextView) getActivity().findViewById(R.id.todoDetailBalanceText);
        todoDetailAllowText = (TextView) getActivity().findViewById(R.id.todoDetailAllowText);
        todoDetailAfterText = (TextView) getActivity().findViewById(R.id.todoDetailAfterText);
        todoDetailUpdate = (Button) getActivity().findViewById(R.id.todoDetailUpdate);
        todoDetailDelete = (Button) getActivity().findViewById(R.id.todoDetailDelete);
        todoDetailBack = (ImageButton) getActivity().findViewById(R.id.todoDetailBack);
        todoDetailTypeView = (View) getActivity().findViewById(R.id.todoDetailTypeView);

        todoDetailTitleText.setText(todo.getTitle());
        todoDetailCostText.setText(todo.getCost()+"원");

        Date current = todo.getDate();
        String str = current.getYear() + "년 " + current.getMonth() + "월 " + current.getDay() + "일";
        todoDetailDateText.setText(str);

        Helpers helpers = new Helpers();
        Type type = helpers.returnType(todo.getType());
        todoDetailTypeText.setText(type.getTypeName());

        ColorDrawable drawable = (ColorDrawable) todoDetailTypeView.getBackground();
        drawable.setColor(context.getResources().getColor(type.getTypeColor()));

        /*
         * todoDetailBalanceText : year -> month -> day  // 현재잔고(totalbudget - totalspend)
         * todoDetailAllowText : year -> month -> day -> limit // 할당금액(하루 사용할 수 있는 금액)
         * todoDetailAfterText : year -> month -> day // 사용후 잔액(현재잔고 - 할당금액: (totalbuget-totalspend) - limit)
         * myRef.child("year").child("month").child("day").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //이것만 필요함


                        // A new comment has been added, add it to the displayed list
                        //private List<Comment> mComments = new ArrayList<>(); //리스트를 만듦(객체들의 리스트)

                        //Comment comment = dataSnapshot.getValue(Comment.class); //ImageData class 로 바꿔줘야됨됨
                        ImageData data = dataSnapshot.getValue(ImageData.class);

                        // [START_EXCLUDE]
                        // Update RecyclerView
                        mCommentIds.add(dataSnapshot.getKey());//getKey() : 키값을 가져옴
                        mData.add(data);//arrayList에 객체 하나를 추가(add)함
                        mAdapter.notifyItemInserted(mData.size() - 1);
                        // [END_EXCLUDE]

                       Log.d("출력 로그", "onChildAdded:" + data.description.toString());
                    }
                }
         * myRef.child("year").child("month").child("day").child("limit").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //이것만 필요함


                        // A new comment has been added, add it to the displayed list
                        //private List<Comment> mComments = new ArrayList<>(); //리스트를 만듦(객체들의 리스트)

                        //Comment comment = dataSnapshot.getValue(Comment.class); //ImageData class 로 바꿔줘야됨됨
                        ImageData data = dataSnapshot.getValue(ImageData.class);

                        // [START_EXCLUDE]
                        // Update RecyclerView
                        mCommentIds.add(dataSnapshot.getKey());//getKey() : 키값을 가져옴
                        mData.add(data);//arrayList에 객체 하나를 추가(add)함
                        mAdapter.notifyItemInserted(mData.size() - 1);
                        // [END_EXCLUDE]

                       Log.d("출력 로그", "onChildAdded:" + data.description.toString());
                    }
                }
         */
        /*
         * 이강민 : 밑에 뷰들에 값 채워줘야 해요!
         * todoDetailBalanceText : 현재 잔고가 얼마인지 보여주는 뷰입니다
         * todoDetailAllowText : 오늘 하루 허용된 용돈이 얼마인지 보여주는 뷰입니다
         * todoDetailAfterText : 해당 금액을 사용하고 난 뒤에 얼마가 남는지 보여주는 뷰입니다
         *
         * 그리고 버튼 눌렀을 때 액션도 추가해줘야 합니다. 리스터는 붙여뒀습니다!
         * todoDetailUpdate : 수정 버튼 클릭
         * todoDetailDelete : 삭제 버튼 클릭
         */


        todoDetailUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*
                 * TodoFormFragment에서 todoFormDateText(일정날짜), todoFormTitleEdit(일정제목), todoFormTypeText(일정종류), todoFormCostEdit(사용할 돈-사용자지정), todoFormCostText(예상소비비용-자동으로 계산) 값 받아서 Firebase에 저장하기
                 * year -> month -> day  // 해당날짜
                 * year -> month -> day -> todo // 해당일정제목
                 * year -> month -> day -> spendtodo -> category // 일정종류
                 * year -> month -> day -> spendtodo -> spend // 사용할 돈(사용자지정)
                 * year -> month -> day -> limit // 예상소비비용(자동계산)
                 * myRef.child("year").child("month").child("day").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //이것만 필요함


                        // A new comment has been added, add it to the displayed list
                        //private List<Comment> mComments = new ArrayList<>(); //리스트를 만듦(객체들의 리스트)

                        //Comment comment = dataSnapshot.getValue(Comment.class); //ImageData class 로 바꿔줘야됨됨
                        ImageData data = dataSnapshot.getValue(ImageData.class);

                        // [START_EXCLUDE]
                        // Update RecyclerView
                        mCommentIds.add(dataSnapshot.getKey());//getKey() : 키값을 가져옴
                        mData.add(data);//arrayList에 객체 하나를 추가(add)함
                        mAdapter.notifyItemInserted(mData.size() - 1);
                        // [END_EXCLUDE]

                       Log.d("출력 로그", "onChildAdded:" + data.description.toString());
                    }
                }
                 * myRef.child("year").child("month").child("day").child("todo").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //이것만 필요함


                        // A new comment has been added, add it to the displayed list
                        //private List<Comment> mComments = new ArrayList<>(); //리스트를 만듦(객체들의 리스트)

                        //Comment comment = dataSnapshot.getValue(Comment.class); //ImageData class 로 바꿔줘야됨됨
                        ImageData data = dataSnapshot.getValue(ImageData.class);

                        // [START_EXCLUDE]
                        // Update RecyclerView
                        mCommentIds.add(dataSnapshot.getKey());//getKey() : 키값을 가져옴
                        mData.add(data);//arrayList에 객체 하나를 추가(add)함
                        mAdapter.notifyItemInserted(mData.size() - 1);
                        // [END_EXCLUDE]

                       Log.d("출력 로그", "onChildAdded:" + data.description.toString());
                    }
                }
                myRef.child("year").child("month").child("day").child("spendtodo").child("category").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //이것만 필요함


                        // A new comment has been added, add it to the displayed list
                        //private List<Comment> mComments = new ArrayList<>(); //리스트를 만듦(객체들의 리스트)

                        //Comment comment = dataSnapshot.getValue(Comment.class); //ImageData class 로 바꿔줘야됨됨
                        ImageData data = dataSnapshot.getValue(ImageData.class);

                        // [START_EXCLUDE]
                        // Update RecyclerView
                        mCommentIds.add(dataSnapshot.getKey());//getKey() : 키값을 가져옴
                        mData.add(data);//arrayList에 객체 하나를 추가(add)함
                        mAdapter.notifyItemInserted(mData.size() - 1);
                        // [END_EXCLUDE]

                       Log.d("출력 로그", "onChildAdded:" + data.description.toString());
                    }
                }
                myRef.child("year").child("month").child("day").child("spendtodo").child("spend").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //이것만 필요함


                        // A new comment has been added, add it to the displayed list
                        //private List<Comment> mComments = new ArrayList<>(); //리스트를 만듦(객체들의 리스트)

                        //Comment comment = dataSnapshot.getValue(Comment.class); //ImageData class 로 바꿔줘야됨됨
                        ImageData data = dataSnapshot.getValue(ImageData.class);

                        // [START_EXCLUDE]
                        // Update RecyclerView
                        mCommentIds.add(dataSnapshot.getKey());//getKey() : 키값을 가져옴
                        mData.add(data);//arrayList에 객체 하나를 추가(add)함
                        mAdapter.notifyItemInserted(mData.size() - 1);
                        // [END_EXCLUDE]

                       Log.d("출력 로그", "onChildAdded:" + data.description.toString());
                    }
                }
                myRef.child("year").child("month").child("day").child("limit").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //이것만 필요함


                        // A new comment has been added, add it to the displayed list
                        //private List<Comment> mComments = new ArrayList<>(); //리스트를 만듦(객체들의 리스트)

                        //Comment comment = dataSnapshot.getValue(Comment.class); //ImageData class 로 바꿔줘야됨됨
                        ImageData data = dataSnapshot.getValue(ImageData.class);

                        // [START_EXCLUDE]
                        // Update RecyclerView
                        mCommentIds.add(dataSnapshot.getKey());//getKey() : 키값을 가져옴
                        mData.add(data);//arrayList에 객체 하나를 추가(add)함
                        mAdapter.notifyItemInserted(mData.size() - 1);
                        // [END_EXCLUDE]

                       Log.d("출력 로그", "onChildAdded:" + data.description.toString());
                    }
                }

                 */

            }
        });

        todoDetailDelete.setOnClickListener(new View.OnClickListener() { //해당 일정 지우기 -> 해당버튼(삭제버튼) 추가해야한다.
            public void onClick(View v) {

                /*
                 * year -> month -> day -> todo // 해당일정
                 * myRef.child("year").child("month").child("day").child("limit").deleteChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChilddeleted(DataSnapshot dataSnapshot, String s) {
                        //이것만 필요함


                        // A new comment has been added, add it to the displayed list
                        //private List<Comment> mComments = new ArrayList<>(); //리스트를 만듦(객체들의 리스트)

                        //Comment comment = dataSnapshot.getValue(Comment.class); //ImageData class 로 바꿔줘야됨됨
                        ImageData data = dataSnapshot.getValue(ImageData.class);

                        // [START_EXCLUDE]
                        // Update RecyclerView
                        mCommentIds.delete(dataSnapshot.getKey());//getKey() : 키값을 가져옴
                        mData.delete(data);//arrayList에 객체 하나를 추가(add)함
                        mAdapter.notifyItemInserted(mData.size() - 1);
                        // [END_EXCLUDE]

                       Log.d("출력 로그", "onChilddeleted:" + data.description.toString());
                    }
                }
                 */
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

    public void setData(Schedule todo) {
        this.todo = todo;
    }

}
