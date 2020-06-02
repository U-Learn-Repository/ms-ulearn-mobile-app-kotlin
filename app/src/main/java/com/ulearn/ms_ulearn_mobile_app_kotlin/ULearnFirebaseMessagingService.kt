package com.ulearn.ms_ulearn_mobile_app_kotlin

import android.util.Log
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
            // sendNotification(remoteMessage)
        }
    }

}