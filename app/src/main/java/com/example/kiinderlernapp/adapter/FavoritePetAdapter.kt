package com.example.kiinderlernapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.databinding.FragmentFavoritePetsBinding
import com.example.kiinderlernapp.databinding.ListItemAnimalBinding
import com.example.kiinderlernapp.databinding.ListItemFavoritepetsBinding
import com.example.kiinderlernapp.ui.MainViewModel

class FavoritePetAdapter(
    private var dataset: List<Animal>, // Der Datensatz der Lieblingstiere
    private val viewModel: MainViewModel // Das Haupt-ViewModel
) : RecyclerView.Adapter<FavoritePetAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListItemFavoritepetsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemFavoritepetsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var animal = dataset[position]

        // Lade und zeige das Bild des Lieblingstiers mit abgerundeten Ecken an
        holder.binding.imageViewPet.load(animal.imageRecource) {
            error(R.drawable.false_2061131_1280)
            transformations(RoundedCornersTransformation(10f))
        }

        // TODO: Hier fehlt noch eine Anmerkung für die Vergrößerungseinstellung, wenn sie hinzugefügt wird.

        // OnClickListener für die Schaltfläche imageDelete, um das Tier aus den Favoriten zu entfernen
        holder.binding.imageDelete.setOnClickListener {
            viewModel.deleteById(animal.id) // Lösche das Tier aus den Favoriten
            notifyDataSetChanged() // Benachrichtige das RecyclerView, um die Änderungen anzuzeigen
            viewModel.getDatabase() // Aktualisiere die Datenbankansicht
        }
    }
}
