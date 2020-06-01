package com.example.memo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.d("StartService","onBind()");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("StartService","onCreate()");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Fragment1.mContext.memoDatabase.memoDao().resetData();
        Fragment1.mContext.Review();
        Log.d("태그","서비스 시작");

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("태그", "서비스 종료");

    }
}
