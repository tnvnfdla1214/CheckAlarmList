package com.example.memo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddDataActivity extends AppCompatActivity {
    EditText etmemo;            // editmemo를 받아 쓸 EditText 변수 선언
    private Button btn_save;    // btn_add를 받아 쓸 Button 변수 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);         // activity_add_data 레이아웃과 연결
        etmemo=(EditText)findViewById(R.id.editmemo);       // 추가 할 메모 입력할 EditText와 연결
        btn_save=(Button)findViewById(R.id.btn_add);        // 추가 버튼 Button과 연결
        final MemoDatalist memoDataList=new MemoDatalist(); // 데이터베이스의 참조변수 선언

        //// **추가 버튼을 눌렀을 때의 이벤트** ////
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String memo=etmemo.getText().toString();                    // etmemo로 받은 값을 memo에 String 형태로 저장
                memoDataList.setMemo(memo);                                 // memo를 데이터베이스의 Memo에 set
                memoDataList.setAlarm(1);                                   // 일정 확인 하지 않은 상태(Alarm = 1)로 만들어 Alarm에 set
                int ID_len = memoDataList.getId();
                Fragment1.memoDatabase.memoDao().addData(memoDataList);     // 받은 Data들을 memoDatabase에 add
                Toast.makeText(getApplicationContext(),"일정이 추가되었습니다.",Toast.LENGTH_LONG).show();    //확인의 토스 메시지
                SharedPreferences sp = getSharedPreferences("myFile",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("ID_len",ID_len);

                etmemo.setText("");                                         // 메모 추가 후 입력란 Null 값으로 set
                Fragment1.mContext.Review();                                // 추가가 된 후 리스트 화면에서 바뀐 정보 Review(라이브러리 함수 x)

            }
        });
    }
}

