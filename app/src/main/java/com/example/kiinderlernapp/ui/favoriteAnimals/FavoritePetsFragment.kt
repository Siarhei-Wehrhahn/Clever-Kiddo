package com.example.kiinderlernapp.ui.favoriteAnimals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.kiinderlernapp.adapter.FavoritePetAdapter
import com.example.kiinderlernapp.databinding.FragmentFavoritePetsBinding
import com.example.kiinderlernapp.ui.MainViewModel

class FavoritePetsFragment : Fragment() {

    private lateinit var binding: FragmentFavoritePetsBinding
    private val viewmodel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritePetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.getDatabase()

        // TODO eine großere ansicht erstellen von den bildern



        viewmodel.dataset.observe(viewLifecycleOwner) {
            binding.recyclerViewFavo.adapter = FavoritePetAdapter(it, viewmodel)
        }
    }
}