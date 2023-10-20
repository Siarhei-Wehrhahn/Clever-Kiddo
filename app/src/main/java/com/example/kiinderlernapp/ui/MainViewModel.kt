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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = AppRepository(DogApi, CatApi, DataBase.getDatabase(application))

    val database = DataBase.getDatabase(application)

    private var _textToSpeach = MutableLiveData<String>()
    val textToSpeach: LiveData<String>
        get() = _textToSpeach

    val animals = repo.animals
    val dataset = repo.dataset

    fun insert(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.InsertAnimals(animal)
        }
    }

    fun loadDataAnimals() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAnimals()
        }
    }

    fun textToSpeach(text: String) {
        _textToSpeach.value = text
    }

    //TODO Livedata für die datenbank machen
    fun getDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getDatabase()
        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteById(id)
        }
    }
}