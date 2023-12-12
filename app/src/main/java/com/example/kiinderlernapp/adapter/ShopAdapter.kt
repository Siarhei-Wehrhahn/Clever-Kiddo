package com.example.kiinderlernapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.kiinderlernapp.data.datamodels.ShopItem
import com.example.kiinderlernapp.data.datamodels.loadItem
import com.example.kiinderlernapp.databinding.ListItemShopBinding
import com.example.kiinderlernapp.ui.MainViewModel

class ShopAdapter(
    var shopItems: List<ShopItem>,
    val viewModel: MainViewModel,
    val context: Context
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
        // Hier wird auf die SharedPreferences zugegriffen, um den Punktestand zu laden und anzuzeigen.
        val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        viewModel.setStars(sharedPref.getInt("score", 0))

        holder.binding.imageItem.setImageResource(item.res)
        holder.binding.textItemPrice.text = item.price.toString()

        holder.binding.item.setOnClickListener {
            if (viewModel.score.value!! >= item.price) {
                viewModel.addItem(item.item)
                viewModel.removeStars(item.price)

                // Ein SharedPreferences-Objekt erstellen, um die Punktzahl zu speichern
                val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()

                // Die aktuelle Punktzahl im SharedPreferences speichern
                editor.putInt("score", viewModel.score.value!!)
                editor.apply()
            } else {
                Toast.makeText(context, "Dein Geld reicht leider nicht!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun update(items: List<ShopItem>) {
        shopItems = items
        notifyDataSetChanged()
    }

}