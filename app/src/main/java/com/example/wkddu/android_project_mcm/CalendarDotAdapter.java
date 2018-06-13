package com.example.wkddu.android_project_mcm;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/*
 * 캘린더의 각 칸에 일정을 점으로 찍어주는 어댑터입니다
 * 여기서 해줘야 할 일은
 * 해당 날짜에 있는 일정을 불러와서 타입에 따라 점만 찍어주면 됩니다!
 */



public class CalendarDotAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<Schedule> todoList;
    Context context;

    /*DatabaseRerence mDatabase;
    mDatabase = FirebaseDatabase.getInstance().getReference("year");
    DatabaseReference rDatabase = mDatabase.child("month");
    DatabaseReference rDatabase = rDatabase.child("day");*/

    public CalendarDotAdapter(Context context, ArrayList<Schedule> Data) {
        this.context = context;
        this.todoList = Data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.calendar_todo_dot, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        int todoType = todoList.get(position).getType();

        Helpers helpers = new Helpers();
        int todoColor = helpers.returnType(todoType).getTypeColor();

        GradientDrawable drawable = (GradientDrawable) holder.dot.getBackground();
        drawable.setColor(context.getResources().getColor(todoColor));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    View dot;

    public MyViewHolder(View v) {
        super(v);
        dot = (View) v.findViewById(R.id.calRowDot);

        /*
         *
         * year -> month -> day -> todo -> category(todoFormTypeView) // 일정카테고리별 색상
         * ex) myRef.child("year").child("month").child("day").child("spendtodo").child("category").getValue(n); // 일정 카테고리별 색상 갯수 받기?
         *
         *    myRef.child("year").child("month").child("day").child("spendtodo").child("category").addChildEventListener(new ChildEventListener() {
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
         *    // 받은 일정카테고리별 색상을 점 색상으로 매칭
         *    // 매칭된 점색상을 달력에 출력하기
         */
    }
}
