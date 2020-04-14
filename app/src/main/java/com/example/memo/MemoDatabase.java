package com.example.memo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={MemoDatalist.class},version = 1)
public abstract class MemoDatabase extends RoomDatabase {
    public abstract MemoDao memoDao();
}
