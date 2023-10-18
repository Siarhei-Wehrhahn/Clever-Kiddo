package com.example.kiinderlernapp.ui.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.kiinderlernapp.adapter.AnimalAdapter
import com.example.kiinderlernapp.databinding.FragmentCatsBinding
import com.example.kiinderlernapp.ui.MainViewModel
import kotlinx.coroutines.launch

class CatsFragment : Fragment() {

    private lateinit var binding: FragmentCatsBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.loadDataCats()
        }

        viewModel.cats.observe(viewLifecycleOwner) {
            binding.recyclerViewCats.adapter = AnimalAdapter(it, viewModel)
        }


        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.recyclerViewCats)
    }
}