package com.example.kiinderlernapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kiinderlernapp.data.datamodels.cat.Response
import com.example.kiinderlernapp.data.datamodels.dog.Dog
import com.example.kiinderlernapp.data.datamodels.dog.Dogs
import com.example.kiinderlernapp.data.remoute.CatApi
import com.example.kiinderlernapp.data.remoute.DogApi

class AppRepository(private val dogApi: DogApi, private val catApi: CatApi) {

    private val log = "AppRepository"

    private val _cats = MutableLiveData<Response>()
    val cats: LiveData<Response>
        get() = _cats

    private val _dogs = MutableLiveData<List<Dog>>()
    val dogs: LiveData<List<Dog>>
        get() = _dogs

    suspend fun getCats() {
        try {
            _cats.value = catApi.retrofitService.getCats()
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }

    suspend fun getDogs() {
        try {
            var list = mutableListOf<Dog>()
            repeat(50) {
                list.add(Dog(0,dogApi.retrofitService.getDogs().message))
            }
            _dogs.value = list.toList()
        } catch (e: Exception) {
            Log.e("$log", "${e.message}")
        }
    }
}