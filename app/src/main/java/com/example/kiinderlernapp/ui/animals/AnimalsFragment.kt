package com.example.kiinderlernapp.ui.animals

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.adapter.AnimalAdapter
import com.example.kiinderlernapp.databinding.FragmentAnimalsBinding
import com.example.kiinderlernapp.ui.MainViewModel
import java.util.Locale

class AnimalsFragment : Fragment(),TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentAnimalsBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textToSpeech = TextToSpeech(requireContext(),this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadDataAnimals()

        viewModel.textToSpeach.observe(viewLifecycleOwner) {
            textToSpeech.setPitch(listOf(0.8f,0.9f,1f,1.1f,1.2f).random())
            textToSpeech.setSpeechRate(listOf(0.8f,0.9f,1f,1.1f,1.2f).random())
            textToSpeech.speak(it, TextToSpeech.QUEUE_FLUSH, null, null)

        }

        viewModel.animals.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter =
                AnimalAdapter(it.shuffled(), viewModel, findNavController())
        }

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.recyclerView)
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