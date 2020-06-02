package com.example.memo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MemoDao  {
    @Insert
    void addData(MemoDatalist memodatalist);
    @Update
    public void update(MemoDatalist memodatalist);
    @Query("select * from memodatalist where id = :id")
    List<MemoDatalist> load(int id);
    @Query("select * from memodatalist")
    public List<MemoDatalist> getMemoData();
    @Query ("delete from memodatalist where id = :id")
    public void  deleteData(int id);
    @Query ("update memodatalist set memo = :setmemo where id = :posid")
    public void  updatememo(String setmemo, int posid);

    //Alarm이 1인 메모를 Select. (확인을 누르지 않은)
    @Query("select * from memodatalist where Alarm = 1")    // 1 = o , 2 = x
    public List<MemoDatalist>getData();
    //해당 id의 Alarm을 2로 update. (확인을 누르는 행위)
    @Query ("update memodatalist set Alarm = 2 where id = :posid")  //1->2
    public void  checkData(int posid);
    //Alarm이 2인 것을 전부 1로 update. (알람이 울려서 다시 전부 표시하기 위해 Alarm 값 초기화)
    @Query ("update memodatalist set Alarm = 1 where Alarm = 2 ")  //2->1
    public void  resetData();

}
