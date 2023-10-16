package com.example.kiinderlernapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiinderlernapp.data.AppRepository
import com.example.kiinderlernapp.data.remoute.CatApi
import com.example.kiinderlernapp.data.remoute.DogApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repo = AppRepository(DogApi, CatApi)

    var _textToSpeach = MutableLiveData<String>()
    val textToSpeach: LiveData<String>
        get() = _textToSpeach

    val cats = repo.cats
    val dogs = repo.dogs

    suspend fun loadDataCats() {
        viewModelScope.launch {
            repo.getCats()
        }
    }

    suspend fun loadDataDogs() {
        viewModelScope.launch {
            repo.getDogs()
        }
    }
}