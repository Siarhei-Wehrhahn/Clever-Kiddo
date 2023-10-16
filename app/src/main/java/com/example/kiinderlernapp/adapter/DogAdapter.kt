package com.example.kiinderlernapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.datamodels.dog.Dog
import com.example.kiinderlernapp.databinding.ListItemDogBinding

class DogAdapter(
    private var dataset: List<Dog>
): RecyclerView.Adapter<DogAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListItemDogBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemDogBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var dog = dataset[position]
        holder.binding.imageDog.load(dog.imageRecource) {
            error(R.drawable.false_2061131_1280)
            transformations(RoundedCornersTransformation(10f))
        }


    }
}