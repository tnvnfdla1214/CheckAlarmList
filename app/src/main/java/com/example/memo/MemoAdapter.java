package com.example.memo;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import static com.example.memo.Fragment1.memoDatabase;

public class MemoAdapter  extends RecyclerView.Adapter<MemoAdapter.ViewHolder> implements ItemTouchHelperListener,OnDialogListener{
    List<MemoDatalist> memoDataLists;
    Context context;

    public MemoAdapter(List<MemoDatalist> myDataLists,Context context) {
        this.memoDataLists = myDataLists;
        this.context = context;
    }

    @NonNull
    @Override
    // 데이터를 담기 위한 list_data 연결
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_data,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MemoDatalist md=memoDataLists.get(position);
        viewHolder.txtmemo.setText(md.getMemo());
        viewHolder.onBind(memoDataLists.get(position));
    }

    @Override
    public int getItemCount() {
        return memoDataLists.size();
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        //이동할 객체 저장
        MemoDatalist memoDatalist = memoDataLists.get(from_position);
        //이동할 객체 삭제
        memoDataLists.remove(from_position);
        //이동하고 싶은 position에 추가
        //items.add(to_position,person);
        memoDataLists.add(to_position,memoDatalist);
        //Adapter에 데이터 이동알림
        notifyItemMoved(from_position,to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        memoDataLists.remove(position);
        notifyItemRemoved(position);
    }

    // 왼쪽 스와이프 클릭시
    @Override
    public void onLeftClick(int position, RecyclerView.ViewHolder viewHolder) {

        memoDataLists=memoDatabase.memoDao().getData();

        CustomDialog dialog = new CustomDialog(context, position, memoDataLists.get(position));     // 수정 버튼 클릭시 다이얼로그 생성
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();     // 화면 사이즈
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        WindowManager.LayoutParams wm = dialog.getWindow().getAttributes();                         // 다이얼로그 사이즈
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = (int) (width * 0.7);
        wm.height = height/2;

        dialog.setDialogListener(this);                 //다이얼로그 Listener 세팅
        dialog.show();                                  //다이얼로그 띄우기
    }

    // 오른쪽 스와이프 클릭시
    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {
        memoDataLists=memoDatabase.memoDao().getData();
        int ID = memoDataLists.get(position).getId();       // position으로 ID를 get
        memoDatabase.memoDao().checkData(ID);               // 해당 ID의 메모 데이터를 check (Alarm 1 --> 2)
        Fragment1.mContext.Review();                        // Review 실행
        Toast.makeText(context.getApplicationContext(),"일정이 확인되었습니다.",Toast.LENGTH_LONG).show();
        memoDataLists.remove(position);                     // 해당 position의 리스트의 데이터는 삭제한다.
        notifyItemRemoved(position);
    }

    @Override
    public void onFinish(int position,MemoDatalist myDatalists) {
        String getmemo = myDatalists.getMemo();             // 수정하려는 메모를 get
        int ID = memoDataLists.get(position).getId();       // 수정하려는 위치의 position 으로 Id를 get
        memoDatabase.memoDao().updatememo(getmemo,ID);      // 수정하려는 메모로 update
        memoDataLists.set(position,myDatalists);            // RecyclerView와 연결되어 표현하기 위한 memoDataLists에도 적용
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtmemo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmemo=(TextView)itemView.findViewById(R.id.txt_memo);
        }
        public void onBind(MemoDatalist memoDataLists) {
            txtmemo.setText(memoDataLists.getMemo());
        }
    }
}