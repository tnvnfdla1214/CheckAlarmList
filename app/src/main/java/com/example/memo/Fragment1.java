package com.example.memo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Fragment1 extends Fragment {

    ViewGroup viewGroup;

    List<MemoDatalist> memoDataLists;
    public static MemoDatabase memoDatabase;
    public RecyclerView rv;
    ItemTouchHelper helper;
    MemoAdapter adapter;
    Button btn_save;


    public static Fragment1 mContext;

    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
    }

     */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_main,container,false);

        if(memoDataLists==null){
            memoDatabase= Room.databaseBuilder(getActivity().getApplicationContext(),MemoDatabase.class,"infodb").allowMainThreadQueries().build();
        }
        else{
            memoDataLists=memoDatabase.memoDao().getData();
        }
        rv=(RecyclerView)viewGroup.findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        memoDataLists=memoDatabase.memoDao().getData();
        adapter = new MemoAdapter(memoDataLists,getContext());
        rv.setAdapter(adapter);

        //ItemTouchHelper 생성
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        //RecyclerView에 ItemTouchHelper 붙이기
        helper.attachToRecyclerView(rv);
        Review();
        btn_save=(Button)viewGroup.findViewById(R.id.add_bt);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //3번
                Intent intent = new Intent(getActivity(), AddDataActivity.class);
                startActivity(intent);
            }
        });


        return viewGroup;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);

    }

    public void Review(){
        memoDataLists=memoDatabase.memoDao().getData();
        //RecyclerView의 Adapter 세팅
        //5번
        adapter = new MemoAdapter(memoDataLists,getContext());
        rv.setAdapter(adapter);

    }

    

}
