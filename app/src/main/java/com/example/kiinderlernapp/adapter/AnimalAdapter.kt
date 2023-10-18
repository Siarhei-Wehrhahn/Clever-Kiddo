package com.example.kiinderlernapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.databinding.ListItemAnimalBinding
import com.example.kiinderlernapp.ui.MainViewModel

class AnimalAdapter(
    private var dataset: List<Animal>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<AnimalAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListItemAnimalBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemAnimalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var animal = dataset[position]
        holder.binding.imageDog.load(animal.imageRecource) {
            error(R.drawable.false_2061131_1280)
            transformations(RoundedCornersTransformation(10f))
        }

        holder.binding.buttonDownload.setOnClickListener {
            viewModel.insert(animal)
        }
    }
}