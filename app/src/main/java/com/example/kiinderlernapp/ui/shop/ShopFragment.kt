package com.example.kiinderlernapp.ui.shop

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.adapter.ShopAdapter
import com.example.kiinderlernapp.data.datamodels.ShopItem
import com.example.kiinderlernapp.data.datamodels.loadItem
import com.example.kiinderlernapp.databinding.FragmentShopBinding
import com.example.kiinderlernapp.ui.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class ShopFragment : Fragment() {

    private lateinit var binding: FragmentShopBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = ShopAdapter(loadItem(), viewModel, requireContext())

        // Hier wird auf die SharedPreferences zugegriffen, um den Punktestand zu laden und anzuzeigen.
        val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        viewModel.setStars(sharedPref.getInt("score", 0))

        // Punktestand
        binding.textStarCount.text = viewModel.score.value.toString()

        // Toggle Button für die sortierung der Artikel
        binding.toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            adapter.update(getList())
        }

        binding.imageBack6.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.score.observe(viewLifecycleOwner) {
            val formattedScore = NumberFormat.getNumberInstance(Locale.getDefault()).format(viewModel.score.value)
            binding.textStarCount.text = formattedScore
        }

        binding.recShop.adapter = adapter
    }

    // Fun um die Artikel des shops zu sortieren
    fun getList(): List<ShopItem> {
        var list: MutableList<ShopItem> = mutableListOf()
        if (binding.toggleButton.checkedButtonIds.contains(R.id.lebensmittel)) {
            list.addAll(loadItem().subList(3,11))
        }
        if (binding.toggleButton.checkedButtonIds.contains(R.id.spielzeug)) {
            list.addAll(loadItem().subList(1,3))
        }
        if (binding.toggleButton.checkedButtonIds.contains(R.id.toiletenpapier)) {
            list.addAll(loadItem().subList(0,1))
        }
        return list
    }
}