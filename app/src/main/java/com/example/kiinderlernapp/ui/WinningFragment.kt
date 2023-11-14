package com.example.kiinderlernapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kiinderlernapp.databinding.FragmentWinningBinding
import com.example.kiinderlernapp.ui.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WinningFragment : Fragment() {

    private lateinit var binding: FragmentWinningBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWinningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Das ViewModel aktualisiert die Anzahl der gewonnenen Sterne
        viewModel.winStars()

        // Ein SharedPreferences-Objekt erstellen, um die Punktzahl zu speichern
        val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Die aktuelle Punktzahl im SharedPreferences speichern
        editor.putInt("score", viewModel.score.value!!)
        editor.apply()

        // Eine Verzögerung von 3 Sekunden, bevor zur vorherigen Ansicht zurücknavigiert wird
        lifecycleScope.launch {
            delay(3000)
            findNavController().popBackStack()
        }
    }
}
