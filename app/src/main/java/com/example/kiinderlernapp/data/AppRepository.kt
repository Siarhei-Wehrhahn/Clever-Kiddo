package com.example.kiinderlernapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.data.localdata.DataBase
import com.example.kiinderlernapp.data.localdata.DogDao
import com.example.kiinderlernapp.data.remoute.CatApi
import com.example.kiinderlernapp.data.remoute.DogApi

class AppRepository(
    private val dogApi: DogApi,
    private val catApi: CatApi,
    private val database: DataBase
) {

    private val log = "AppRepository"

    private val _cats = MutableLiveData<List<Animal>>()
    val cats: LiveData<List<Animal>>
        get() = _cats

    private val _dogs = MutableLiveData<List<Animal>>()
    val dogs: LiveData<List<Animal>>
        get() = _dogs

    suspend fun getCats() {
        try {
            var list = mutableListOf<Animal>()
            val urls = catApi.retrofitService.getCats()
            repeat(urls.size -1) {
                list.add(Animal(0,urls[it].url))
            }
            _cats.value = list
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }

    suspend fun getDogs() {
        try {
            var list = mutableListOf<Animal>()
            repeat(50) {
                list.add(Animal(0,dogApi.retrofitService.getDogs().message))
            }
            _dogs.value = list.toList()
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }

    suspend fun InsertAnimals(animal: Animal) {
        try {
            database.dogDatabseDao.insertItem(animal)
        } catch (e: Exception) {
            Log.e("AppRepository", "${e.message}")
        }
    }

    // Für die Datenbank
    fun getAll() {
        try {
            database.dogDatabseDao.getAll()
        } catch (e: Exception) {
            Log.e("AppRepository", "${e.message}")
        }
    }
}