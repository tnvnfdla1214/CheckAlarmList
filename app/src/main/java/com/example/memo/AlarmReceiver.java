package com.example.memo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.core.app.NotificationCompat;
import java.util.Calendar;
import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, Main.class);                                            // 팝업 클릭시 Main 클래스로 이동
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingI = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");      // 팝업 정보 Builder

        //팝업을 사용할 시 OREO API 26 이상에서는 채널이 필요 그외는 바로 아이콘과 함께 팝업을 띄운다.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground);        /// mipmap의 아이콘 사용시 Oreo 이상에서 시스템 UI 에러남
            String channelName ="매일 알람 채널";                            // 채널 정보 입력
            String description = "매일 정해진 시간에 알람합니다.";
            int importance = NotificationManager.IMPORTANCE_HIGH;           // 소리와 알림메시지를 같이 보여줌
            NotificationChannel channel = new NotificationChannel("default", channelName, importance);      // 채널 선언
            channel.setDescription(description);
            if (notificationManager != null) { notificationManager.createNotificationChannel(channel);}         //노티피케이션 채널을 시스템에 등록
        }else builder.setSmallIcon(R.mipmap.ic_launcher);                   /// Oreo 이하에서 mipmap 사용하지 않으면 에러남

        builder.setAutoCancel(true)         // 팝업을 생성하고 아래는 팝업의 정보를 포함
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("{Time to watch some cool stuff!}")
                .setContentTitle("알람이 리셋되었습니다.")
                .setContentText("일정을 확인하거나 새로운 일정을 추가하세요!")
                .setContentIntent(pendingI);

        if (notificationManager != null) {

            notificationManager.notify(1234, builder.build());
            Calendar nextNotifyTime = Calendar.getInstance();
            nextNotifyTime.add(Calendar.DATE, 1);
            SharedPreferences.Editor editor = context.getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
            editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis());
            editor.apply();

        }
    }
}