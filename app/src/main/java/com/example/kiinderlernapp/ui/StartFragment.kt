package com.example.kiinderlernapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.datamodels.Tamagotchi
import com.example.kiinderlernapp.databinding.FragmentStartBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var clickCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Verknüpfen Sie das Fragment mit dem zugehörigen Layout
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            if (viewModel.tamagotchi == null) {
                viewModel.updateTamagotchi(
                    Tamagotchi(
                        0,
                        100,
                        100,
                        100,
                        100,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1
                    )
                )
            }
        }

        viewModel.loadDataTamagotchi()

        Glide.with(this)
            .load(R.raw.loading)
            .into(binding.imageGif)


        // Verwenden von Kotlin Coroutines, um eine Verzögerung von 3 Sekunden zu erzeugen
        lifecycleScope.launch {
            delay(3000)

            // Navigieren zur HomeFragment-Ansicht nach der Verzögerung
            findNavController().navigate(R.id.action_startFragment_to_homeFragment)
        }

        binding.imageGif.setOnClickListener {
            clickCount++

            if (clickCount >= 5 && !viewModel.tamagotchi.value!!.giftActivated) {
                viewModel.addGift()
                showGiftAlert()
                viewModel.tamagotchi.value!!.giftActivated = true

                // Ein SharedPreferences-Objekt erstellen, um die Punktzahl zu speichern
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()

                // Die aktuelle Punktzahl im SharedPreferences speichern
                editor.putInt("score", viewModel.score.value!!)
                editor.apply()
            }
        }
    }

    private fun showGiftAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Geheimnis gelüftet!")
            .setMessage("Du hast ein Geheimnis gelüftet und wirst belohnt!")
            .setPositiveButton("OK") { dialog, _ ->
                // OK-Button wurde geklickt
                dialog.dismiss()
            }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}
