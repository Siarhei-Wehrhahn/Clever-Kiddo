package com.example.kiinderlernapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("App Schließen")
                builder.setMessage("Sind sie sich sicher ?")
                builder.setPositiveButton("Ja") { dialog, which ->
                    requireActivity().finish()
                }
                builder.setNegativeButton("Nein") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Hier wird auf die SharedPreferences zugegriffen, um den Punktestand zu laden und anzuzeigen.
        val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        viewModel._score.value = sharedPref.getInt("score", 0)

        binding.textScore.text = viewModel.score.value.toString()

        viewModel.score.observe(viewLifecycleOwner) {
            binding.textScore.text = viewModel.score.value.toString()
        }

        // Die folgenden Abschnitte sind Klick-Handler für die verschiedenen Optionen im HomeFragment.
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

        binding.vegetableFruitQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_vegetableFragment)
        }

        binding.tamagotchi.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_tamagochiFragment)
        }

        binding.shop.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_shopFragment)
        }
    }
}
