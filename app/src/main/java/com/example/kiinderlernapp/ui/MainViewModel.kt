package com.example.kiinderlernapp.ui

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.AppRepository
import com.example.kiinderlernapp.data.Questions
import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.data.datamodels.Quiz
import com.example.kiinderlernapp.data.datamodels.Tamagotchi
import com.example.kiinderlernapp.data.localdata.animal.AnimalDataBase
import com.example.kiinderlernapp.data.localdata.tamagotchi.TamagotchiDataBase
import com.example.kiinderlernapp.data.remote.CatApi
import com.example.kiinderlernapp.data.remote.DogApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    // Score-Verwaltung
    var score = 0

    // Funktion zum Hinzufügen von Punkten (Sterne)
    fun winStars() {
        score += 2
    }

    private var index = 0

    private val repo = AppRepository(DogApi, CatApi, AnimalDataBase.getDatabase(application), TamagotchiDataBase.getDatabase(application))

    private var _textToSpeach = MutableLiveData<String>()
    val textToSpeach: LiveData<String>
        get() = _textToSpeach

    val animals = repo.animals
    val dataset = repo.dataset
    val tamagotchi = repo.tamagotchi

    suspend fun updateTamagotchi(tamagotchi: Tamagotchi) {
        repo.updateTamagotchiStats(tamagotchi)
    }

    fun insertTamagotchiStats(tamagotchi: Tamagotchi) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertTamagotchiStats(tamagotchi)
        }
    }

    fun loadDataTamagotchi() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getTamagotchiStats()
        }
    }

    // Funktion zum Einfügen eines Tierobjekts in die Datenbank
    fun insert(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertAnimals(animal)
        }
    }

    // Funktion zum Laden von Tierdaten
    fun loadDataAnimals() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAnimals()
        }
    }

    // Funktion zum Auslösen von Text-to-Speech
    fun textToSpeach(text: String) {
        _textToSpeach.value = text
    }

    // Funktion zum Abrufen von Daten aus der Datenbank
    fun getDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getDatabase()
        }
    }

    // Funktion zum Löschen eines Eintrags aus der Datenbank anhand der ID
    fun deleteById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteById(id)
        }
    }

    private var _quiz = MutableLiveData(Questions.questions[index])
    val quiz: LiveData<Quiz>
        get() = _quiz

    // Funktion zum Überprüfen der Antwort im Quiz
    fun checkAnswere(answere: Int, context: Context): Boolean {
        var mMediaPlayer: MediaPlayer? = null
        var rightAnswere = answere == _quiz.value?.rightAnswere
        if (rightAnswere && index < Questions.questions.size - 1) {
            rightAnswere = true
            // Soundeffekt für richtige Antwort abspielen
            if (mMediaPlayer == null) {
                mMediaPlayer = MediaPlayer.create(context, R.raw.success)
                mMediaPlayer!!.start()
            } else mMediaPlayer!!.start()
        }
        if (!rightAnswere) {
            rightAnswere = false
            // Soundeffekt für falsche Antwort abspielen
            if (mMediaPlayer == null) {
                mMediaPlayer = MediaPlayer.create(context, R.raw.wrong)
                mMediaPlayer!!.start()
            } else mMediaPlayer!!.start()
        }
        return rightAnswere
    }

    // Funktion zur Anzeige der nächsten Quizfrage
    fun nextQuestion() {
        if (index < Questions.questions.size - 1) {
            index++
            _quiz.value = Questions.questions[index]
        } else {
            index = 0
        }
    }
}
