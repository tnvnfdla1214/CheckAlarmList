package com.example.memo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class DeviceBootReceiver2 extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {

            // on device boot complete, reset the alarm
            Intent alarmIntent2 = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, alarmIntent2, 0);

            AlarmManager manager2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//

            SharedPreferences sharedPreferences2 = context.getSharedPreferences("daily alarm2", MODE_PRIVATE);
            long millis = sharedPreferences2.getLong("nextNotifyTime2", Calendar.getInstance().getTimeInMillis());


            Calendar current_calendar2 = Calendar.getInstance();
            Calendar nextNotifyTime2 = new GregorianCalendar();
            nextNotifyTime2.setTimeInMillis(sharedPreferences2.getLong("nextNotifyTime2", millis));

            if (current_calendar2.after(nextNotifyTime2)) {
                nextNotifyTime2.add(Calendar.DATE, 1);
            }

            Date currentDateTime2 = nextNotifyTime2.getTime();
            String date_text2 = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime2);
            Toast.makeText(context.getApplicationContext(),"[재부팅후] 다음 알람은 " + date_text2 + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();


            if (manager2 != null) {
                manager2.setRepeating(AlarmManager.RTC_WAKEUP, nextNotifyTime2.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent2);
            }
        }
    }
}
