package com.example.memo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MemoDatalist {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Memo")      // Memo = 메모의 내용
    private String Memo;
    @ColumnInfo(name = "Alarm")     // Alarm = 확인 버튼의 여부
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
    public void setMemo(String Memo) { this.Memo = Memo; }
    public int getAlarm() { return Alarm; }
    public void setAlarm(int alarm) { Alarm = alarm; }
}
