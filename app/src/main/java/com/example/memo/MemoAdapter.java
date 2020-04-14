package com.example.memo;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MemoAdapter  extends RecyclerView.Adapter<MemoAdapter.ViewHolder> implements ItemTouchHelperListener,OnDialogListener{
    List<MemoDatalist> memoDataLists;
    Context context;

    public MemoAdapter(List<MemoDatalist> myDataLists,Context context) {
        this.memoDataLists = myDataLists;
        this.context = context;
    }

    @NonNull
    @Override
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
        //Log.v("태그","메세지2");
    }

    @Override
    public void onLeftClick(int position, RecyclerView.ViewHolder viewHolder) {

        //수정 버튼 클릭시 다이얼로그 생성
        //CustomDialog dialog = new CustomDialog(context, position, items.get(position));
        CustomDialog dialog = new CustomDialog(context, position, memoDataLists.get(position));

        //화면 사이즈 구하기
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //다이얼로그 사이즈 세팅
        WindowManager.LayoutParams wm = dialog.getWindow().getAttributes();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = (int) (width * 0.7);
        wm.height = height/2;
        //다이얼로그 Listener 세팅
        dialog.setDialogListener(this);
        //다이얼로그 띄우기
        dialog.show();
        //Log.v("태그","메세지3");
    }

    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {


        int ID = memoDataLists.get(position).getId();
        Fragment1.memoDatabase.memoDao().checkData(ID);

        Toast.makeText(context.getApplicationContext(),"일정이 확인되었습니다.",Toast.LENGTH_LONG).show();

        memoDataLists.remove(position);
        notifyItemRemoved(position);
        //Log.v("태그","메세지4");
    }


    @Override
    public void onFinish(int position,MemoDatalist myDatalists) {

        String getmemo = myDatalists.getMemo();

        int ID = memoDataLists.get(position).getId();
        Fragment1.memoDatabase.memoDao().updatememo(getmemo,ID);


        memoDataLists.set(position,myDatalists);
        notifyItemChanged(position);
        //Log.v("태그","메세지5");
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
