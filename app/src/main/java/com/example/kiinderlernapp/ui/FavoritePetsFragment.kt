package com.example.kiinderlernapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.kiinderlernapp.adapter.FavoritePetAdapter
import com.example.kiinderlernapp.databinding.FragmentFavoritePetsBinding

class FavoritePetsFragment : Fragment() {

    private lateinit var binding: FragmentFavoritePetsBinding
    private val viewmodel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritePetsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO Recyclerview inizialisieren möglicherweise mit einem observer
    }
}