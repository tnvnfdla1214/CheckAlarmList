package com.example.memo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialog extends Dialog {
    private OnDialogListener listener;
    private Button mod_bt;              // mod_bt을 받아 쓸 Button 변수 선언
    EditText mod_memo;                  // mod_memo를 받아 쓸 EditText 변수 선언
    private String memo;                // 바꿀 메모 내용을 불러와 저장할 String 변수 선언

    public CustomDialog(final Context context, final int position, MemoDatalist memoDataList){
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.customdialog);              // 수정을 실행할 Dialog창 연결
        memo = memoDataList.getMemo();                      // 바꿀 메모의 내용을 memo 변수에 저장
        mod_memo = findViewById(R.id.mod_memo);             // memo의 내용을 넣을 EditText와 연결
        mod_memo.setText(memo);                             // mod_memo에 memo 내용을 set
        mod_bt = findViewById(R.id.mod_bt);                 // 수정 버튼 Button과 연결

        /// **수정 버튼을 눌렀을 때의 이벤트** ////
        mod_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    String memo = mod_memo.getText().toString();    // 수정한 값을 memo에 저장한다.
                    MemoDatalist memoDataList=new MemoDatalist();   // 데이터베이스 참조
                    memoDataList.setMemo(memo);                     // 데이터베이스의 Memo에 수정된 데이터인 memo를 set
                    listener.onFinish(position, memoDataList);
                    Toast.makeText(context.getApplicationContext(),"일정이 수정되었습니다.",Toast.LENGTH_LONG).show();    //확인의 토스 메시지
                    dismiss();
                    Fragment1.mContext.Review();                     // 수정 된 후 리스트 화면에서 바뀐 정보 Review(라이브러리 함수 x)
                }
            }
        });
    }
    public void setDialogListener(OnDialogListener listener){
        this.listener = listener;
    }
}
