package com.example.memo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver2 extends BroadcastReceiver {
    List<MemoDatalist> memoDataLists;
    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationManager notificationManager2 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent2 = new Intent(context, Main.class);

        notificationIntent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingI2 = PendingIntent.getActivity(context, 0,
                notificationIntent2, 0);


        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(context, "default");


        //OREO API 26 이상에서는 채널 필요
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            builder2.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남


            String channelName ="매일 알람 채널";
            String description = "매일 정해진 시간에 알람합니다.";
            int importance2 = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

            NotificationChannel channel2 = new NotificationChannel("default", channelName, importance2);
            channel2.setDescription(description);

            if (notificationManager2 != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationManager2.createNotificationChannel(channel2);
            }
        }else builder2.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남


        builder2.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())


                .setTicker("{Time to watch some cool stuff!}")
                .setContentTitle("2알람이 리셋되었습니다.")
                .setContentText("2알람이 리셋되었습니다.")
                .setContentInfo("INFO")
                .setContentIntent(pendingI2);

        if (notificationManager2 != null) {

            // 노티피케이션 동작시킴
            notificationManager2.notify(1234, builder2.build());

            Calendar nextNotifyTime2 = Calendar.getInstance();

            // 내일 같은 시간으로 알람시간 결정
            nextNotifyTime2.add(Calendar.DATE, 1);

            //  Preference에 설정한 값 저장
            SharedPreferences.Editor editor2 = context.getSharedPreferences("daily alarm2", MODE_PRIVATE).edit();
            editor2.putLong("nextNotifyTime2", nextNotifyTime2.getTimeInMillis());
            editor2.apply();


            Fragment1.memoDatabase.memoDao().resetPm();
            (Fragment1.mContext).Review();

        }
    }
}