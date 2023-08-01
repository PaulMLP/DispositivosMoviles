package com.programacion.dispositivosmoviles.ui.utilities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.ui.activities.CameraActivity

class BroadcasterNotifications : BroadcastReceiver() {
    private val CHANNEL: String = "Notificaciones"

    override fun onReceive(context: Context, intent: Intent) {
        val myIntent = Intent(context, CameraActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, myIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val noti = NotificationCompat.Builder(context, CHANNEL)
        noti.setContentIntent(pendingIntent)
        noti.setContentTitle("Primera Notificacion")
        noti.setContentText("Notificacion Programada")
        noti.setSmallIcon(R.drawable.baseline_message_24)
        noti.priority = NotificationCompat.PRIORITY_DEFAULT
        noti.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Esta es una notificacion programada para recordar que estamos trabajando")
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, noti.build())
    }
}