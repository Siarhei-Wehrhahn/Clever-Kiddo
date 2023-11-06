package com.example.kiinderlernapp.ui.tamagochi

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.kiinderlernapp.databinding.FragmentHomeBinding
import com.example.kiinderlernapp.databinding.FragmentTamagochiBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        var feedingList = listOf(
            binding.imageApple,
            binding.imageBroccoli,
            binding.imageCumbrum,
            binding.imageKiwi,
            binding.imagePeas,
            binding.imagePomegrenade,
            binding.imageSalat,
            binding.imageStrawberry
        )

            binding.foodButton.setOnClickListener {
                if (binding.feddingScrollV.scaleX == 1f) {
                    val scaleUp = ObjectAnimator.ofFloat(binding.feddingScrollV, "scaleX", 0.01f)
                    val animation = AnimatorSet()
                    animation.duration = 500
                    animation.play(scaleUp)
                    animation.start()
                    lifecycleScope.launch {
                        delay(500)
                        binding.feddingScrollV.isVisible = false
                    }
                } else {
                val scaleUp = ObjectAnimator.ofFloat(binding.feddingScrollV, "scaleX", 1.0f)
                val animation = AnimatorSet()
                animation.duration = 500
                binding.feddingScrollV.isVisible = true
                animation.play(scaleUp)
                animation.start()
            }
        }
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