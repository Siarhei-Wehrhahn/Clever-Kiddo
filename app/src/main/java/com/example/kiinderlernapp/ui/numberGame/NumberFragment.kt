package com.example.kiinderlernapp.ui.numberGame

import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.databinding.FragmentNumberBinding
import com.example.kiinderlernapp.ui.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.util.Locale

class NumberFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentNumberBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var randomAnswereList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).shuffled()
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textToSpeech = TextToSpeech(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mMediaPlayer: MediaPlayer? = null

        // Verzögerung, um sicherzustellen, dass die Text-to-Speech-Engine geladen ist
        lifecycleScope.launch {
            delay(500)
            viewModel.textToSpeach.observe(viewLifecycleOwner) {
                // Zufällige Tonhöhe und Sprachrate einstellen
                textToSpeech.setPitch(listOf(0.8f, 0.9f, 1f, 1.1f, 1.2f).random())
                textToSpeech.setSpeechRate(listOf(0.8f, 0.9f, 1f, 1.1f, 1.2f).random())
                textToSpeech.speak(it, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        // Zufällige Nummer auswählen
        var randomNumber = (1..10).random()

        // Text-to-Speech-Ansage für die ausgewählte Nummer
        viewModel.textToSpeach("Drücke auf die $randomNumber")
        binding.textQuestion.text = "Drücke auf die $randomNumber"

        // Eine Liste von Antwortbuttons erstellen
        var answerButtons = listOf(
            binding.text1,
            binding.text2,
            binding.text3,
            binding.text4,
            binding.text5,
            binding.text6,
            binding.text7,
            binding.text8,
            binding.text9,
            binding.text10
        )

        // Antwortbuttons mit zufälligen Nummern füllen
        randomAnswereList = randomAnswereList.shuffled()
        repeat(answerButtons.size - 1) {
            answerButtons[it].text = randomAnswereList[it].toString()
            answerButtons[it].typeface = Typeface.create(
                ResourcesCompat.getFont(
                    requireContext(),
                    listOf(
                        R.font.allan,
                        R.font.almendra,
                        R.font.aladin,
                        R.font.courgette,
                        R.font.architects_daughter,
                        R.font.della_respira,
                        R.font.advent_pro_thin
                    ).random()
                ), Typeface.NORMAL
            )
        }

        // Antwortbuttons konfigurieren
        answerButtons.forEachIndexed { index, button ->
            button.text = randomAnswereList[index].toString()
            button.setOnClickListener {
                if (button.text.toString().toInt() == randomNumber) {
                    // Sound abspielen, wenn die Antwort richtig ist
                    if (mMediaPlayer == null) {
                        mMediaPlayer = MediaPlayer.create(context, R.raw.success)
                        mMediaPlayer!!.isLooping = false
                        mMediaPlayer!!.start()
                    } else {
                        mMediaPlayer = null
                        mMediaPlayer = MediaPlayer.create(context, R.raw.success)
                        mMediaPlayer!!.isLooping = false
                        mMediaPlayer?.start()
                    }
                    findNavController().navigate(R.id.action_numberFragment_to_winningFragment)
                } else {
                    // Sound abspielen, wenn die Antwort falsch ist, und den Button ausblenden
                    if (mMediaPlayer == null) {
                        mMediaPlayer = MediaPlayer.create(context, R.raw.wrong)
                        mMediaPlayer!!.isLooping = false
                        mMediaPlayer!!.start()
                    } else {
                        mMediaPlayer = null
                        mMediaPlayer = MediaPlayer.create(context, R.raw.wrong)
                        mMediaPlayer!!.isLooping = false
                        mMediaPlayer?.start()
                    }
                    button.isVisible = false
                }
            }
        }

        // TODO: Möglicherweise einen Shop implementieren, um mit Ingame-Währung einzukaufen, die durch Erfolge gewonnen wird
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.GERMAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
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
