package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ULearnFirebaseMessagingService : FirebaseMessagingService()  {
    val TAG = "MessageFirebase";

    val self = this;

    override fun onNewToken(token: String) {
        Log.d("New_Token", token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Title: ${remoteMessage?.notification?.title}")
            Log.d(TAG, "Body: ${remoteMessage?.notification?.body}")
            send(remoteMessage.notification?.title.toString(), remoteMessage.notification?.body.toString())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "all_notifications" // You should create a String resource for this instead of storing in a variable
            val mChannel = NotificationChannel(
                channelId,
                "General Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mChannel.description = "This is default channel used for all other notifications"

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    private fun send(title: String, body: String) {

        createNotificationChannel()
        val channelId = "all_notifications" // Use same Channel ID

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        val builder =
            NotificationCompat.Builder(this, channelId) // Create notification with channel Id
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_MAX)

        builder.setContentIntent(pendingIntent).setAutoCancel(true)

        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        with(mNotificationManager) {
            notify(123, builder.build())
        }
    }
}