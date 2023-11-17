package com.example.kiinderlernapp

import android.Manifest
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.example.kiinderlernapp.ui.MainViewModel
import com.example.kiinderlernapp.utils.NotificationService

class MainActivity : AppCompatActivity() {

    private val notifi = NotificationService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        notifi.setAppRunningStatus(true)
        notifi.setOpenStatus(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        notifi.setAppRunningStatus(false)
        notifi.setOpenStatus(false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            9000 -> {
                // Überprüfen, ob die Berechtigung erteilt wurde
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Berechtigung wurde erteilt, zeige die Benachrichtigung
                    notifi.showNotification()
                } else {
                    // Berechtigung wurde verweigert, informiere den Benutzer oder ergreife andere Maßnahmen
                    Toast.makeText(this,"Berrechtigung Verweigert", Toast.LENGTH_SHORT).show()
                }
            }
            // Andere Berechtigungsanfragen hier verarbeiten, falls notwendig
        }
    }
}
