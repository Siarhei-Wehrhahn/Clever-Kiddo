package com.example.kiinderlernapp.utils

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.kiinderlernapp.MainActivity
import com.example.kiinderlernapp.R

class NotificationService(
    var activity: Activity
) : Service() {

    private var isRunning: Boolean = false
    private var isOpen: Boolean = false
    private val notificationId = 1
    private val chanelId: String = "TamagotchhiChanel"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (isRunning && isOpen) {
            Handler(Looper.getMainLooper()).postDelayed({ showNotification() }, 1000)
            showNotification()
        }
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    fun showNotification() {
        createNotificationChanel()
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, chanelId)
            .setContentTitle("Tamagotchi Info")
            .setContentText("Dein Tamagotchi braucht dich!")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 9000)
            return
        }
        notificationManager.notify(notificationId, notification)
    }

    private fun createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Tamagotchi Channel"
            val descriptionText = "Kanal für Tamagotchi-Benachrichtigungen"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(chanelId, name, importance).apply {
                description = descriptionText
                enableLights(true)
                lightColor = Color.RED
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun setAppRunningStatus(running: Boolean) {
        isRunning = running
    }

    fun setOpenStatus(open: Boolean) {
        isOpen = open
    }
}
