package com.example.kiinderlernapp.ui.colorGame

import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kiinderlernapp.adapter.ColorAdapter
import com.example.kiinderlernapp.databinding.FragmentColorBinding
import com.example.kiinderlernapp.ui.MainViewModel
import java.util.Locale

class ColorFragment : Fragment(),TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentColorBinding
    private val viewmodel: MainViewModel by activityViewModels()
    private lateinit var textToSpeech: TextToSpeech
    private val colors = listOf(
        "#FF1100",  // Rot
        "#08FF00",  // Grün
        "#0048FF",  // Blau
        "#FF000000",  // Schwarz
        "#FFFFFFFF",  // Weiß
        "#7700FF",  // Lila
        "#808080",  // Grau
        "#FF00D4",  // Pink
        "#FFFF00",  // Gelb
        "#FFA500",  // Orange
        "#964B00",  // Braun
        "#008080",  // Türkis
        "#00FFFF",  // Cyan
        "#9400D3",  // Violett
        "#FF00FF",  // Magenta
        "#FFD700", // Gold
        "#C0C0C0",// Silber
        "#800000",  // Maroon
        "#000080",  // Navy
    )

    private val descriptions = listOf(
        "Rot", "Grün", "Blau", "Schwarz", "Weiß", "Lila", "Grau",
        "Pink", "Gelb", "Orange", "Braun", "Türkis", "Cyan", "Violett", "Magenta",
        "Gold", "Silber", "Maroon", "Navy"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textToSpeech = TextToSpeech(requireContext(),this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentColorBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.textToSpeach.observe(viewLifecycleOwner) {
            textToSpeech.setPitch(listOf(0.8f,0.9f,1f,1.1f,1.2f).random())
            textToSpeech.setSpeechRate(listOf(0.8f,0.9f,1f,1.1f,1.2f).random())
            textToSpeech.speak(it, TextToSpeech.QUEUE_FLUSH, null, null)

        }

        binding.recyclerViewColor.adapter = ColorAdapter(colors,descriptions,viewmodel)
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.recyclerViewColor)

        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.GERMAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                textToSpeech.setLanguage(Locale.US)
            }
        }
    }
}