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
    private Context context;
    private Button mod_bt;
    EditText mod_memo;
    private String memo;

    public CustomDialog(final Context context, final int position, MemoDatalist memoDataList){
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.customdialog);

        memo = memoDataList.getMemo();


        //이름, 나이 EditText에 값 채우기
        mod_memo = findViewById(R.id.mod_memo);
        mod_memo.setText(memo);


        mod_bt = findViewById(R.id.mod_bt);
        mod_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener!=null){
                    //EditText의 수정된 값 가져오기
                    String memo = mod_memo.getText().toString();

                    MemoDatalist memoDataList=new MemoDatalist();
                    memoDataList.setMemo(memo);


                    //Listener를 통해서 person객체 전달
                    listener.onFinish(position, memoDataList);
                    //다이얼로그 종료
                    Toast.makeText(context.getApplicationContext(),"일정이 수정되었습니다.",Toast.LENGTH_LONG).show();
                    dismiss();
                }


            }
        });
    }
    public void setDialogListener(OnDialogListener listener){
        this.listener = listener;
    }

}
