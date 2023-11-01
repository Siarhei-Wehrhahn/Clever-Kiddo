package com.example.kiinderlernapp.data

import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.datamodels.Quiz


object Questions {

    val questions: List<Quiz>
        get() {
            return questionsWithAnswers
        }

    var questionsWithAnswers = listOf(
        Quiz(
            question = "Welches ist das Gemüse?",
            R.drawable.apfel,
            R.drawable.erdbeere,
            R.drawable.rosenkohl,
            R.drawable.granatapfel,
            R.drawable.rosenkohl
        ),

        Quiz(
            question = "Tippe auf den Brokkoli!",
            R.drawable.broccoli,
            R.drawable.erbsen,
            R.drawable.gurcke,
            R.drawable.kopfsalat,
            R.drawable.broccoli
        ),

        Quiz(
            question = "Wähle die Erdbeere aus!",
            R.drawable.apfel,
            R.drawable.erdbeere,
            R.drawable.broccoli,
            R.drawable.erbsen,
            R.drawable.erdbeere
        ),

        Quiz(
            question = "Tippe auf den Rosenkohl!",
            R.drawable.rosenkohl,
            R.drawable.granatapfel,
            R.drawable.kiwi,
            R.drawable.mais,
            R.drawable.rosenkohl
        ),

        Quiz(
            question = "Berühre die Tomate!",
            R.drawable.erdbeere,
            R.drawable.granatapfel,
            R.drawable.kopfsalat,
            R.drawable.tomate,
            R.drawable.tomate
        ),

        Quiz(
            question = "Wähle die Radieschen aus!",
            R.drawable.erbsen,
            R.drawable.granatapfel,
            R.drawable.radieschen,
            R.drawable.kopfsalat,
            R.drawable.radieschen
        ),

        Quiz(
            question = "Berühre die Erbsen!",
            R.drawable.kopfsalat,
            R.drawable.erbsen,
            R.drawable.mais,
            R.drawable.tomate,
            R.drawable.erbsen
        ),

        Quiz(
            question = "Klicke auf den Granatapfel!",
            R.drawable.broccoli,
            R.drawable.erdbeere,
            R.drawable.granatapfel,
            R.drawable.rosenkohl,
            R.drawable.granatapfel
        ),

        Quiz(
            question = "Tippe auf den Apfel!",
            R.drawable.apfel,
            R.drawable.blumenkohl,
            R.drawable.gurcke,
            R.drawable.granatapfel,
            R.drawable.apfel
        ),

        Quiz(
            question = "Drücke auf den Mais!",
            R.drawable.broccoli,
            R.drawable.gurcke,
            R.drawable.kopfsalat,
            R.drawable.mais,
            R.drawable.mais
        ),

        Quiz(
            question = "Klicke auf die Zwiebeln!",
            R.drawable.erbsen,
            R.drawable.gurcke,
            R.drawable.kiwi,
            R.drawable.zwiebel,
            R.drawable.zwiebel
        ),

        Quiz(
            question = "Berühre die Weintrauben!",
            R.drawable.weintrauben,
            R.drawable.blumenkohl,
            R.drawable.granatapfel,
            R.drawable.kopfsalat,
            R.drawable.weintrauben
        ),

        Quiz(
            question = "Wähle die Gurke!",
            R.drawable.kopfsalat,
            R.drawable.kiwi,
            R.drawable.gurcke,
            R.drawable.tomate,
            R.drawable.gurcke
        ),

        Quiz(
            question = "Drücke auf den Kopfsalat!",
            R.drawable.broccoli,
            R.drawable.gurcke,
            R.drawable.kiwi,
            R.drawable.kopfsalat,
            R.drawable.kopfsalat
        ),

        Quiz(
            question = "Tippe auf die Blumenkohl!",
            R.drawable.blumenkohl,
            R.drawable.broccoli,
            R.drawable.gurcke,
            R.drawable.mais,
            R.drawable.blumenkohl
        ),

        Quiz(
            question = "Wähle die Kiwi!",
            R.drawable.erdbeere,
            R.drawable.granatapfel,
            R.drawable.kiwi,
            R.drawable.mais,
            R.drawable.kiwi
        )
    )
}