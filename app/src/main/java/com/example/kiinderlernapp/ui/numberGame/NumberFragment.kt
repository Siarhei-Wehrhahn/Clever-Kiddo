package com.example.kiinderlernapp.ui.numberGame

import android.graphics.Typeface
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
    private val viewModel: MainViewModel by viewModels()
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

        // delay muss gemacht werden weil die textToSpeech Engine länger zum laden braucht als der observer
        lifecycleScope.launch {
            delay(500)
            viewModel.textToSpeach.observe(viewLifecycleOwner) {
                textToSpeech.setPitch(listOf(0.8f, 0.9f, 1f, 1.1f, 1.2f).random())
                textToSpeech.setSpeechRate(listOf(0.8f, 0.9f, 1f, 1.1f, 1.2f).random())
                textToSpeech.speak(it, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        var randomNumber = (1..10).random()

        viewModel.textToSpeach("Drücke auf die $randomNumber")

        binding.textQuestion.setText("Drücke auf die $randomNumber")

        var answereButtons = listOf(
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
        randomAnswereList = randomAnswereList.shuffled()
        repeat(answereButtons.size - 1) {
            answereButtons[it].text = randomAnswereList[it].toString()
            answereButtons[it].typeface = Typeface.create(
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

        answereButtons.forEachIndexed { index, button ->
            button.text = randomAnswereList[index].toString()
            button.setOnClickListener {
                if (button.text.toString().toInt() == randomNumber) {
                    viewModel.textToSpeach("Richtig")
                    findNavController().navigate(R.id.action_numberFragment_to_winningFragment)
                } else {
                    viewModel.textToSpeach("Leider Falsch")
                    button.isVisible = false
                }
            }
        }

        // TODO Mehrere antwortmöglichkeiten und bei falscher antwort coole Animation mit sound abspielen lassen

        // TODO Vielleicht einen shop einbringen bei dem man mit ingamegeld einkaufen kann welches man durch erfolge gewinnt
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