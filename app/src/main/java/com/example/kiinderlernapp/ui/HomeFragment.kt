package com.example.kiinderlernapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.score
import com.example.kiinderlernapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textScore.text = score.toString()

        binding.colorGame.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_colorFragment)
        }

        binding.numberGame.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_numberFragment)
        }

        binding.dogs.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_dogsFragment)
        }

        binding.petsDataBase.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoritePetsFragment)
        }
    }
}