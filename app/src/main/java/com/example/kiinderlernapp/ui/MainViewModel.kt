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

    fun addItem(item: String) {
        viewModelScope.launch {
            when(item) {
                "tennisBall" -> tamagotchi.value?.tennisBall = tamagotchi.value?.tennisBall?.plus(1)!!"footBall" -> tamagotchi.value?.footBall = tamagotchi.value?.footBall?.plus(1)!!

                "apple" -> tamagotchi.value?.apple = tamagotchi.value?.apple?.plus(1)!!

                "broccoli" -> tamagotchi.value?.apple = tamagotchi.value?.broccoli?.plus(1)!!

                "peas" -> tamagotchi.value?.peas = tamagotchi.value?.peas?.plus(1)!!

                "strawberry" -> tamagotchi.value?.strawberry = tamagotchi.value?.strawberry?.plus(1)!!

                "pomegrenade" -> tamagotchi.value?.pomegrenade = tamagotchi.value?.pomegrenade?.plus(1)!!

                "cucumber" -> tamagotchi.value?.cucumber = tamagotchi.value?.cucumber?.plus(1)!!

                "kiwi" -> tamagotchi.value?.kiwi = tamagotchi.value?.kiwi?.plus(1)!!

                "salat" -> tamagotchi.value?.salat = tamagotchi.value?.salat?.plus(1)!!

                "toilet_paper" -> tamagotchi.value?.toiletPaper = tamagotchi.value?.toiletPaper?.plus(1)!!
            }
            repo.insertTamagotchiStats(tamagotchi.value!!)
        }
    }

    fun removeItem(item: String) {
        viewModelScope.launch {
            when(item) {
                "tennisball" -> {
                        tamagotchi.value?.tennisBall = tamagotchi.value?.tennisBall?.minus(1)!!
                        tamagotchi.value?.joy = tamagotchi.value?.joy?.plus(15)!!
                }

                "football" -> {
                        tamagotchi.value?.footBall = tamagotchi.value?.footBall?.minus(1)!!
                        tamagotchi.value?.joy = tamagotchi.value?.joy?.plus(20)!!
                }

                "apple" -> {
                        tamagotchi.value?.apple = tamagotchi.value?.apple?.minus(1)!!
                        tamagotchi.value?.eat = tamagotchi.value?.eat?.plus(5)!!
                }

                "broccoli" -> {
                        tamagotchi.value?.broccoli = tamagotchi.value?.broccoli?.minus(1)!!
                        tamagotchi.value?.eat = tamagotchi.value?.eat?.plus(10)!!
                }

                "peas" -> {
                        tamagotchi.value?.peas = tamagotchi.value?.peas?.minus(1)!!
                        tamagotchi.value?.eat = tamagotchi.value?.eat?.plus(10)!!
                }

                "strawberry" -> {
                        tamagotchi.value?.strawberry = tamagotchi.value?.strawberry?.minus(1)!!
                        tamagotchi.value?.eat = tamagotchi.value?.eat?.plus(5)!!
                }

                "pomegrenade" -> {
                        tamagotchi.value?.pomegrenade = tamagotchi.value?.pomegrenade?.minus(1)!!
                        tamagotchi.value?.eat = tamagotchi.value?.eat?.plus(20)!!
                }

                "cucumber" -> {
                        tamagotchi.value?.cucumber = tamagotchi.value?.cucumber?.minus(1)!!
                        tamagotchi.value?.eat = tamagotchi.value?.eat?.plus(10)!!
                }

                "kiwi" -> {
                        tamagotchi.value?.kiwi = tamagotchi.value?.kiwi?.minus(1)!!
                        tamagotchi.value?.eat = tamagotchi.value?.eat?.plus(5)!!
                }

                "salat" -> {
                        tamagotchi.value?.salat = tamagotchi.value?.salat?.minus(1)!!
                        tamagotchi.value?.eat = tamagotchi.value?.eat?.plus(10)!!
                }

                "toilet_paper" -> {
                    tamagotchi.value?.toiletPaper = tamagotchi.value?.toiletPaper?.minus(1)!!
                    tamagotchi.value?.toilet = 100
                }
            }
            repo.insertTamagotchiStats(tamagotchi.value!!)
            repo.getTamagotchiStats()
        }
    }

    fun createDefaultTamagotchi() {
        loadDataTamagotchi()
        if (tamagotchi.value == null) {
            insertTamagotchiStats(Tamagotchi(1,100,100,100,100,1,1,1,1,1,1,1,1,1,1))
        }
    }

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
