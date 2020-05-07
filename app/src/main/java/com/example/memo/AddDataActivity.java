package com.example.memo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddDataActivity extends AppCompatActivity {

    EditText etmemo;
    private Button btn_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        etmemo=(EditText)findViewById(R.id.editmemo);
        btn_save=(Button)findViewById(R.id.btn_add);
        final MemoDatalist memoDataList=new MemoDatalist();
        final CheckBox am = (CheckBox) findViewById(R.id.AM) ;
        final CheckBox pm = (CheckBox) findViewById(R.id.PM) ;

        am.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v){
                if(((CheckBox)v).isChecked()){
                    memoDataList.setAM_PM("오전 알림");
                    pm.setChecked(false);
                }
            }
        });

        pm.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v){
                if(((CheckBox)v).isChecked()){
                    memoDataList.setAM_PM("오후 알림");
                    am.setChecked(false);
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pm.isChecked() || am.isChecked()){
                }else{
                    Toast.makeText(getApplicationContext(),"오전/오후를 선택해주세요.",Toast.LENGTH_LONG).show();

                }



                String memo=etmemo.getText().toString();


                memoDataList.setMemo(memo);
                memoDataList.setAlarm(1);
                int ID_len = memoDataList.getId();

                Fragment1.memoDatabase.memoDao().addData(memoDataList);

                Toast.makeText(getApplicationContext(),"일정이 추가되었습니다.",Toast.LENGTH_LONG).show();

                SharedPreferences sp = getSharedPreferences("myFile",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("ID_len",ID_len);

                etmemo.setText("");
                am.setChecked(false);
                pm.setChecked(false);


                ((Fragment1) Fragment1.mContext).Review();



            }
        });


    }
}
