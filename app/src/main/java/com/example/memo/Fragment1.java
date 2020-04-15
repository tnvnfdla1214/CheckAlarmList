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

public class Fragment1 extends Fragment {
    ViewGroup viewGroup;

    List<MemoDatalist> memoDataLists;
    public static MemoDatabase memoDatabase;
    private RecyclerView rv;
    ItemTouchHelper helper;
    MemoAdapter adapter;
    Button btn_save;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void getData() {
        class GetData extends AsyncTask<Void, Void, List<MemoDatalist>> {

            @Override
            protected List<MemoDatalist> doInBackground(Void... voids) {
                List<MemoDatalist> memoDataLists=Fragment1.memoDatabase.memoDao().getData();
                return memoDataLists;
            }
            @Override
            protected void onPostExecute(List<MemoDatalist> memoDatalist) {
                //6번
                MemoAdapter adapter=new MemoAdapter(memoDatalist,getActivity());
                rv.setAdapter(adapter);
                super.onPostExecute(memoDatalist);
            }
        }

        GetData gd=new GetData();
        gd.execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_main,container,false);

        //1번
        memoDatabase= Room.databaseBuilder(getActivity().getApplicationContext(),MemoDatabase.class,"infodb").allowMainThreadQueries().build();



        //2번
        rv=(RecyclerView)viewGroup.findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        //4번
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        getData();

        memoDataLists=Fragment1.memoDatabase.memoDao().getData();
        //RecyclerView의 Adapter 세팅
        //5번
        adapter = new MemoAdapter(memoDataLists,getContext());
        rv.setAdapter(adapter);


        //ItemTouchHelper 생성
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        //RecyclerView에 ItemTouchHelper 붙이기
        helper.attachToRecyclerView(rv);

        //2번
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


}
