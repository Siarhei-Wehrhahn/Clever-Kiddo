package com.example.kiinderlernapp.adapter

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.databinding.ListItemAnimalBinding
import com.example.kiinderlernapp.ui.MainViewModel

class AnimalAdapter(
    private var dataset: List<Animal>,
    private val viewModel: MainViewModel,
    private val navController: NavController
) : RecyclerView.Adapter<AnimalAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ListItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var animal = dataset[position]
        var mMediaPlayer: MediaPlayer? = null

        // Lade und zeige das Bild des Tieres mit abgerundeten Ecken an
        holder.binding.imageDog.load(animal.imageRecource) {
            error(R.drawable.false_2061131_1280)
            transformations(RoundedCornersTransformation(10f))
        }

        // OnClickListener für die Schaltfläche imageCatButton
        holder.binding.imageCatButton.setOnClickListener {
            if (!animal.isDog) {
                // Navigiere zum winningFragment, wenn es sich nicht um einen Hund handelt
                navController.navigate(R.id.action_animalFragment_to_winningFragment)
                if (mMediaPlayer == null) {
                    mMediaPlayer = MediaPlayer.create(holder.itemView.context, R.raw.success)
                    mMediaPlayer!!.isLooping = false
                    mMediaPlayer!!.start()
                } else mMediaPlayer!!.start()
            } else {
                // Spiele einen Sound ab, um anzuzeigen, dass die Antwort falsch ist
                if (mMediaPlayer == null) {
                    mMediaPlayer = MediaPlayer.create(holder.itemView.context, R.raw.wrong)
                    mMediaPlayer!!.isLooping = false
                    mMediaPlayer!!.start()
                } else mMediaPlayer!!.start()
            }
        }

        // OnClickListener für die Schaltfläche imageDogButton
        holder.binding.imageDogButton.setOnClickListener {
            if (animal.isDog) {
                // Navigiere zum winningFragment, wenn es sich um einen Hund handelt
                navController.navigate(R.id.action_animalFragment_to_winningFragment)
                if (mMediaPlayer == null) {
                    mMediaPlayer = MediaPlayer.create(holder.itemView.context, R.raw.success)
                    mMediaPlayer!!.isLooping = false
                    mMediaPlayer!!.start()
                } else mMediaPlayer!!.start()
            } else {
                // Spiele einen Sound ab, um anzuzeigen, dass die Antwort falsch ist
                if (mMediaPlayer == null) {
                    mMediaPlayer = MediaPlayer.create(holder.itemView.context, R.raw.wrong)
                    mMediaPlayer!!.isLooping = false
                    mMediaPlayer!!.start()
                } else mMediaPlayer!!.start()
            }
        }

        // OnClickListener für die Schaltfläche buttonDownload, um das Tier herunterzuladen
        holder.binding.buttonDownload.setOnClickListener {
            viewModel.insert(animal)
            showToast(holder.binding.root.context, "Download...")
        }
    }

    // Funktion, um einen Toast anzuzeigen
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
