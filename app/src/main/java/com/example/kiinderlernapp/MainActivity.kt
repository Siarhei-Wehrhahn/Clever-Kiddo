package com.example.kiinderlernapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.viewModels
import com.example.kiinderlernapp.ui.MainViewModel

class MainActivity : AppCompatActivity(){

    private val viewModel: MainViewModel by viewModels()
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}