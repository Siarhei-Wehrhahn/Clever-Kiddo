package com.example.kiinderlernapp.data

import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.datamodels.Quiz


object Questions {

    val questions: List<Quiz>
        get() {
            return loadQuestions()
        }

    private fun loadQuestions(): List<Quiz> {
        var pics = listOf(
            R.drawable.apfel,
            R.drawable.blumenkohl,
            R.drawable.broccoli,
            R.drawable.erbsen,
            R.drawable.erdbeere,
            R.drawable.granatapfel,
            R.drawable.gurcke,
            R.drawable.kiwi,
            R.drawable.kopfsalat,
            R.drawable.mais,
            R.drawable.radieschen,
            R.drawable.rosenkohl,
            R.drawable.tomate,
            R.drawable.weintrauben,
            R.drawable.zwiebel
        ).random()
        return listOf(
            Quiz(
                question = "Tippe auf den Brokkoli!",
                R.drawable.broccoli,
                pics,
                pics,
                pics,
                R.drawable.broccoli
            ),

            Quiz(
                question = "Wähle die Erdbeere aus!",
                pics,
                R.drawable.erdbeere,
                pics,
                pics,
                R.drawable.erdbeere
            ),

            Quiz(
                question = "Tippe auf den Rosenkohl!",
                R.drawable.rosenkohl,
                pics,
                pics,
                pics,
                R.drawable.rosenkohl
            ),

            Quiz(
                question = "Berühre die Tomate!",
                pics,
                pics,
                pics,
                R.drawable.tomate,
                R.drawable.tomate
            ),

            Quiz(
                question = "Wähle die Radieschen aus!",
                pics,
                pics,
                R.drawable.radieschen,
                pics,
                R.drawable.radieschen
            ),

            Quiz(
                question = "Berühre die Erbsen!",
                pics,
                R.drawable.erbsen,
                pics,
                pics,
                R.drawable.erbsen
            ),

            Quiz(
                question = "Klicke auf den Granatapfel!",
                pics,
                pics,
                R.drawable.granatapfel,
                pics,
                R.drawable.granatapfel
            ),

            Quiz(
                question = "Tippe auf den Apfel!",
                R.drawable.apfel,
                pics,
                pics,
                pics,
                R.drawable.apfel
            ),

            Quiz(
                question = "Drücke auf den Mais!",
                pics,
                pics,
                pics,
                R.drawable.mais,
                R.drawable.mais
            ),

            Quiz(
                question = "Klicke auf die Zwiebeln!",
                pics,
                pics,
                R.drawable.zwiebel,
                pics,
                R.drawable.zwiebel
            ),

            Quiz(
                question = "Berühre die Weintrauben!",
                R.drawable.weintrauben,
                pics,
                pics,
                pics,
                R.drawable.weintrauben
            ),

            Quiz(
                question = "Wähle die Gurke!",
                pics,
                pics,
                R.drawable.gurcke,
                pics,
                R.drawable.gurcke
            ),

            Quiz(
                question = "Drücke auf den Kopfsalat!",
                pics,
                pics,
                pics,
                R.drawable.kopfsalat,
                R.drawable.kopfsalat
            ),

            Quiz(
                question = "Tippe auf die Blumenkohl!",
                R.drawable.blumenkohl,
                pics,
                pics,
                pics,
                R.drawable.blumenkohl
            ),

            Quiz(
                question = "Wähle die Kiwi!",
                pics,
                pics,
                R.drawable.kiwi,
                pics,
                R.drawable.kiwi
            )
        )
    }
}