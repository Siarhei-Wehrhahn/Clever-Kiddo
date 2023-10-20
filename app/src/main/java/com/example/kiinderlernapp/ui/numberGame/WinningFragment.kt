package com.example.kiinderlernapp.ui.numberGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.score
import com.example.kiinderlernapp.databinding.FragmentWinningBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WinningFragment : Fragment() {

    private lateinit var binding: FragmentWinningBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWinningBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        score += 2

        lifecycleScope.launch {
            delay(3000)
            findNavController().popBackStack()
        }
    }
}