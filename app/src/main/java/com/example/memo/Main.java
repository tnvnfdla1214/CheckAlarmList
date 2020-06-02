package com.example.memo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.google.gson.internal.bind.util.ISO8601Utils.format;

public class Main extends AppCompatActivity {
    public static Calendar calendar1 = Calendar.getInstance();
    BottomNavigationView bottomNavigationView;
    Fragment1 fragment1;
    Fragment2 fragment2;
    public static Context mContext;
    private Intent mIntent;
    long now;           // 현재 시간을 구한다.
    Date date ;         // 현재 시간을 date 변수에 저장한다.

    SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm:ss");  // 시간을 나타낼 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    String formatDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        mContext = this;

        calendar1.set(Calendar.HOUR_OF_DAY, 04);        // 자정으로 시간 지정 //
        calendar1.set(Calendar.MINUTE, 42);
        calendar1.set(Calendar.SECOND, 00);

        if (calendar1.before(Calendar.getInstance())) {     // 시간이 지났다면 다음날 같은시간으로 지정
            mIntent = new Intent(getApplicationContext(), MyService.class);
            startService(mIntent);
            calendar1.add(Calendar.DATE, 1);
        }
        ShowTimeMethod();
        SharedPreferences.Editor editorAM = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
        editorAM.putLong("nextNotifyTime", (long) calendar1.getTimeInMillis());
        editorAM.apply();

        diaryNotification(calendar1);

        //프래그먼트 생성
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        //fragment1를 제일 처음 띄워줄 뷰로 setting
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();

        //bottomnavigationview를 이용하여 하단바의 아이콘을 터치하면 해당 레이아웃으로 이동
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.tab1:{
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment1).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.tab2:{
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment2).commitAllowingStateLoss();
                        return true;
                    }
                    default: return false;
                }
            }
        });
    }
    ////** 알람 리시버가 동작하여 팝업을 실행시키는 메서드 **////
    boolean diaryNotification(Calendar calendar)
    {
        Boolean dailyNotify = true; // 무조건 알람을 사용
        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (dailyNotify) {
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        }
        return false;
    }

    ////** 프로그램이 실행 중이지 않아도, 데이터의 리셋 실행 **////
    public void ShowTimeMethod(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                format(new Date());
                now=System.currentTimeMillis();
                date = new Date(now);
                formatDate = sdfNow.format(date);
                if(formatDate.equals("04:42:00")){               // 자정으로 시간 지정 //
                    Fragment1.mContext.memoDatabase.memoDao().resetData();
                    Fragment1.mContext.Review();
                }
            }
        };
        Runnable task = new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){}
                    handler.sendEmptyMessage(1);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}
