package com.kenzanboo.notificationsandservices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;

/**
 * Created by kenzanboo on 7/21/15.
 */
public class Alarm {
    private int ALARM_TIMER = 3;
    protected Context context;
    public Alarm(Context context) {
        this.context = context;
    }

    protected void setAlarm() {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmMgr.set(AlarmManager.ELAPSED_REALTIME,
//                SystemClock.elapsedRealtime() + ALARM_TIMER * 1000,
//                getMainActivityPendingIntent());
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + ALARM_TIMER * 1000,
                ALARM_TIMER * 1000,
                getBroadcastActivityPendingIntent());

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 16);
//        calendar.set(Calendar.MINUTE, 25);
//        long milliseconds = calendar.getTimeInMillis();
//        alarmMgr.setInexactRepeating(AlarmManager.RTC,
//                milliseconds,
//                AlarmManager.INTERVAL_DAY,
//                getMainActivityPendingIntent());

        ComponentName bootReceiver = new ComponentName(context, BootReceiver.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(bootReceiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
    protected void cancelAlarm() {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(getBroadcastActivityPendingIntent());

        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
    protected PendingIntent getMainActivityPendingIntent() {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return(pendingIntent);
    }

    protected PendingIntent getBroadcastActivityPendingIntent() {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        return(pendingIntent);
    }
}
