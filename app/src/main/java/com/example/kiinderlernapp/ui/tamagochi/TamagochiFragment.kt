package com.example.kiinderlernapp.ui.tamagochi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.kiinderlernapp.databinding.FragmentHomeBinding
import com.example.kiinderlernapp.databinding.FragmentTamagochiBinding

class TamagochiFragment : Fragment() {

    private lateinit var binding: FragmentTamagochiBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTamagochiBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        // Hier wird der OnClickListener für den Button festgelegt
        binding.FüternButton.setOnClickListener {
            // Hier wird das GIF abgespielt, wenn auf den Button geklickt wird.
            val gifResId = R.raw.your_local_gif // Ersetzen Sie 'your_local_gif' durch den Dateinamen Ihres GIFs.

            // Laden und anzeigen des GIFs
            Glide.with(this)
                .asGif()
                .load(gifResId)
                .into(binding.GIF)
        }

         */
    }
}