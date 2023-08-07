package com.programacion.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.databinding.ActivityNotificationBinding
import com.programacion.dispositivosmoviles.ui.utilities.BroadcasterNotifications
import java.util.Calendar

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotification.setOnClickListener {
            //createNotificationChannel()
            sendNotification()
        }

        binding.btnNotificationProgramada.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hora = binding.timePicker.hour
            val minutes = binding.timePicker.minute

            Toast.makeText(
                this,
                "La notificacion se activara a las $hora con $minutes",
                Toast.LENGTH_SHORT
            ).show()
            calendar.set(Calendar.HOUR, hora)
            calendar.set(Calendar.MINUTE, minutes)
            calendar.set(Calendar.SECOND, 0)
            sendNotificationTimePicker(calendar.timeInMillis)
        }
    }

    private val CHANNEL: String = "Notificaciones"

    private fun createNotificationChannel(time: Long) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = "Aviso"
        val descriptionText = "Notificaciones simples de aviso"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @SuppressLint("MissingPermission")
    fun sendNotification() {
        val notifyIntent = Intent(this, CameraActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val noti = NotificationCompat.Builder(this, CHANNEL).apply {
            setContentIntent(notifyPendingIntent)
            setContentTitle("Primera Notificacion")
            setContentText("Esta es una notificacion para recordarte que estamos trabajando en android")
            setSmallIcon(R.drawable.baseline_message_24)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Esta es una notificacion larguisisisisisismaaaaaaaaaaaaaaaaaaaaaaa")
            )
        }

        with(NotificationManagerCompat.from(this)) {
            notify(1, noti.build())
        }
    }

    private fun sendNotificationTimePicker(time: Long) {
        val myIntent = Intent(applicationContext, BroadcasterNotifications::class.java)
        val myPendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0, myIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            time,
            myPendingIntent
        ) //Nos ayuda a que el sistema se levante

    }
}