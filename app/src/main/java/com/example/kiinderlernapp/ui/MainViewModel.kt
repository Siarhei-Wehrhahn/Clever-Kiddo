package com.example.kiinderlernapp.ui

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.AppRepository
import com.example.kiinderlernapp.data.Questions
import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.data.datamodels.Quiz
import com.example.kiinderlernapp.data.localdata.DataBase
import com.example.kiinderlernapp.data.remoute.CatApi
import com.example.kiinderlernapp.data.remoute.DogApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var score = 0

    fun winStars() {
        score += 2
    }

    private var index = 0

    private val repo = AppRepository(DogApi, CatApi, DataBase.getDatabase(application))

    val database = DataBase.getDatabase(application)

    private var _textToSpeach = MutableLiveData<String>()
    val textToSpeach: LiveData<String>
        get() = _textToSpeach

    val animals = repo.animals
    val dataset = repo.dataset

    fun insert(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertAnimals(animal)
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

    private var _quiz = MutableLiveData(Questions.questions[index])
    val quiz: LiveData<Quiz>
        get() = _quiz

    fun checkAnswere(answere: Int,context: Context): Boolean {
        var mMediaPlayer: MediaPlayer? = null
        var rightAnswere = answere == _quiz.value?.rightAnswere
        if (rightAnswere && index < Questions.questions.size -1) {
            rightAnswere = true
            // sound das die antwort falsch ist
            if (mMediaPlayer == null) {
                mMediaPlayer = MediaPlayer.create(context, R.raw.success)
                mMediaPlayer!!.isLooping = false
                mMediaPlayer!!.start()
            } else mMediaPlayer!!.start()
        }
        if (!rightAnswere) {
            rightAnswere = false
            // sound das die antwort falsch ist
            if (mMediaPlayer == null) {
                mMediaPlayer = MediaPlayer.create(context, R.raw.wrong)
                mMediaPlayer!!.isLooping = false
                mMediaPlayer!!.start()
            } else mMediaPlayer!!.start()
        }
        return rightAnswere
    }

    fun nextQuestion() {
        if (index < Questions.questions.size -1) {
            index++
            _quiz.value = Questions.questions[index]
        } else {
            index = 0
        }
    }
}