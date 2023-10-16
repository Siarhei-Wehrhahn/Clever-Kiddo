package com.example.kiinderlernapp.adapter

import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.datamodels.dog.Dog
import com.example.kiinderlernapp.databinding.ListItemColorBinding
import com.example.kiinderlernapp.databinding.ListItemDogBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.concurrent.thread

class ColorAdapter(
    private var colorHexa: List<String>,
    private val colorDescription: List<String>
) : RecyclerView.Adapter<ColorAdapter.ItemViewColorHolder>() {

    inner class ItemViewColorHolder(val binding: ListItemColorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewColorHolder {
        val binding =
            ListItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewColorHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewColorHolder, position: Int) {
        var color = colorHexa[position]
        var colorName = colorDescription[position]
        holder.binding.root.setBackgroundColor(Color.parseColor(color))
        holder.binding.result.text = colorName

        holder.binding.root.setOnClickListener {
            holder.binding.result.isVisible = true
            val handler = Handler()
            handler.postDelayed({holder.binding.result.isVisible = false},3000)
        }
        if (colorName == "Weiß") {
            holder.binding.result.setTextColor(Color.BLACK)
        }

    }

    override fun getItemCount(): Int {
        return colorHexa.size
    }
}
