package com.example.memo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddDataActivity extends AppCompatActivity {

    EditText etmemo;
    private Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        etmemo=(EditText)findViewById(R.id.editmemo);
        btn_save=(Button)findViewById(R.id.btn_add);



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String memo=etmemo.getText().toString();
                MemoDatalist memoDataList=new MemoDatalist();
                memoDataList.setMemo(memo);
                memoDataList.setAlarm(1);
                Fragment1.memoDatabase.memoDao().addData(memoDataList);
                Toast.makeText(getApplicationContext(),"일정이 추가되었습니다.",Toast.LENGTH_LONG).show();
                etmemo.setText("");
            }
        });


    }
}
