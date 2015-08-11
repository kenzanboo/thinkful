package com.kenzanboo.notificationsandservices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by kenzanboo on 7/21/15.
 */
public class Notifier {
    protected Context context;
    private static int notificationID = 100;

    public Notifier(Context context) {
        this.context = context;
    }


    protected void createNotification() {
        //Build Notification
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(
                context);
        nBuilder.setContentTitle("Notification");
        nBuilder.setContentText("This is a Notification");
        nBuilder.setSmallIcon(R.mipmap.ic_launcher);
        nBuilder.setContentIntent(getMainActivityPendingIntent());
        nBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationID = notificationID + 1;
        mNotificationManager.notify(notificationID, nBuilder.build());

    }
    protected PendingIntent getMainActivityPendingIntent() {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1234, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return(pendingIntent);
    }

}
