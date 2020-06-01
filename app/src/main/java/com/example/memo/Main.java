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
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.google.gson.internal.bind.util.ISO8601Utils.format;

public class Main extends AppCompatActivity {
    public static Calendar calendar1 = Calendar.getInstance();
    BottomNavigationView bottomNavigationView;
    Fragment1 fragment1;
    Fragment2 fragment2;
    public static Context mContext;

    private Intent mIntent;


    // 현재시간을 msec 으로 구한다.
    long now;
    // 현재시간을 date 변수에 저장한다.
    Date date ;

    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm:ss");
    // nowDate 변수에 값을 저장한다.
    String formatDate;





    //public static Calendar calendar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        mContext = this;

        calendar1.set(Calendar.HOUR_OF_DAY, 06);
        calendar1.set(Calendar.MINUTE, 39);
        calendar1.set(Calendar.SECOND, 00);

        if (calendar1.before(Calendar.getInstance())) {

            mIntent=  new Intent(getApplicationContext(), MyService.class);
            startService(mIntent);
            //stopService(mIntent);
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

        //제일 처음 띄워줄 뷰를 세팅해줍니다. commit();까지 해줘야 합니다.
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
        Log.d("asd","asd100");


        //bottomnavigationview의 아이콘을 선택 했을때 원하는 프래그먼트가 띄워질 수 있도록 리스너를 추가합니다.
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
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

    boolean diaryNotification(Calendar calendar)
    {

        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {



            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                }
            }
            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        }
        return false;
    }

    public void ShowTimeMethod(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                format(new Date());
                now=System.currentTimeMillis();
                date = new Date(now);
                formatDate = sdfNow.format(date);

                if(formatDate.equals("06:43:00")){
                    Log.d("태그","들어와라");
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
