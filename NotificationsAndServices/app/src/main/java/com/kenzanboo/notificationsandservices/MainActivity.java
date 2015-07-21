package com.kenzanboo.notificationsandservices;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {
    private int notificationID = 100;
    private int ALARM_TIMER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtons();
    }

    private void initializeButtons() {
        Button notificationButton = (Button) findViewById(R.id.notify);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNotification();
            }
        });
        Button alarmButton = (Button) findViewById(R.id.setAlarm);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });
        Button cancelAlarmButton = (Button) findViewById(R.id.cancelAlarm);
        cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }

    protected void setAlarm() {
        AlarmManager alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
//        alarmMgr.set(AlarmManager.ELAPSED_REALTIME,
//                SystemClock.elapsedRealtime() + ALARM_TIMER * 1000,
//                getMainActivityPendingIntent());
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + ALARM_TIMER * 1000,
                ALARM_TIMER * 1000,
                getMainActivityPendingIntent());

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 16);
//        calendar.set(Calendar.MINUTE, 25);
//        long milliseconds = calendar.getTimeInMillis();
//        alarmMgr.setInexactRepeating(AlarmManager.RTC,
//                milliseconds,
//                AlarmManager.INTERVAL_DAY,
//                getMainActivityPendingIntent());
    }
    protected void cancelAlarm() {
        AlarmManager alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(getMainActivityPendingIntent());    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void displayNotification() {
        //Build Notification
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(
                this);
        nBuilder.setContentTitle("Notification");
        nBuilder.setContentText("This is a Notification");
        nBuilder.setSmallIcon(R.mipmap.ic_launcher);
        nBuilder.setContentIntent(getMainActivityPendingIntent());
        nBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationID, nBuilder.build());

    }
    protected PendingIntent getMainActivityPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1234, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return(pendingIntent);
    }
}


