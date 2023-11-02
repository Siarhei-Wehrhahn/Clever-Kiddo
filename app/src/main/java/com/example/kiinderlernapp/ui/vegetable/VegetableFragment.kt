package com.example.kiinderlernapp.ui.vegetable

import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.databinding.FragmentVegetableBinding
import com.example.kiinderlernapp.ui.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class VegetableFragment : Fragment(),TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentVegetableBinding
    private val viewModel: MainViewModel by activityViewModels()
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
        binding = FragmentVegetableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setText()
        viewModel.quiz.observe(viewLifecycleOwner) {
            setText()
        }
    }

    private fun setText() {

        // Text für die Frage und Bilder für die Antworten setzen
        binding.textQuestion3.text = viewModel.quiz.value?.question
        binding.imageAnswereA.setImageResource(viewModel.quiz.value!!.answerA)
        binding.imageAnswereB.setImageResource(viewModel.quiz.value!!.answerB)
        binding.imageAnswereC.setImageResource(viewModel.quiz.value!!.answerC)
        binding.imageAnswereD.setImageResource(viewModel.quiz.value!!.answerD)

        viewModel.textToSpeach(viewModel.quiz.value!!.question)

        lifecycleScope.launch {
            delay(500)
            viewModel.textToSpeach.observe(viewLifecycleOwner) {
                // Zufällige Tonhöhe und Sprachrate einstellen
                textToSpeech.setPitch(listOf(0.8f, 0.9f, 1f, 1.1f, 1.2f).random())
                textToSpeech.setSpeechRate(listOf(0.8f, 0.9f, 1f, 1.1f, 1.2f).random())
                textToSpeech.speak(it, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        // Liste der Antwortbilder erstellen
        val answereList =
            listOf(binding.imageAnswereA, binding.imageAnswereB, binding.imageAnswereC, binding.imageAnswereD)

        // Antwortklick-Logik
        answereList.forEach { answerImage ->
            answerImage.setOnClickListener {
                val answer = viewModel.checkAnswere(answereList.indexOf(answerImage) + 1, requireContext())

                if (answer) {
                    binding.imagePlus2.isVisible = true
                    binding.imageStar.isVisible = true
                    viewModel.winStars()

                    // Verzögerung, bevor zur nächsten Frage gewechselt wird
                    lifecycleScope.launch {
                        delay(1000)
                        binding.imagePlus2.isVisible = false
                        binding.imageStar.isVisible = false
                        viewModel.nextQuestion()
                        answereList.forEach { it.visibility = View.VISIBLE }
                    }
                } else {
                    // Antwortbild ausblenden, wenn die Antwort falsch ist
                    answerImage.visibility = View.GONE
                }
            }
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
