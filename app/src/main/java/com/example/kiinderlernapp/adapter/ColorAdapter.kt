package com.example.kiinderlernapp.adapter

import android.graphics.Color
import android.os.Handler
import android.service.voice.VoiceInteractionSession.VisibleActivityCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.kiinderlernapp.databinding.ListItemColorBinding
import com.example.kiinderlernapp.ui.MainViewModel

class ColorAdapter(
    private var colorHexa: List<String>,
    private val colorDescription: List<String>,
    private val viewModel: MainViewModel
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

        // Setze die Hintergrundfarbe des Elements
        holder.binding.root.setBackgroundColor(Color.parseColor(color))

        // Setze den Text im TextView auf die Farbname
        holder.binding.result.text = colorName

        // Definiere die Aktion, wenn auf das Element geklickt wird
        holder.binding.root.setOnClickListener {
            holder.binding.textWichColor.isVisible = false
            // Mache den Text sichtbar
            holder.binding.result.isVisible = true
            val handler = Handler()
            viewModel.textToSpeach(colorName) // Sprich den Farbnamen aus
            handler.postDelayed({ holder.binding.result.isVisible = false }, 3000) // Verstecke den Text nach 3 Sekunden
            handler.postDelayed({ holder.binding.textWichColor.isVisible = true }, 3000)

        }

        // Wenn die Farbe Weiß ist, ändere die Textfarbe auf Schwarz
        if (colorName == "Weiß") {
            holder.binding.result.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount(): Int {
        return colorHexa.size
    }
}
