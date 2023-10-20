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

class AnimalsFragment : Fragment() {

    private lateinit var binding: FragmentAnimalsBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var textToSpeech: TextToSpeech
    private var soundPool: SoundPool? = null
    private var soundId: Int = 0

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

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(1) // Maximale Anzahl der gleichzeitigen Soundeffekte
            .setAudioAttributes(audioAttributes)
            .build()

        // Laden der Sounddatei in den SoundPool
        soundId = soundPool?.load(requireContext(), R.raw.success, 1) ?: 0

        view.setOnClickListener {
            playSoundEffect()
        }


        viewModel.animals.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter =
                AnimalAdapter(it.shuffled(), viewModel, findNavController())
        }

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.recyclerView)
    }

    fun playSoundEffect() {
        soundPool?.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Freigeben des SoundPool
        soundPool?.release()
        soundPool = null
    }
}