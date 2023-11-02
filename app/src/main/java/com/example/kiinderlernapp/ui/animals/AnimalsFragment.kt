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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.adapter.AnimalAdapter
import com.example.kiinderlernapp.databinding.FragmentAnimalsBinding
import com.example.kiinderlernapp.ui.MainViewModel
import java.util.Locale

class AnimalsFragment : Fragment() {

    private lateinit var binding: FragmentAnimalsBinding
    private val viewModel: MainViewModel by activityViewModels()

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

        viewModel.animals.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter =
                AnimalAdapter(it.shuffled(), viewModel, findNavController())
        }

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.recyclerView)
    }
}