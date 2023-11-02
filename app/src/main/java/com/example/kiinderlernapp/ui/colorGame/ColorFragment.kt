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

class ColorFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentColorBinding
    private val viewmodel: MainViewModel by activityViewModels()
    private lateinit var textToSpeech: TextToSpeech
    private val colors = listOf(
        "#FF1100",  // Rot
        "#08FF00",  // Grün
        "#0048FF",  // Blau
        "#FF000000",  // Schwarz
        "#FFFFFFFF",  // Weiß
        // Weitere Farben
    )

    private val descriptions = listOf(
        "Rot", "Grün", "Blau", "Schwarz", "Weiß", // Beschreibungen für die Farben
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textToSpeech = TextToSpeech(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentColorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Beobachten der LiveData aus dem ViewModel
        viewmodel.textToSpeach.observe(viewLifecycleOwner) {
            // Einstellen von Tonhöhe und Sprechrate auf zufällige Werte
            textToSpeech.setPitch(listOf(0.8f, 0.9f, 1f, 1.1f, 1.2f).random())
            textToSpeech.setSpeechRate(listOf(0.8f, 0.9f, 1f, 1.1f, 1.2f).random())
            // Text-to-Speech abspielen
            textToSpeech.speak(it, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        // Adapter für die RecyclerView mit den Farben und Beschreibungen
        binding.recyclerViewColor.adapter = ColorAdapter(colors, descriptions, viewmodel)
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.recyclerViewColor)

        // Klick-Ereignis für die Zurück-Schaltfläche
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Wenn die Initialisierung erfolgreich ist, setzen Sie die Sprache auf Deutsch (Locale.GERMAN)
            val result = textToSpeech.setLanguage(Locale.GERMAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Wenn die gewünschte Sprache nicht verfügbar ist, fällt es auf die Standardsprache (Locale.US) zurück
                textToSpeech.setLanguage(Locale.US)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        // Text-to-Speech beenden, wenn das Fragment zerstört wird
        textToSpeech.shutdown()
    }
}
