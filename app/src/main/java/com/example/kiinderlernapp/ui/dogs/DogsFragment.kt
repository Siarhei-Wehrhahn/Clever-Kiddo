package com.example.kiinderlernapp.ui.dogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kiinderlernapp.adapter.DogAdapter
import com.example.kiinderlernapp.databinding.FragmentDogsBinding
import com.example.kiinderlernapp.ui.MainViewModel
import kotlinx.coroutines.launch

class DogsFragment : Fragment() {

    private lateinit var binding : FragmentDogsBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDogsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.loadDataDogs()
        }

        viewModel.dogs.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = DogAdapter(it)
        }

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.recyclerView)
    }
}