import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.ui.tamagochi.TamagotchiBinder
import java.util.concurrent.TimeUnit

class TamagotchiService : Service() {

    companion object {
        private const val NOTIFICATION_ID = 1
    }

    private fun createNotification(): Notification {
        val channelId = "your_channel_id"
        val channelName = "Your Channel Name"

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Your Notification Title")
            .setContentText("Your Notification Text")
            .setSmallIcon(R.drawable.kopfsalat)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        return notificationBuilder.build()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TamagotchiService", "Service gestartet")

        // Füge diese Zeilen hinzu, um den Service im Vordergrund zu halten
        val notification = createNotification() // Erstelle die Benachrichtigung
        startForeground(NOTIFICATION_ID, notification)

        // Konfiguriere WorkManager, um die Worker-Aufgabe regelmäßig auszuführen
        val repeatInterval = 1L // Alle paar Sekunden
        val repeatIntervalTimeUnit = TimeUnit.SECONDS

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequest.Builder(
            TamagotchiWorker::class.java,
            repeatInterval,
            repeatIntervalTimeUnit
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "TamagotchiWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return TamagotchiBinder(this)
    }
}
