package com.example.memo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MemoDatalist {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Memo")
    private String Memo;
    @ColumnInfo(name = "Alarm")
    private int Alarm;
    @ColumnInfo(name = "AM_PM")
    private String AM_PM;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMemo() {
        return Memo;
    }
    public void setMemo(String Memo) { this.Memo = Memo; }
    public int getAlarm() { return Alarm; }
    public void setAlarm(int alarm) { Alarm = alarm; }

    public String getAM_PM() {return AM_PM; }
    public void setAM_PM(String AM_PM) {this.AM_PM = AM_PM;}
}
