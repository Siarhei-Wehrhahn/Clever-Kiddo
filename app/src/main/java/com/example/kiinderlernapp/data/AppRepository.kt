package com.example.kiinderlernapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.data.datamodels.Quiz
import com.example.kiinderlernapp.data.localdata.DataBase
import com.example.kiinderlernapp.data.remoute.CatApi
import com.example.kiinderlernapp.data.remoute.DogApi

class AppRepository(
    private val dogApi: DogApi, // Zugriff auf die Dog API
    private val catApi: CatApi, // Zugriff auf die Cat API
    private val database: DataBase // Zugriff auf die lokale Datenbank
) {

    private val log = "AppRepository"

    private val _cats = MutableLiveData<List<Animal>>()
    val cats: LiveData<List<Animal>>
        get() = _cats

    private val _animals = MutableLiveData<List<Animal>>()
    val animals: LiveData<List<Animal>>
        get() = _animals

    private val _dataset = MutableLiveData<List<Animal>>()
    val dataset: LiveData<List<Animal>>
        get() = _dataset

    // Funktion zum Abrufen von Daten aus der lokalen Datenbank
    suspend fun getDatabase() {
        try {
            _dataset.postValue(database.dogDatabseDao.getAll())
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }

    // Funktion zum Abrufen von Tierbildern von Dog und Cat APIs
    suspend fun getAnimals() {
        try {
            var list = mutableListOf<Animal>()
            val catUrls = catApi.retrofitService.getCats()
            val dogUrls = dogApi.retrofitService.getDogs()
            // Füge ein Hundebild und ein Katzenbild zur Liste hinzu und mische sie
            list.add(Animal(0, dogUrls.message, true))
            list.add(Animal(0, catUrls[0].url, false))
            _animals.postValue(list.toList().shuffled())
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }

    // Funktion zum Einfügen eines Tierobjekts in die lokale Datenbank
    suspend fun insertAnimals(animal: Animal) {
        try {
            database.dogDatabseDao.insertItem(animal)
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }

    // Funktion zum Löschen eines Tierobjekts anhand seiner ID aus der Datenbank
    fun deleteById(id: Int) {
        try {
            database.dogDatabseDao.delete(id)
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }
}
