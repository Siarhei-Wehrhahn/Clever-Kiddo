package com.example.kiinderlernapp.ui.vegetable

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.Questions
import com.example.kiinderlernapp.data.datamodels.Quiz
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
        binding = FragmentVegetableBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(500)
            viewModel.textToSpeach.observe(viewLifecycleOwner) {
                textToSpeech.setPitch(listOf(0.8f, 0.9f, 1f, 1.1f, 1.2f).random())
                textToSpeech.setSpeechRate(listOf(0.8f, 0.9f, 1f, 1.1f, 1.2f).random())
                textToSpeech.speak(it, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        setText()
        viewModel.quiz.observe(viewLifecycleOwner) {
            setText()
        }
    }

    private fun setText() {
        binding.textQuestion3.text = viewModel.quiz.value?.question

        binding.imageAnswereA.setImageResource(viewModel.quiz.value!!.answerA)

        binding.imageAnswereB.setImageResource(viewModel.quiz.value!!.answerB)

        binding.imageAnswereC.setImageResource(viewModel.quiz.value!!.answerC)

        binding.imageAnswereD.setImageResource(viewModel.quiz.value!!.answerD)

        var answereList =
            listOf(binding.imageAnswereA, binding.imageAnswereB, binding.imageAnswereC, binding.imageAnswereD)
        answereList.forEach {
            it.setOnClickListener {
                var answere = viewModel.checkAnswere(answereList.indexOf(it) + 1)
                if (answere) {
                    viewModel.nextQuestion()
                } else {
                    viewModel.textToSpeach("Leider Falsch")
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