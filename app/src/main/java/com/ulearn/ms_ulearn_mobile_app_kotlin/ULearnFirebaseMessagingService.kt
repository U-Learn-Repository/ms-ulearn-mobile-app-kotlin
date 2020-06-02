package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ULearnFirebaseMessagingService : FirebaseMessagingService()  {
    val TAG = "#########"

    override fun onNewToken(token: String) {
        Log.d("New_Token", token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "Poruka: ${remoteMessage.from}")

        if (remoteMessage.notification != null) {
            Log.d(TAG, "Sadrzaj: ${remoteMessage.notification?.body}")

            sendNotification(remoteMessage.notification?.title.toString(), remoteMessage.notification?.body.toString())
        }
    }

    fun sendNotification(title: String, body: String) {

        var CHANNEL_ID = "com.chikeandroid.tutsplustalerts.ANDROID";

        // Create an explicit intent for an Activity in your app
        val intent = Intent(this, Login::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.education)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, builder.build())
    }

}