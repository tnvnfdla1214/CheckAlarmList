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
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Main extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment1 fragment1;
    Fragment2 fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        /*SharedPreferences sharedPreferencesAM = getSharedPreferences("daily alarm AM", MODE_PRIVATE);
        SharedPreferences sharedPreferencesPM = getSharedPreferences("daily alarm PM", MODE_PRIVATE);
        long AM = sharedPreferencesAM.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());
        long PM = sharedPreferencesPM.getLong("nextNotifyTime2", Calendar.getInstance().getTimeInMillis());

        Calendar nextNotifyTimeAM = new GregorianCalendar();
        Calendar nextNotifyTimePM = new GregorianCalendar();

        nextNotifyTimeAM.setTimeInMillis(AM);
        nextNotifyTimePM.setTimeInMillis(PM);*/


        SharedPreferences sharedPreferencesAM = getSharedPreferences("daily alarm", MODE_PRIVATE);
        long AM = sharedPreferencesAM.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());
        Calendar nextNotifyTimeAM = new GregorianCalendar();
        nextNotifyTimeAM.setTimeInMillis(AM);

        //  Preference에 설정한 값 저장
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 1);
        calendar1.set(Calendar.MINUTE, 58);
        calendar1.set(Calendar.SECOND, 00);


        if (calendar1.before(Calendar.getInstance())) {
            calendar1.add(Calendar.DATE, 1);
            
        }

        SharedPreferences.Editor editorAM = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
        editorAM.putLong("nextNotifyTime", (long) calendar1.getTimeInMillis());
        editorAM.apply();
        diaryNotification(calendar1);


        /*SharedPreferences sharedPreferencesPM = getSharedPreferences("daily alarm2", MODE_PRIVATE);
        long PM = sharedPreferencesPM.getLong("nextNotifyTime2", Calendar.getInstance().getTimeInMillis());
        Calendar nextNotifyTimePM = new GregorianCalendar();
        nextNotifyTimePM.setTimeInMillis(PM);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 1);
        calendar2.set(Calendar.MINUTE, 37);
        calendar2.set(Calendar.SECOND, 00);

        if (calendar2.before(Calendar.getInstance())) {
            calendar2.add(Calendar.HOUR_OF_DAY,12 );
        }

        SharedPreferences.Editor editorPM = getSharedPreferences("daily alarm2", MODE_PRIVATE).edit();
        editorPM.putLong("nextNotifyTime2", (long) calendar2.getTimeInMillis());
        editorPM.apply();
        diaryNotification2(calendar2);*/





//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // 이전 설정값으로 TimePicker 초기화
        Date currentTimeAM = nextNotifyTimeAM.getTime();
        SimpleDateFormat HourFormatAM = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormatAM = new SimpleDateFormat("mm", Locale.getDefault());

        /*Date currentTimePM = nextNotifyTimePM.getTime();*/
        SimpleDateFormat HourFormatPM = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormatPM = new SimpleDateFormat("mm", Locale.getDefault());


        int pre_hourAM = Integer.parseInt(HourFormatAM.format(currentTimeAM));
        int pre_minuteAM = Integer.parseInt(MinuteFormatAM.format(currentTimeAM));

        /*int pre_hourPM = Integer.parseInt(HourFormatPM.format(currentTimePM));
        int pre_minutePM = Integer.parseInt(MinuteFormatPM.format(currentTimePM));*/


        /*Date currentDateTimeAM = calendar1.getTime();
        String date_textAM = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTimeAM);
        Toast.makeText(getApplicationContext(), date_textAM + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();

        Date currentDateTimePM = calendar2.getTime();
        String date_textPM = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTimePM);
        Toast.makeText(getApplicationContext(), date_textPM + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();*/


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        /*//  Preference에 설정한 값 저장
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 1);
        calendar1.set(Calendar.MINUTE, 29);
        calendar1.set(Calendar.SECOND, 00);

        if (calendar1.before(Calendar.getInstance())) {
            calendar1.add(Calendar.DATE, 1);
        }

        SharedPreferences.Editor editorAM = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
        editorAM.putLong("nextNotifyTime", (long) calendar1.getTimeInMillis());
        editorAM.apply();
        diaryNotification(calendar1);


        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 1);
        calendar2.set(Calendar.MINUTE, 30);
        calendar2.set(Calendar.SECOND, 00);

        if (calendar2.before(Calendar.getInstance())) {
            calendar2.add(Calendar.DATE, 1);
        }

        SharedPreferences.Editor editorPM = getSharedPreferences("daily alarm2", MODE_PRIVATE).edit();
        editorPM.putLong("nextNotifyTime2", (long) calendar2.getTimeInMillis());
        editorPM.apply();
        diaryNotification2(calendar2);*/








//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //프래그먼트 생성
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        //제일 처음 띄워줄 뷰를 세팅해줍니다. commit();까지 해줘야 합니다.
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();

        //bottomnavigationview의 아이콘을 선택 했을때 원하는 프래그먼트가 띄워질 수 있도록 리스너를 추가합니다.
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1:{
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment1).commitAllowingStateLoss();
//                            startActivity(new Intent(Main.this,MainActivity.class));
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
    void diaryNotification2(Calendar calendar)
    {

        Boolean dailyNotify2 = true; // 무조건 알람을 사용

        PackageManager pm2 = this.getPackageManager();
        ComponentName receiver2 = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent2 = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 0, alarmIntent2, 0);
        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify2) {


            if (alarmManager2 != null) {

                alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent2);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager2.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent2);
                }
            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm2.setComponentEnabledSetting(receiver2,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }

    }


}
