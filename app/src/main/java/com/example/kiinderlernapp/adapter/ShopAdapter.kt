package com.example.kiinderlernapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kiinderlernapp.data.datamodels.ShopItem
import com.example.kiinderlernapp.databinding.ListItemShopBinding
import com.example.kiinderlernapp.ui.MainViewModel

class ShopAdapter(
    val shopItems: List<ShopItem>,
    val viewModel: MainViewModel
): RecyclerView.Adapter<ShopAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListItemShopBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return shopItems.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var item = shopItems[position]

        holder.binding.imageItem.setImageResource(item.res)
        holder.binding.textItemPrice.text = item.price.toString()
    }

}