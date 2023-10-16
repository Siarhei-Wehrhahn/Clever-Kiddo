package com.example.kiinderlernapp.ui.colorGame

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kiinderlernapp.adapter.ColorAdapter
import com.example.kiinderlernapp.databinding.FragmentColorBinding

class ColorFragment : Fragment() {

    private lateinit var binding: FragmentColorBinding
    private val colors = listOf(
        "#FF1100",  // Rot
        "#08FF00",  // Grün
        "#0048FF",  // Blau
        "#FF000000",  // Schwarz
        "#FFFFFFFF",  // Weiß
        "#CFCFCF",  // Licht
        "#7700FF",  // Lila
        "#808080",  // Grau
        "#FF00D4",  // Pink
        "#FFFF00",  // Gelb
        "#FFA500",  // Orange
        "#964B00",  // Braun
        "#008080",  // Teal
        "#00FFFF",  // Cyan
        "#9400D3",  // Violett
        "#FF00FF",  // Magenta
        "#FFD700", // Gold
        "#C0C0C0",// Silber
        "#800000",  // Maroon
        "#000080",  // Navy
    )

    private val descriptions = listOf(
        "Rot", "Grün", "Blau", "Schwarz", "Weiß", "Licht", "Purple", "Grau",
        "Pink", "Gelb", "Orange", "Braun", "Teal", "Cyan", "Violett", "Magenta",
        "Gold", "Silber", "Maroon", "Navy"
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentColorBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewColor.adapter = ColorAdapter(colors,descriptions)
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.recyclerViewColor)
    }
}