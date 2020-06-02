package com.example.memo;

import android.content.Intent;
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
    public RecyclerView rv;
    ItemTouchHelper helper;
    MemoAdapter adapter;
    Button btn_save;
    public static Fragment1 mContext;

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
        memoDataLists=memoDatabase.memoDao().getData();                 // 데이터를 가져온다. (확인 버튼을 누르지 않은 = [Alarm = 1])
        adapter = new MemoAdapter(memoDataLists,getContext());          // MemoAdapter를 통해 RecyclerView에 set
        rv.setAdapter(adapter);
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        helper.attachToRecyclerView(rv);                                // RecyclerView에 ItemTouchHelper 붙이기
        Review();                                                       // Review 실행
        btn_save=(Button)viewGroup.findViewById(R.id.add_bt);           // 추가 버튼을 연결
        //// 추가 버튼을 눌렀을 때 이벤트 ////
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddDataActivity.class);   // AddDataActivity로 이동하라는 Intent
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

    public void Review(){           //// 데이터를 불러와 어댑터로 recyclerview에 set
        memoDataLists=memoDatabase.memoDao().getData();
        //RecyclerView의 Adapter 세팅
        //5번
        adapter = new MemoAdapter(memoDataLists,getContext());
        rv.setAdapter(adapter);
    }
}
