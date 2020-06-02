package com.example.memo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_main,container,false);       // activity_main 레이아웃과 연결

        ////데이터베이스에서 불러온 List가 비어있다면 불러오고 아니라면 바로 데이터를 읽는다.
        if(memoDataLists==null){
            memoDatabase= Room.databaseBuilder(getActivity().getApplicationContext(),MemoDatabase.class,"infodb").allowMainThreadQueries().build();
        }
        else{
            memoDataLists=memoDatabase.memoDao().getData();
        }
        rv=(RecyclerView)viewGroup.findViewById(R.id.rec);              // RecyclerView를 연결
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        memoDataLists=memoDatabase.memoDao().getMemoData();             // 존재하는 모든 메모를 불러온다.
        adapter = new PerMemoAdapter(memoDataLists,getContext());       // PerMemoAdapter를 통해 RecyclerView에 set
        rv.setAdapter(adapter);
        helper = new ItemTouchHelper(new ItemTouchHelperCallback2(adapter));
        helper.attachToRecyclerView(rv);                                // RecyclerView에 ItemTouchHelper 붙이기
        return viewGroup;
    }
}