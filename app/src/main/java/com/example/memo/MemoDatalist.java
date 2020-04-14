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
    @ColumnInfo(name = "Time")
    private long Time;
    @ColumnInfo(name = "Alarm")
    private int Alarm;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMemo() {
        return Memo;
    }
    public void setMemo(String Memo) {
        this.Memo = Memo;
    }
    public long getTime() {
        return Time;
    }
    public void setTime(long Time) {
        this.Time = Time;
    }

    public int getAlarm() { return Alarm; }
    public void setAlarm(int alarm) { Alarm = alarm; }
}
