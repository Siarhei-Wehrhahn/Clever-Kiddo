package com.example.kiinderlernapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.data.localdata.DataBase
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

    private val _animals = MutableLiveData<List<Animal>>()
    val animals: LiveData<List<Animal>>
        get() = _animals

    private val _dataset = MutableLiveData<List<Animal>>()
    val dataset: LiveData<List<Animal>>
        get() = _dataset

    suspend fun getDatabase() {
        try {
            _dataset.postValue(database.dogDatabseDao.getAll())
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }

    suspend fun getAnimals() {
        try {
            var list = mutableListOf<Animal>()
            val catUrls = catApi.retrofitService.getCats()
            val dogUrls = dogApi.retrofitService.getDogs()
            repeat(1) {
                list.add(Animal(0,dogUrls.message))
                list.add(Animal(0,catUrls[it].url))
            }
            _animals.postValue(list.toList())
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }

    suspend fun InsertAnimals(animal: Animal) {
        try {
            database.dogDatabseDao.insertItem(animal)
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }

    fun deleteById(id: Int) {
        try {
            database.dogDatabseDao.delete(id)
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }
}