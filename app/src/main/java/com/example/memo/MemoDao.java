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

    //추가
    @Query("select * from memodatalist where Alarm = 1")    // 1 = o , 2 = x
    public List<MemoDatalist>getData();
    @Query ("update memodatalist set Alarm = 2 where id = :posid")  //1->2
    public void  checkData(int posid);
    @Query ("update memodatalist set Alarm = 1 where Alarm = 2 ")  //2->1
    public void  resetData();

}
