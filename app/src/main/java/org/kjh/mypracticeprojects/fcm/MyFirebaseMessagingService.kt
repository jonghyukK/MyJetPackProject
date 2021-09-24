package org.kjh.mypracticeprojects.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.orhanobut.logger.Logger
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.ui.main.MainActivity

/**
 * MyPracticeProjects
 * Class: MyFirebaseMessagingService
 * Created by mac on 2021/09/17.
 *
 * Description:
 */
class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        Logger.d("""
            data : ${p0.notification?.body}
            data : ${p0.notification?.title}
        """.trimIndent())

        p0.notification?.let {
            sendNotification(it.title!!, it.body!!)
        }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    private fun sendNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = getActivity(this, 0, intent, FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_fcm_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_background)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_home))
            setContentTitle(title)
            setContentText(body)
            setAutoCancel(true)
            setSound(defaultSoundUri)
            setContentIntent(pendingIntent)
            setDefaults(Notification.DEFAULT_ALL)
            priority = PRIORITY_HIGH
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}