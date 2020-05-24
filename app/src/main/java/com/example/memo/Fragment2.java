package com.example.memo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.List;

public class Fragment2 extends Fragment {
    ViewGroup viewGroup;
    List<MemoDatalist> memoDataLists;
    public static MemoDatabase memoDatabase;
    private RecyclerView rv;
    ItemTouchHelper helper;
    PerMemoAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_memo,container,false);
        memoDatabase= Room.databaseBuilder(getActivity().getApplicationContext(),MemoDatabase.class,"infodb").allowMainThreadQueries().build();
        rv=(RecyclerView)viewGroup.findViewById(R.id.rec1);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        memoDataLists=memoDatabase.memoDao().getMemoData();
        adapter = new PerMemoAdapter(memoDataLists,getContext());
        rv.setAdapter(adapter);
        helper = new ItemTouchHelper(new ItemTouchHelperCallback2(adapter));
        helper.attachToRecyclerView(rv);
        return viewGroup;
    }
}