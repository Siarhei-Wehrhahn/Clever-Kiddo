package com.example.kiinderlernapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.data.datamodels.Tamagotchi
import com.example.kiinderlernapp.data.localdata.animal.AnimalDataBase
import com.example.kiinderlernapp.data.localdata.tamagotchi.TamagotchiDataBase
import com.example.kiinderlernapp.data.remote.CatApi
import com.example.kiinderlernapp.data.remote.DogApi

class AppRepository(
    private val dogApi: DogApi, // Zugriff auf die Dog API
    private val catApi: CatApi, // Zugriff auf die Cat API
    private val animalDatabase: AnimalDataBase, // Zugriff auf die lokale Animal Datenbank
    private val tamagotchiDatabase: TamagotchiDataBase // Zugriff auf die lokale Tamagotchi Datenbank
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

    private val _tamagotchi = MutableLiveData<Tamagotchi>()
    val tamagotchi: LiveData<Tamagotchi>
        get() = _tamagotchi

    suspend fun updateTamagotchiStats(tamagotchi: Tamagotchi) {
        try {
            tamagotchiDatabase.tamagotchiDatabaseDao.updateStats(tamagotchi)
        } catch (e: Exception) {
            Log.e("&log", "${e.message}")
        }
    }

    suspend fun insertTamagotchiStats(tamagotchi: Tamagotchi) {
        try {
            tamagotchiDatabase.tamagotchiDatabaseDao.insertStats(tamagotchi)
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }

    suspend fun getTamagotchiStats() {
        try {
            _tamagotchi.postValue(tamagotchiDatabase.tamagotchiDatabaseDao.getTamagotchiValues())
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }

    // Funktion zum Abrufen von Daten aus der lokalen Datenbank
    suspend fun getDatabase() {
        try {
            _dataset.postValue(animalDatabase.animalDatabseDao.getAll())
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
            animalDatabase.animalDatabseDao.insertItem(animal)
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }

    // Funktion zum Löschen eines Tierobjekts anhand seiner ID aus der Datenbank
    fun deleteById(id: Int) {
        try {
            animalDatabase.animalDatabseDao.delete(id)
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }
}
