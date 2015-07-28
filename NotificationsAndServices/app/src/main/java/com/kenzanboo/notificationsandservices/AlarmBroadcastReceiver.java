package com.kenzanboo.notificationsandservices;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by kenzanboo on 7/27/15.
 */
public class AlarmBroadcastReceiver extends WakefulBroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, NotifyService.class);
        startWakefulService(context, service);
    }
}
