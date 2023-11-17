package com.example.kiinderlernapp.ui.favoriteAnimals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kiinderlernapp.adapter.FavoritePetAdapter
import com.example.kiinderlernapp.databinding.FragmentFavoritePetsBinding
import com.example.kiinderlernapp.ui.MainViewModel

class FavoritePetsFragment : Fragment() {

    private lateinit var binding: FragmentFavoritePetsBinding
    private val viewmodel: MainViewModel by activityViewModels()

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

        // ViewModel-Methode "getDatabase" aufrufen, um die Daten aus der lokalen Datenbank abzurufen
        viewmodel.getDatabase()

        binding.imageBack3.setOnClickListener {
            findNavController().popBackStack()
        }

        // RecyclerView-Adapter "FavoritePetAdapter" mit den abgerufenen Daten setzen
        viewmodel.dataset.observe(viewLifecycleOwner) {
            binding.recyclerViewFavo.adapter = FavoritePetAdapter(it, viewmodel)
        }
    }
}
