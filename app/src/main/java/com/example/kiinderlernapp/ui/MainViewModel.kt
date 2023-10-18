package com.example.kiinderlernapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiinderlernapp.data.AppRepository
import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.data.localdata.DataBase
import com.example.kiinderlernapp.data.remoute.CatApi
import com.example.kiinderlernapp.data.remoute.DogApi
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = AppRepository(DogApi, CatApi, DataBase.getDatabase(application))

    val database = DataBase.getDatabase(application)

    private var _textToSpeach = MutableLiveData<String>()
    val textToSpeach: LiveData<String>
        get() = _textToSpeach

    //TODO Livedata für die datenbank machen

    val cats = repo.cats
    val dogs = repo.dogs

    fun insert(animal: Animal) {
        viewModelScope.launch {
            repo.InsertAnimals(animal)
        }
    }

    fun loadDataCats() {
        viewModelScope.launch {
            repo.getCats()
        }
    }

    suspend fun loadDataDogs() {
        viewModelScope.launch {
            repo.getDogs()
        }
    }

    fun loadDb() {
        viewModelScope.launch {
            repo.getAll()
        }
    }

    fun textToSpeach(text: String) {
        _textToSpeach.value = text
    }
}