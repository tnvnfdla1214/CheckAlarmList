package com.example.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static MemoDatabase memoDatabase;
    private RecyclerView rv;
    ItemTouchHelper helper;
    MemoAdapter adapter;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        memoDatabase= Room.databaseBuilder(getApplicationContext(),MemoDatabase.class,"infodb").allowMainThreadQueries().build();


        rv=(RecyclerView)findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        getData();

        List<MemoDatalist>memoDataLists=MainActivity.memoDatabase.memoDao().getMemoData();
        //RecyclerView의 Adapter 세팅
        adapter = new MemoAdapter(memoDataLists,this);
        rv.setAdapter(adapter);


        //ItemTouchHelper 생성
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        //RecyclerView에 ItemTouchHelper 붙이기
        helper.attachToRecyclerView(rv);


        btn_save=(Button)findViewById(R.id.add_bt);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddDataActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        class GetData extends AsyncTask<Void, Void, List<MemoDatalist>> {

            @Override
            protected List<MemoDatalist> doInBackground(Void... voids) {
                List<MemoDatalist> memoDataLists=MainActivity.memoDatabase.memoDao().getMemoData();
                return memoDataLists;
            }
            @Override
            protected void onPostExecute(List<MemoDatalist> memoDatalist) {
                MemoAdapter adapter=new MemoAdapter(memoDatalist,MainActivity.this);
                rv.setAdapter(adapter);
                super.onPostExecute(memoDatalist);
            }
        }

        GetData gd=new GetData();
        gd.execute();
    }

}
